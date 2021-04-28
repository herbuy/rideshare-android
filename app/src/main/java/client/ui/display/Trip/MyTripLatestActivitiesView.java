package client.ui.display.Trip;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import client.data.AppCallback;
import client.data.Rest;
import client.ui.display.user_activities.ActivityRenderingContext;
import client.ui.display.user_activities.ActivityRenderingContextForKnownTrip;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForGetTripActivities;
import core.businessobjects.TripActivity;
import libraries.android.MakeDummy;
import libraries.android.MultiStateNetworkListView;
import resources.Dp;
import retrofit2.Call;
import retrofit2.Response;

public class MyTripLatestActivitiesView implements HerbuyView {
    Context context;

    public MyTripLatestActivitiesView(Context context) {
        this.context = context;
    }

    String error = "";
    @Override
    public View getView() {
        final List<View> successItems = new ArrayList<View>();


        final MultiStateNetworkListView listView = new MultiStateNetworkListView(context) {
            @Override
            public void refresh() {

            }
            @Override
            protected AbsListView createListView() {
                return new ListView(context);
            }

            @Override
            protected View defaultView() {
                return MakeDummy.textView(context,"Default view");
            }

            @Override
            protected View busyView() {
                return MakeDummy.textView(context,"Loading...");
            }

            @Override
            protected View emptyView() {
                return MakeDummy.textView(context,"No activities yet");
            }

            @Override
            protected List<View> successView() {
                return successItems;
            }

            @Override
            protected View errorView() {
                return null;
            }
        };

        listView.changeViewToBusy();
        Rest.api().getTripActivities(new ParamsForGetTripActivities()).enqueue(new AppCallback<List<TripActivity>>() {
            @Override
            protected void onSuccess(Call<List<TripActivity>> call, Response<List<TripActivity>> response) {

                ActivityRenderingContext renderingContext = getActivityRenderingContextWhereWeKnowTheTrip();
                for (TripActivity tripActivity : response.body()) {
                    successItems.add(
                            MakeDummy.padding(renderingContext.render(tripActivity),Dp.two_em())
                    );
                }
                listView.changeViewToSuccess();

            }

            @Override
            protected void onError(Call<List<TripActivity>> call, Throwable t) {
                error = t.getMessage();
                listView.changeViewToError();
            }
        });
        return listView.getView();
    }

    private ActivityRenderingContext getActivityRenderingContextWhereWeKnowTheTrip() {
        return new ActivityRenderingContextForKnownTrip(context);
    }
}
