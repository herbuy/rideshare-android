package server.trip;

import cache.TurnedDownTripsDatabase;
import core.businessobjects.TurnedDownTrip;

public class TurnDownTripResult {

    private String error = "";
    private boolean success = false;

    private TurnDownTripResult() {
    }

    public static TurnDownTripResult where(String fromTripId, String toTripId) {
        TurnDownTripResult result = new TurnDownTripResult();
        result.init(fromTripId,toTripId);
        return result;
    }

    private void init(String fromTripId, String toTripId) {
        if(TripTurnedDown.where(fromTripId,toTripId)){
            error = "Already turned down";
        }
        else{
            TurnedDownTrip turnedDownTrip = CreateTurnedDownTrip.where(fromTripId,toTripId);
            new TurnedDownTripsDatabase().save(turnedDownTrip.getRecordId(),turnedDownTrip);
            success = true;
        }

    }

    public String getError() {
        return error;
    }

    public boolean isSuccess() {
        return success;
    }
}
