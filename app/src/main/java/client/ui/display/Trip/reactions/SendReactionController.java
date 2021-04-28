package client.ui.display.Trip.reactions;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForSendTripReaction;
import core.businessobjects.Trip;
import core.businessobjects.TripReaction;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import retrofit2.Call;
import retrofit2.Response;

public abstract class SendReactionController implements HerbuyView {
    Context context;
    Trip trip;
    FrameLayoutBasedHerbuyStateView view;

    public SendReactionController(Context context, Trip trip) {
        this.context = context;
        this.trip = trip;
        view  = new FrameLayoutBasedHerbuyStateView(context);
    }


    @Override
    public View getView() {
        init();

        return view.getView();
    }

    private void init() {
        view.render(defaultView());
    }

    private View defaultView() {
        return Atom.button(context, getDisplayText().toString(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.render(busy());
                ParamsForSendTripReaction paramsForSendTripReaction = new ParamsForSendTripReaction();
                paramsForSendTripReaction.setSessionId(LocalSession.instance().getId());
                paramsForSendTripReaction.setTripId(trip.getTripId());
                paramsForSendTripReaction.setReactionType(getReactionType());

                Rest.api().sendTripReaction(paramsForSendTripReaction).enqueue(new AppCallback<List<TripReaction>>() {
                    @Override
                    protected void onSuccess(Call<List<TripReaction>> call, Response<List<TripReaction>> response) {
                        view.render(success());
                        onReactionSent(response.body().get(0));
                    }

                    @Override
                    protected void onError(Call<List<TripReaction>> call, Throwable t) {
                        view.render(defaultView());
                        MessageBox.show(t.getMessage(),context);
                    }
                });

            }
        });
    }

    protected abstract void onReactionSent(TripReaction tripReaction);

    protected abstract String getReactionType();

    protected View busy() {
        return MakeDummy.backgroundColor(MakeDummy.textColor(Atom.button(context, "Wait...",null), Color.parseColor("#888888")), Color.TRANSPARENT);
    }

    private View success() {
        return MakeDummy.backgroundColor(MakeDummy.textColor(Atom.button(context, "Done", null), Color.parseColor("#888888")), Color.TRANSPARENT);
    }

    protected abstract CharSequence getDisplayText();

}
