package client.ui.display.Trip.tripList;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import client.data.AppCallback;
import client.data.Rest2;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForGetTrips;
import core.businessobjects.Trip;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.NetStatus;
import libraries.android.HerbuyStateView;
import resources.Dp;
import retrofit2.Call;
import retrofit2.Response;

public abstract class TripListController implements HerbuyView {
    private Context context;
    FrameLayoutBasedHerbuyStateView tripListView;

    public TripListController(Context context) {
        this.context = context;
        tripListView = new FrameLayoutBasedHerbuyStateView(context); //new TripListView(context);
    }

    @Override
    public View getView() {
        NetStatus.instance().addListener(new NetStatus.Listener() {
            @Override
            public void onConnected() {
                refresh();
            }

            @Override
            public void onDisconnected() {
                //we might not want to remove the data we already have in the list [so we can check count or use other means to decide]
                tripListView.render(Atom.centeredText(context,"Not Connected to internet"));
            }
        });


        //refresh();

        return tripListView.getView();
    }

    public void refresh() {
        tripListView.renderBusy();
        Rest2.api().getTrips(getParamsForGetTrips()).enqueue(new AppCallback<List<Trip>>() {
            @Override
            protected void onSuccess(Call<List<Trip>> call, Response<List<Trip>> response) {
                renderTripsOrIndicateEmpty(response, response.body());
            }

            private void renderTripsOrIndicateEmpty(Response<List<Trip>> response, List<Trip> body) {
                if (body == null || body.size() < 1) {
                    tripListView.render(empty());
                } else {
                    tripListView.render(makeTripListView(response));
                }
            }

            private View empty() {
                return Atom.centeredText(context, "No content. Will appear here once available.");
            }

            @Override
            protected void onError(Call<List<Trip>> call, Throwable t) {
                tripListView.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                    @Override
                    public void run() {
                        refresh();
                    }
                });
            }
        });
    }

    protected abstract ParamsForGetTrips getParamsForGetTrips();

    private View makeTripListView(Response<List<Trip>> response) {


        LinearLayout listView = MakeDummy.linearLayoutVertical(context);
        for (Trip item : response.body()) {
            listView.addView(createListItemView(item));
            listView.addView(MakeDummy.lineSeparator(context, Dp.two_em()));
        }

        return MakeDummy.scrollView(listView);
    }


    protected abstract View createListItemView(Trip trip);
}
