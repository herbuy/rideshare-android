package client.ui.display.tt_requests;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.skyvolt.jabber.R;

import java.util.ArrayList;
import java.util.List;

import client.data.AppCallback;
import client.data.Rest;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForGetTrips;
import core.businessobjects.Trip;
import client.data.DummyData;
import libraries.android.MakeDummy;
import libraries.android.AdapterForFragmentPager;
import libraries.android.MultiStateNetworkListView;
import libraries.android.TabInterceptor;
import resources.Dp;
import retrofit2.Call;
import retrofit2.Response;

public class TTRequestCollections implements HerbuyView {
    AppCompatActivity context;

    public TTRequestCollections(AppCompatActivity context) {
        this.context = context;
    }

    @Override
    public View getView() {
        View root = LayoutInflater.from(context).inflate(R.layout.activity_tabbed,null);


        ViewPager viewPager = (ViewPager) root.findViewById(R.id.container);
        viewPager.setAdapter(
                new AdapterForFragmentPager(context.getSupportFragmentManager())
                        .addTab(
                                "Received",
                                tripsOrderByMostRecentReceivedTTRequest()
                        )
                        .addTab(
                                "Sent",
                                tripsOrderedByMostRecentlySentTTRequest()
                        )
                        .addTab(
                                "Settings",
                                MakeDummy.textView(context, "Settings")
                        )
        );


        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        TabInterceptor.intercept(tabLayout);

        return root;
    }

    private View tripsOrderedByMostRecentlySentTTRequest() {
        return new MultiStateNetworkListView(context){
            @Override
            public void refresh() {

            }
            @Override
            protected AbsListView createListView() {
                return new ListView(context);
            }

            @Override
            protected View defaultView() {
                return MakeDummy.textView(context,"Default View");
            }

            @Override
            protected View busyView() {
                return MakeDummy.textView(context, "Loading...");
            }

            @Override
            protected View emptyView() {
                return MakeDummy.textView(context,"No Requests found. Relax and take a soda");
            }

            @Override
            protected List<View> successView() {
                return null;
            }

            @Override
            protected View errorView() {
                return null;
            }
        }.changeViewToBusy().getView();

    }

    private View tripsOrderByMostRecentReceivedTTRequest() {
        return new MultiStateNetworkListView(context){
            @Override
            public void refresh() {

            }
            @Override
            protected AbsListView createListView() {
                ListView listView = new ListView(context);

                return listView;
            }

            @Override
            protected View defaultView() {
                return MakeDummy.textView(context,"Default View");
            }


            @Override
            protected View busyView() {
                Rest.api().getTrips(new ParamsForGetTrips()).enqueue(new AppCallback<List<Trip>>() {
                    @Override
                    protected void onSuccess(Call<List<Trip>> call, Response<List<Trip>> response) {
                        for(Trip trip: response.body()){
                            View itemView = MakeDummy.textView(context, DummyData.randomName());

                            successViewList.add(itemView);
                        }
                        changeViewToSuccess();
                    }


                    @Override
                    protected void onError(Call<List<Trip>> call, Throwable t) {

                        error = t.getMessage();
                        changeViewToError();
                    }
                });
                return paddedText("Loading...");
            }
            String error = "";
            @Override
            protected View errorView() {
                return paddedText(error);
            }

            @Override
            protected View emptyView() {
                return MakeDummy.textView(context,"No Requests found. Relax and take a soda");
            }

            List<View> successViewList = new ArrayList<View>();
            @Override
            protected List<View> successView() {
                return successViewList;
            }


        }.changeViewToBusy().getView();
    }

    private View paddedText(String text) {
        return MakeDummy.padding(MakeDummy.centerHorizontal(MakeDummy.textView(context, text)), Dp.scaleBy(8));
    }
}
