package client.ui.display.Trip.tt_requests;

import android.content.Context;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import client.data.LocalSession;

import client.data.Rest2;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.display.Trip.tt_requests.sent.SentTripTTRequestController;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForSendTTProposal;
import core.businessobjects.Proposal;
import core.businessobjects.Trip;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.android.HerbuyStateView;
import retrofit2.Call;
import retrofit2.Response;



public abstract class TTRSendController implements HerbuyView {
    private final Trip fromTrip;
    Context context;
    Trip toTrip;
    FrameLayoutBasedHerbuyStateView view;

    public TTRSendController(Context context, Trip toTrip, Trip proposingTrip) {
        this.context = context;
        this.toTrip = toTrip;
        this.fromTrip = proposingTrip;
        view = new FrameLayoutBasedHerbuyStateView(context);
        view.render(sendButton());
    }

    private View sendButton() {
        return Atom.button(
                context,
                toTrip.isDriver() ? "Ask for Lift" : "Offer Lift",
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSendRequest();
            }
        });
    }

    protected void doSendRequest() {

        view.render(MakeDummy.textView(context,"Sending request..."));
        Rest2.api().sendProposal(paramsForSendTTRequest()).enqueue(new AppCallback<List<Proposal>>() {
            @Override
            protected void onSuccess(Call<List<Proposal>> call, Response<List<Proposal>> response) {

                Proposal sentProposal = response.body().get(0);
                view.render(success(sentProposal));
                TTRSendController.this.onSuccess(sentProposal);
            }

            @Override
            protected void onError(Call<List<Proposal>> call, Throwable t) {
                MessageBox.show(t.getMessage(),context);
                view.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                    @Override
                    public void run() {
                        doSendRequest();
                    }
                });
            }
        });
    }

    protected abstract void onSuccess(Proposal sentProposal);

    private ParamsForSendTTProposal paramsForSendTTRequest() {
        ParamsForSendTTProposal paramsForSendTTProposal = new ParamsForSendTTProposal();
        paramsForSendTTProposal.setSessionId(LocalSession.instance().getId());
        paramsForSendTTProposal.setReceivingTripId(toTrip.getTripId());
        paramsForSendTTProposal.setResponseTypeToInterested();
        paramsForSendTTProposal.setSendingTripId(fromTrip.getTripId());
        return paramsForSendTTProposal;
    }

    private View success(Proposal proposal) {
        return new SentTripTTRequestController(context, proposal).getView();
    }

    @Override
    public View getView() {
        return view.getView();
    }
}
