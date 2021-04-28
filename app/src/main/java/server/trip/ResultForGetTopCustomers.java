package server.trip;

import java.util.Map;

import cache.CompletedTripsDatabase;
import cache.TripDatabase;
import core.businessobjects.CompletedTrip;
import core.businessobjects.Trip;
import libraries.data.LocalDatabase;
import server.Enrich;

public class ResultForGetTopCustomers {


    public Map<String,Integer> bySearchesConducted() {
        return new TripDatabase().countByKey(new LocalDatabase.CountByKey<Trip>() {
            @Override
            public String getKey(Trip trip) {
                Enrich.trip(trip,null);
                if(trip == null || trip.getForUserName() == null || trip.getForUserName().trim().equals("")){
                    return "Others";
                }

                return trip.getForUserName().trim();
            }
        });
    }



    Map<String,Integer> byTripsCompleted() {

        return new CompletedTripsDatabase().countByKey(new LocalDatabase.CountByKey<CompletedTrip>() {
            @Override
            public String getKey(CompletedTrip trip) {
                Enrich.completedTrip(trip);
                return trip.getUserNameCompleted();
            }
        });
    }

}
