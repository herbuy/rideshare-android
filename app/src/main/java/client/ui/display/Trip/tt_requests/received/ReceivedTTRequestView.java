package client.ui.display.Trip.tt_requests.received;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import client.ui.display.Trip.tt_requests.sent.ReceivedTTRequestStatusIndicator;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Proposal;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;

public class ReceivedTTRequestView implements HerbuyView{
    Context context;
    Proposal proposal;

    public ReceivedTTRequestView(Context context, Proposal proposal) {
        this.context = context;
        this.proposal = proposal;
    }

    @Override
    public View getView() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                description(),
                status()

        );

        layout.setBackgroundColor(Color.WHITE);
        layout.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return layout;
    }

    private View status() {
        //remove this line of code bse we are only using it to test what happens if the status was soething else
        //messageToTripActor.setProposalStatus(TTRequest.Status.PENDING);


        return new ReceivedTTRequestStatusIndicator(context, proposal).getView();
    }

    private View description() {
        return Atom.textView(
                context,
                String.format(
                        "%s wants to travel with you from %s to %s on %s - %s",
                        proposal.getActorName(),
                        proposal.getTripOriginName(),
                        proposal.getTripDestinationName(),
                        proposal.getTripDate(),
                        proposal.getProposalDate()

                )
        );
    }
}
