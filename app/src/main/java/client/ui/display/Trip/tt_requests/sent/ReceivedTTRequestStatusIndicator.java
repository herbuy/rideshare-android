package client.ui.display.Trip.tt_requests.sent;

import android.content.Context;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest;
import client.ui.GotoActivity;
import client.ui.IntentExtras;
import core.businessmessages.toServer.ParamsForAcceptTTRequest;
import core.businessobjects.Proposal;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.english.AddSIfNotEndWithS;
import libraries.android.HerbuyStateView;
import retrofit2.Call;
import retrofit2.Response;
import  client.ui.display.Trip.tt_requests.TripTTRequestStatusIndicator;

public class ReceivedTTRequestStatusIndicator extends TripTTRequestStatusIndicator {

    public ReceivedTTRequestStatusIndicator(Context context, Proposal proposal) {
        super(context, proposal);
    }

    private void doAccept() {

        statusView.render(MakeDummy.textView(context, "Accepting request..."));
        Rest.api().acceptTTRequest(getParamsForAcceptRequest()).enqueue(new AppCallback<List<Proposal>>() {
            @Override
            protected void onSuccess(Call<List<Proposal>> call, Response<List<Proposal>> response) {
                statusView.render(statusAccepted());
            }

            @Override
            protected void onError(Call<List<Proposal>> call, Throwable t) {
                statusView.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                    @Override
                    public void run() {
                        doAccept();
                    }
                });
            }
        });
    }

    private ParamsForAcceptTTRequest getParamsForAcceptRequest() {
        ParamsForAcceptTTRequest paramsForAcceptTTRequest = new ParamsForAcceptTTRequest();
        paramsForAcceptTTRequest.setSessionId(LocalSession.instance().getId());
        paramsForAcceptTTRequest.setTtRequestId(proposal.getProposalId());
        return paramsForAcceptTTRequest;
    }

    @Override
    protected View statusPending() {
        return MakeDummy.linearLayoutVertical(
                context,
                MakeDummy.textView(
                        context,
                        String.format("Waiting for you to respond to %s request", AddSIfNotEndWithS.run(proposal.getActorName()))
                ),
                Atom.button(context, "Accept", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doAccept();
                    }
                })
        );
    }

    @Override
    protected View statusAccepted() {
        return MakeDummy.linearLayoutVertical(
                context,
                MakeDummy.textView(
                        context,
                        String.format(
                                "you accepted %s request to travel with you from %s to %s on %s. You can chat to discuss the details",
                                AddSIfNotEndWithS.run(proposal.getActorName()),
                                proposal.getTripOriginName(),
                                proposal.getTripDestinationName(),
                                proposal.getTripDate()
                        )
                ),
                Atom.button(context, "Chat", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentExtras.ChatParameters chatParameters = new IntentExtras.ChatParameters();
                        chatParameters.setTargetUserId(proposal.getActorId());
                        chatParameters.setTargetUserName(proposal.getActorName());
                        chatParameters.setTargetUserProfilePic(proposal.getActorProfilePic());
                        GotoActivity.chat(context, chatParameters);
                    }
                })

        );

    }

    @Override
    protected View statusRejected() {
        return MakeDummy.textView(
                context,
                String.format("you declined this request. You will definitely miss travelling with %s", proposal.getActorName())
        );
    }

    @Override
    protected View statusExpired() {
        return MakeDummy.textView(
                context,
                String.format("This request has expired")
        );
    }

    @Override
    protected View statusCancelled() {
        return MakeDummy.textView(
                context,
                String.format("%s changed his mind and cancelled this request", proposal.getActorName())
        );
    }

}
