package com.skyvolt.jabber;

import android.os.Bundle;
import android.view.View;

import client.ui.GotoActivity;
import client.ui.Molecule;
import client.ui.display.Trip.TripGridViewForOtherTripsToDestination;
import client.data.DummyData;
import libraries.android.MakeDummy;
import resources.Dp;
import shared.BaseActivity;

public class OtherTripsToDestinationActivity extends BaseActivity {

    String dummyDestination = DummyData.randomLocationName();
    String dummyOrigin = DummyData.randomLocationName();
    String dummyTravelDate = DummyData.randomDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setTitle("Also Going to " + dummyDestination);
        setContentView(contentView());
    }

    private View contentView() {
        return MakeDummy.linearLayoutVertical(
                this,
                pageTitle(),
                pageItems()
        );
    }

    private View pageItems() {
        TripGridViewForOtherTripsToDestination tripGridView = new TripGridViewForOtherTripsToDestination(this){
            @Override
            protected void onZoomIn(View sharedElement) {
                sharedElement.setTransitionName("trans1");
                GotoActivity.otherTripToDestinationZoomedIn(OtherTripsToDestinationActivity.this,sharedElement,"trans1");
            }
        };
        return tripGridView.getView();
    }

    private View pageTitle() {
        String primaryText = String.format(
                "Other People leaving %s for %s on %s ",
                dummyOrigin, dummyDestination, dummyTravelDate
        );

        return Molecule.actionPanelItem(
                this, primaryText,
                "You may want to travel with them",
                false,
                MakeDummy.imageView(this, Dp.scaleBy(3f), Dp.scaleBy(3f), R.drawable.ic_menu_traveller_white)
        );


    }
}
