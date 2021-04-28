package server.trip;

import java.util.Map;

import cache.FamilyDatabase;
import cache.TripDatabase;
import core.businessobjects.Family;
import core.businessobjects.Trip;
import libraries.UnixTimestampInMillis;
import libraries.data.LocalDatabase;
import libraries.HerbuyCalendar;
import server.families.GetFamilyMembers;

public class ResultForGetSearchAnalytics {
    public ResultForGetSearchAnalytics() {

        /*
        searchType();
        mostCommonOrigins();
        topDestinations();

        popularTravelDays();
        popularTravelMonths();
        popularTravelDayStages();
        popularTravelHours();




        passengerCountSought();
        specifyFuelCharge();
        byPreferedTravelDate();*/

    }

    public Map<String,Integer> passengerCountSought() {
        return new TripDatabase().countByKey(new LocalDatabase.CountByKey<Trip>() {
            @Override
            public String getKey(Trip trip) {
                if(trip == null || trip.isPassenger()){
                    return "Not Applicable";
                }

                return trip.getMaxConcurrentTTMarriages().toString() ;
            }
        });
    }

    public Map<String,Integer> specifyFuelCharge() {
        return new TripDatabase().countByKey(new LocalDatabase.CountByKey<Trip>() {
            @Override
            public String getKey(Trip trip) {
                if(trip == null){
                    return "Others";
                }

                return trip.getFuelCharge() <= 0 ? "Never" : "Do Specify" ;
            }
        });

    }

    public Map<String,Integer> popularTravelHours() {
        return new TripDatabase().countByKey(new LocalDatabase.CountByKey<Trip>() {
            @Override
            public String getKey(Trip trip) {

                if(trip == null || trip.getTripTime() == null || trip.getTripTime().trim().equals("")){
                    return "Others";
                }

                return new HerbuyCalendar(UnixTimestampInMillis.fromTime(trip.getTripTime().trim())).getHourOnTwelveHourClock();
            }
        });

    }

    public Map<String,Integer> popularTravelDayStages() {
        return new TripDatabase().countByKey(new LocalDatabase.CountByKey<Trip>() {
            @Override
            public String getKey(Trip trip) {
                if(trip == null || trip.getTripTime() == null || trip.getTripTime().trim().equals("")){
                    return "Others";
                }

                return new HerbuyCalendar(UnixTimestampInMillis.fromTime(trip.getTripTime().trim())).getDayStage();
            }
        });
    }

    public Map<String,Integer> byPreferedTravelDate() {
        return new TripDatabase().countByKey(new LocalDatabase.CountByKey<Trip>() {
            @Override
            public String getKey(Trip trip) {
                if(trip == null || trip.getTripDate() == null || trip.getTripDate().trim().equals("")){
                    return "Others";
                }

                return new HerbuyCalendar(UnixTimestampInMillis.fromDate(trip.getTripDate().trim())).getFriendlyDate();
            }
        });

    }

    public Map<String,Integer> popularTravelDays() {
        return new TripDatabase().countByKey(new LocalDatabase.CountByKey<Trip>() {
            @Override
            public String getKey(Trip trip) {
                if(trip == null || trip.getTripDate() == null || trip.getTripDate().trim().equals("")){
                    return "Others";
                }

                return new HerbuyCalendar(UnixTimestampInMillis.fromDate(trip.getTripDate().trim())).getDayNameShort();
            }
        });
    }

    public Map<String,Integer> popularTravelMonths() {
        return new TripDatabase().countByKey(new LocalDatabase.CountByKey<Trip>() {
            @Override
            public String getKey(Trip trip) {
                if(trip == null || trip.getTripDate() == null || trip.getTripDate().trim().equals("")){
                    return "Others";
                }

                return new HerbuyCalendar(UnixTimestampInMillis.fromDate(trip.getTripDate().trim())).getMonthNameShort();
            }
        });

    }


    public Map<String,Integer> searchType() {
        //========= BY APPLICANT TYPE /PURPOSE
        return new TripDatabase().countByKey(new LocalDatabase.CountByKey<Trip>() {
            @Override
            public String getKey(Trip trip) {
                return trip.isDriver() ? "Passenger Searches" : "Driver searches";
            }
        });

    }

    public Map<String,Integer> mostCommonOrigins() {
        //BY ORIGIN
        return new TripDatabase().countByKey(new LocalDatabase.CountByKey<Trip>() {
            @Override
            public String getKey(Trip trip) {
                if(trip == null || trip.getOriginName() == null || trip.getOriginName().trim().equals("")){
                    return "Others";
                }

                return trip.getOriginName().trim();
            }
        });
    }

    public Map<String,Integer> topDestinations() {
        return new TripDatabase().countByKey(new LocalDatabase.CountByKey<Trip>() {
            @Override
            public String getKey(Trip trip) {
                if(trip == null || trip.getDestinationName() == null || trip.getDestinationName().trim().equals("")){
                    return "Others";
                }

                return trip.getDestinationName().trim();
            }
        });

    }

    public Map<String,Integer> travellersPerRide() {
        //========= BY APPLICANT TYPE /PURPOSE
        return new FamilyDatabase().countByKey(new LocalDatabase.CountByKey<Family>() {
            @Override
            public String getKey(Family family) {
                Integer totalFamilyMembers = GetFamilyMembers.where(family.getFamilyId(), null).size();
                return totalFamilyMembers.toString();

            }
        });

    }
}
