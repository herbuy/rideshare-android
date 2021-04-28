package com.skyvolt.jabber;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import client.data.LocalSession;
import client.ui.GotoActivity;
import client.ui.display.Trip.TripDetailsActivity;
import client.ui.display.Trip.schedule_trip.trip_form_display_params.TripFormDisplayParameters;
import client.ui.display.notification.NotificationService;
import core.businessobjects.Trip;
import initialize.InitializeSystem;
import layers.render.Atom;
import libraries.android.AddressListFromString;
import libraries.android.CSVFromAddress;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.android.ObjectTaskPermit;
import libraries.underscore.Singleton;
import shared.BaseActivity;
import test.CurrentLocation;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitializeSystem.where(getBaseContext());
        finish();

        //setContentView(new ColorMatcher(this).getView());
        openDefaultActivity();

        //GotoActivity.scheduleTrip(this, TripFormDisplayParameters.DRIVER);
        //GotoActivity.signUp(this);


        //GotoActivity.tripDetails(this, LocalSession.instance().getLastTripScheduled(new Trip()));
        //GotoActivity.login(this);
        //GotoActivity.scheduleTrip(this, TripFormDisplayParameters.DRIVER);

        //TextInputLayout child = Atom.textInputLayout(this,"Your name","",true,null,null);
        //setContentView(MakeDummy.scrollView(child));
        /*setContentView(MakeDummy.scrollView(
                new LabelledEditText3(this).setHint("Hello")
        ));*/


        //setContentView(new HiddablePagesTest(getApplicationContext()).getView());


    }

    @Override
    protected void onDestroy() {
        this.sendBroadcast(NotificationService.restart(this));
        super.onDestroy();
    }

    private void openDefaultActivity() {
        finish();
        GotoActivity.splashScreen(this);

    }



}
