package server.trip;

import java.util.UUID;

import cache.TripDatabase;
import core.businessobjects.Trip;
import libraries.data.LocalDatabase;
import server.backendobjects.BackEndTripFromParams;

public class NotifyOtherTrips {
    public static void acceptingProposalsFromTrip(final BackEndTripFromParams scheduledTrip) {
        final String transactionId = UUID.randomUUID().toString();

        new TripDatabase().forEach(new LocalDatabase.ForEach<Trip>() {
            @Override
            public void run(Trip receivingTrip) {

                //order matters, we notify those who can propose to a newly scheduled trip
                if(TripDecorator.fromTrip(scheduledTrip).isAcceptingProposalsFromTrip(receivingTrip)){
                    AddTripScheduledMessage.where(receivingTrip.getTripId(), scheduledTrip.getTripId(),transactionId);
                    UpdateTripTimestampLastUpdated.where(receivingTrip.getTripId());
                }

            }
        });
    }
}
