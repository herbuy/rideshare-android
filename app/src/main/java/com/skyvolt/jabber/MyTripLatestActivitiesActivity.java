package com.skyvolt.jabber;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import client.ui.display.Trip.MyTripLatestActivitiesView;

public class MyTripLatestActivitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Trip Latest Activities");
        setContentView(new MyTripLatestActivitiesView(this).getView());
    }
}
