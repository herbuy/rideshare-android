package com.skyvolt.jabber;

import android.os.Bundle;

import client.ui.IntentExtras;
import client.ui.display.Location.LocationTTProfileGeneric;
import core.businessobjects.Location;
import libraries.JsonEncoder;
import libraries.android.MessageBox;
import shared.BaseActivity;

public class LocationProfileActivityGeneric extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Location location = JsonEncoder.decode(getIntent().getStringExtra(IntentExtras.location),Location.class);
        if(location == null){
            MessageBox.show("Location not specified",this);
        }
        setTitle(location.getName());
        setContentView(new LocationTTProfileGeneric(this,location).getView());
    }
}
