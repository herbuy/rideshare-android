package client.ui.display.Trip.reactions;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForGetTripReactions;
import core.businessobjects.Trip;
import core.businessobjects.TripReaction;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.HerbuyAbsListViewer;
import libraries.android.HerbuyStateView;
import resources.Dp;
import retrofit2.Call;
import retrofit2.Response;

public class ListOfPeopleWhoReactedToTrip implements HerbuyView {
    Context context;
    Trip trip;
    FrameLayoutBasedHerbuyStateView view;

    public ListOfPeopleWhoReactedToTrip(Context context, Trip trip) {
        this.context = context;
        this.trip = trip;
        view  = new FrameLayoutBasedHerbuyStateView(context);
        view.getView().setBackgroundColor(Color.WHITE);
    }

    @Override
    public View getView() {
        init();
        return view.getView();
    }

    protected void init() {
        view.render(MakeDummy.textView(context, "Loading..."));
        Rest.api().getTripReactions(paramsForGetTripReactions()).enqueue(new AppCallback<List<TripReaction>>() {
            @Override
            protected void onSuccess(Call<List<TripReaction>> call, Response<List<TripReaction>> response) {
                if (response.body().size() < 1) {
                    view.render(listEmpty());
                } else {
                    view.render(listOfReactors(response.body()));
                }
            }

            private View listOfReactors(List<TripReaction> body) {
                HerbuyAbsListViewer<TripReaction> listOfPeopleWhoReacted = new HerbuyAbsListViewer<TripReaction>(context) {
                    @Override
                    protected AbsListView createAbsListView(Context context) {
                        return new ListView(context);
                    }

                    @Override
                    protected View createItemView(Context context, TripReaction item) {
                        LinearLayout layout = MakeDummy.linearLayoutVertical(
                                context,
                                Atom.textViewPrimaryBold(context, item.getActorName()),
                                Atom.textViewPrimaryNormal(context, item.getReactionType())
                        );
                        layout.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
                        return layout;
                    }
                };
                listOfPeopleWhoReacted.setContent(body);
                return listOfPeopleWhoReacted.getView();
            }

            private View listEmpty() {
                return MakeDummy.textView(context, "List Empty");
            }

            @Override
            protected void onError(Call<List<TripReaction>> call, Throwable t) {
                view.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                    @Override
                    public void run() {

                    }
                });
            }
        });

    }

    private ParamsForGetTripReactions paramsForGetTripReactions() {
        ParamsForGetTripReactions paramsForGetTripReactions = new ParamsForGetTripReactions();
        paramsForGetTripReactions.setSessionId(LocalSession.instance().getId());
        paramsForGetTripReactions.setTripIdEquals(trip.getTripId());
        return paramsForGetTripReactions;
    }

    public void refresh() {
        init();
    }
}
