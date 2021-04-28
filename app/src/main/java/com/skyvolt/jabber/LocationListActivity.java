package com.skyvolt.jabber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import client.ui.GotoActivity;
import client.ui.IntentExtras;
import client.ui.RequestCodes;
import client.ui.display.Location.LocationList;
import core.businessobjects.Location;
import libraries.JsonEncoder;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import resources.Dp;
import shared.BaseActivity;

public class LocationListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Locations");
        setContentView(contentView());
    }

    private View contentView() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                this,
                btnAddLocation(),
                listOfLocations()
        );

        layout.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return layout;


    }

    LocationList locationList;
    private View listOfLocations() {
        locationList = new LocationList(this);
        return locationList.getView();
    }

    private View btnAddLocation() {
        return Atom.button(this, "+ Add Location", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.addLocation(LocationListActivity.this);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RequestCodes.ADD_LOCATION){
            if(resultCode == RESULT_OK){
                Location addedLocation = JsonEncoder.decode(data.getStringExtra(IntentExtras.location), Location.class);
                if(locationList != null){
                    locationList.orderByMostRecentlyAdded();
                    locationList.brieflyHighlight(addedLocation.getId());
                    locationList.refresh();
                }
                else{
                    MessageBox.show("Location list is null, can not refresh",this);
                }
            }
        }

    }
}
