package server.trip;

import java.util.UUID;

import core.businessobjects.TurnedDownTrip;

public class CreateTurnedDownTrip {
    public static TurnedDownTrip where(String fromTripId, String toTripId) {
        TurnedDownTrip turnedDownTrip = new TurnedDownTrip();
        turnedDownTrip.setRecordId(UUID.randomUUID().toString());
        turnedDownTrip.setTripId(toTripId);
        turnedDownTrip.setRejectingTripId(fromTripId);
        return turnedDownTrip;
    }
}
