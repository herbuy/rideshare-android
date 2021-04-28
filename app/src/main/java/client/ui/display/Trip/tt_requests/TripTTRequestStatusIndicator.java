package client.ui.display.Trip.tt_requests;

import android.content.Context;
import android.view.View;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Proposal;


public abstract class TripTTRequestStatusIndicator implements HerbuyView {
    protected final Context context;
    protected final Proposal proposal;
    protected final FrameLayoutBasedHerbuyStateView statusView;

    public TripTTRequestStatusIndicator(Context context, Proposal proposal) {
        this.context = context;
        statusView = new FrameLayoutBasedHerbuyStateView(context);
        this.proposal = proposal;
    }

    @Override
    public View getView() {
        init();
        return statusView.getView();
    }

    private void init() {
        if(Proposal.Status.PENDING.equalsIgnoreCase(proposal.getProposalStatus())){
            statusView.render(statusPending());
        }
        else if(Proposal.Status.ACCEPTED.equalsIgnoreCase(proposal.getProposalStatus())){
            statusView.render(statusAccepted());
        }
        else if(Proposal.Status.REJECTED.equalsIgnoreCase(proposal.getProposalStatus())){
            statusView.render(statusRejected());
        }
        else if(Proposal.Status.EXPIRED.equalsIgnoreCase(proposal.getProposalStatus())){
            statusView.render(statusExpired());
        }
        else if(Proposal.Status.CANCELLED.equalsIgnoreCase(proposal.getProposalStatus())){
            statusView.render(statusCancelled());
        }
        else{
            throw new RuntimeException("Unknown status for sent TT Request");
        }
    }

    public void render(View view){
        statusView.render(view);
    }

    protected abstract View statusCancelled();

    protected abstract View statusExpired();

    protected abstract View statusRejected();

    protected abstract View statusAccepted();

    protected abstract View statusPending();


}
