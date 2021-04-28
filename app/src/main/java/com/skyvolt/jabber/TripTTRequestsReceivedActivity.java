package com.skyvolt.jabber;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import client.ui.IntentExtras;
import client.ui.display.Trip.tt_requests.TripTTRequestsReceivedController;
import core.businessobjects.Trip;
import libraries.JsonEncoder;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import shared.BaseActivity;

public class TripTTRequestsReceivedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Trip trip = JsonEncoder.decode(getIntent().getStringExtra(IntentExtras.tripDetails),Trip.class);
        if(trip == null){
            MessageBox.show("Trip not specified",this);
        }

        setTitle("Travel Requests Received");
        setContentView(contentView(trip));
    }

    private View contentView(Trip trip) {
        return MakeDummy.linearLayoutVertical(
                this,
                title(trip),
                body(trip)
        );
    }

    private View title(Trip trip) {
        TextView textView = Atom.textViewPrimaryNormal(this,
                String.format(
                        "People who want to travel with you from %s to %s on %s",
                        trip.getOriginName(),trip.getDestinationName(),trip.getTripDate()
                )
        );
        return textView;
    }

    private View body(Trip trip) {
        return new TripTTRequestsReceivedController(this,trip).getView();
    }
}
