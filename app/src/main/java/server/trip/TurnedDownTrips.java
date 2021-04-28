package server.trip;

import java.util.List;
import cache.TurnedDownTripsDatabase;
import core.businessobjects.TurnedDownTrip;
import libraries.data.LocalDatabase;

/* returns turned down trips matching a criteria */
public class TurnedDownTrips {
    private boolean notFound = false;
    private List<TurnedDownTrip> trips;

    //use TurnedDownTrips.where() instead -- this constructor is hidden
    private TurnedDownTrips(){
    }

    public static TurnedDownTrips where(final String turnedDownTripId, final String rejectingTripId){
        TurnedDownTrips instance = new TurnedDownTrips();
        instance.init(turnedDownTripId,rejectingTripId);
        return instance;
    }

    private void init(final String turnedDownTripId, final String rejectingTripId) {
        List<TurnedDownTrip> results = new TurnedDownTripsDatabase().select(new LocalDatabase.Where<TurnedDownTrip>() {
            @Override
            public boolean isTrue(TurnedDownTrip turnedDownTrip) {
                return turnedDownTrip.isTripId(turnedDownTripId) && turnedDownTrip.isTurnedDownByTripId(rejectingTripId);
            }
        });


        if(results == null || results.size() < 1){
            notFound = true;
        }
        else{
            trips = results;
        }
    }

    public boolean isNotFound() {
        return notFound;
    }

    public boolean isFound() {
        return !isNotFound();
    }

    public List<TurnedDownTrip> getTrips() {
        return trips;
    }
}
