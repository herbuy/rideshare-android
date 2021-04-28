package client.ui.display.Location;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;

import client.data.AppCallback;
import client.data.Rest;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Location;
import libraries.android.MakeDummy;
import libraries.android.HerbuyAbsListViewer;
import libraries.android.HerbuyStateView;
import retrofit2.Call;
import retrofit2.Response;

public class LocationList implements HerbuyView {
    private Context context;
    private FrameLayoutBasedHerbuyStateView view;

    public LocationList(Context context) {
        this.context = context;
        view = new FrameLayoutBasedHerbuyStateView(context);
    }

    @Override
    public View getView() {
        refresh();
        return view.getView();
    }

    public void refresh() {
        view.render(MakeDummy.textView(context, "Loading list of locations..."));
        Rest.api().getLocations().enqueue(new AppCallback<List<Location>>() {
            @Override
            protected void onSuccess(Call<List<Location>> call, Response<List<Location>> response) {
                view.render(list(response.body()));
            }

            private View list(List<Location> body) {
                return new HerbuyAbsListViewer<Location>(context) {
                    @Override
                    protected AbsListView createAbsListView(Context context) {
                        return new ListView(context);
                    }

                    @Override
                    protected View createItemView(Context context, Location item) {
                        return new LocationView(context, item).getView();
                    }
                }.setContent(body).getView();
            }

            @Override
            protected void onError(Call<List<Location>> call, Throwable t) {
                view.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                    @Override
                    public void run() {
                        refresh();

                    }
                });
            }
        });
    }

    public void orderByMostRecentlyAdded() {
        //the next time the list is loaded, the items will be added by most recently added
    }

    public void brieflyHighlight(String id) {

    }
}
