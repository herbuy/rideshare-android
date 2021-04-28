package server.trip;

import cache.TripDatabase;
import core.businessobjects.Trip;
import libraries.data.LocalDatabase;

public class UpdateTripTimestampLastUpdated {
    public static void where(final String tripId) {
        Trip trip = new TripDatabase().selectFirst(new LocalDatabase.Where<Trip>() {
            @Override
            public boolean isTrue(Trip trip) {
                return trip.isTripId(tripId);
            }
        });

        if(trip != null){
            trip.setTimestampLastUpdatedInMillis(System.currentTimeMillis());
            new TripDatabase().save(trip.getTripId(),trip);
        }
    }
}
