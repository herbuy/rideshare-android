package com.skyvolt.jabber;

import android.os.Bundle;
import android.view.View;

import client.data.LocalSession;
import client.ui.IntentExtras;
import client.ui.display.Trip.reactions.ListOfPeopleWhoReactedToTrip;
import client.ui.display.Trip.reactions.ListOfPossibleReactionsToTrip;
import core.businessobjects.Trip;
import core.businessobjects.TripReaction;
import libraries.JsonEncoder;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.english.AddSIfNotEndWithS;
import shared.BaseActivity;

public class TripReactionsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Trip trip = JsonEncoder.decode(getIntent().getStringExtra(IntentExtras.tripDetails), Trip.class);
        if (trip == null) {
            MessageBox.show("Trip not specified", this);
        }

        setTitle("Trip Reactions");
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

        if (trip.wasPostedByuser(LocalSession.instance().getUserId())) {
            return Atom.lightBackground(
                    this,
                    String.format(
                            "People who reacted to your trip from %s to %s set for %s",
                            trip.getOriginName(),
                            trip.getDestinationName(),
                            trip.getTripDate()
                    )
            );

        } else {
            //we have two choices: either return only trips from freinds but show all people who reacted even if not friends
            //or show all trips but only friends who reacted. I prefer first option, so trips are more relevant
            return Atom.lightBackground(
                    this,
                    String.format(
                            "People who reacted to %s trip from %s to %s set for %s",
                            AddSIfNotEndWithS.run(trip.getActorName()),
                            trip.getOriginName(),
                            trip.getDestinationName(),
                            trip.getTripDate()
                    )
            );
        }

    }

    private View body(Trip trip) {


        final ListOfPeopleWhoReactedToTrip peopleWhoReacted = new ListOfPeopleWhoReactedToTrip(this, trip);
        ListOfPossibleReactionsToTrip possibleReactions = new ListOfPossibleReactionsToTrip(this, trip){
            @Override
            protected void onReactionSent(TripReaction tripReaction) {
                peopleWhoReacted.refresh();
            }
        };

        return MakeDummy.linearLayoutVertical(
                this,
                possibleReactions.getView(),
                peopleWhoReacted.getView()
        );
    }
}
