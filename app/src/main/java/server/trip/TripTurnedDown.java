package server.trip;

import cache.TurnedDownTripsDatabase;
import core.businessobjects.TurnedDownTrip;
import libraries.data.LocalDatabase;

public class TripTurnedDown {
    private boolean aTrue = false;

    public static boolean where(String fromTripId, String toTripId) {
        TripTurnedDown instance = new TripTurnedDown();
        instance.init(fromTripId,toTripId);
        return instance.isTrue();
    }

    private void init(final String fromTripId, final String toTripId) {
        TurnedDownTrip instance = new TurnedDownTripsDatabase().selectFirst(new LocalDatabase.Where<TurnedDownTrip>() {
            @Override
            public boolean isTrue(TurnedDownTrip turnedDownTrip) {
                return turnedDownTrip.isTripId(toTripId) && turnedDownTrip.isTurnedDownByTripId(fromTripId);
            }
        });
        aTrue = instance != null;
    }

    public boolean isTrue() {
        return aTrue;
    }
}
