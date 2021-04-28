package com.skyvolt.jabber;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import client.data.Rest;
import client.ui.libraries.HerbuyListView;
import client.ui.display.TravellerView;
import core.businessmessages.toServer.ParamsForGetTrips;
import core.businessobjects.Trip;
import shared.BaseActivity;

public class TravellerListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView());
    }

    private View contentView() {
        HerbuyListView<Trip> travellerListView = new HerbuyListView<Trip>(this) {
            @Override
            protected View createItemView(Trip item) {
                TravellerView travellerView = createTravellerView(item);
                return travellerView.getView();
            }

            private TravellerView createTravellerView(final Trip item) {
                return new TravellerView(getContext(), item) {


                    @Override
                    protected void onViewTravelTogetherRequests(Trip trip) {
                        startActivity(new Intent(
                                TravellerListActivity.this,
                                TTRequestListActivity.class

                        ));
                    }
                };
            }
        };


        Rest.api().getTrips(new ParamsForGetTrips()).enqueue(travellerListView);

        return travellerListView.getView();


    }

}
