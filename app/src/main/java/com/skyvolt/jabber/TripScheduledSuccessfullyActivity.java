package com.skyvolt.jabber;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;


import client.ui.GotoActivity;
import client.ui.IntentExtras;
import client.ui.display.Trip.TripScheduledSuccessfulyView;
import core.businessobjects.Trip;
import libraries.JsonEncoder;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import resources.Dp;
import shared.BaseActivity;

public class TripScheduledSuccessfullyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //takes as input the trip details and outputs the list of next steps u can perform
        //e.g
        //find other people going to destionation [input = destination, getOutputAsNextSteps = people] - helpful to find people you could tavel with
        // read about the destionation - service providers in destionation, facilities, people going there, [input = destination]
        //see list of people who have been to the destionation [input = destination] - May want to consult them
        //notify friends - they may want to wish u a safe journey
        // see a list of all your trips
        // ** there shd be a way to access these pages apart from this screen

        Trip trip = getInputAsTripDetails();
        if(trip == null){
            MessageBox.show("Trip details not specified",this);
        }

        setTitle("Great News");
        setContentView(getOutputAsNextSteps(trip));

    }

    private View getOutputAsNextSteps(Trip trip) {
        View view = new TripScheduledSuccessfulyView(this,trip){
            @Override
            protected void onNotifyFriendsOfTrip(Trip trip) {

            }

            @Override
            protected void onSeeFriendsInDestination(Trip trip) {

            }

            @Override
            protected void onSeeFriendsBeenToDestination(Trip trip) {

            }

            @Override
            protected void onSeeOtherPeopleGoingToDestination(Trip trip) {

                //trips to destination except trip

                GotoActivity.otherTripsToDestinationExceptTripId(TripScheduledSuccessfullyActivity.this, "");

            }

            @Override
            protected void onScheduleAnotherTrip() {

            }

            @Override
            protected void onReadAboutDestination(Trip trip) {

            }
        }.getView();

        view.setBackgroundColor(Color.WHITE);
        view.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return MakeDummy.scrollView(view);
    }

    private Trip getInputAsTripDetails() {
        try{
            return JsonEncoder.decode(getIntent().getStringExtra(IntentExtras.tripDetails),Trip.class);
        }
        catch (Exception ex){
            return null;
        }

    }


}
