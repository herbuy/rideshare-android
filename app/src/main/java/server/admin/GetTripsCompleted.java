package server.admin;

import java.util.Comparator;

import cache.CompletedTripsDatabase;
import core.businessobjects.CompletedTrip;
import libraries.data.LocalDatabase;
import libraries.HerbuyCalendar;

public class GetTripsCompleted {

    public static int overall(){
        return new CompletedTripsDatabase().count();
    }

    public static int today(){
        return new CompletedTripsDatabase().countWhere(new LocalDatabase.Where<CompletedTrip>() {
            @Override
            public boolean isTrue(CompletedTrip item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isToday();
            }
        });
    }

    public static int yesterday(){
        return new CompletedTripsDatabase().countWhere(new LocalDatabase.Where<CompletedTrip>() {
            @Override
            public boolean isTrue(CompletedTrip item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isYesterday();
            }
        });
    }

    public static int thisMonth(){
        return new CompletedTripsDatabase().countWhere(new LocalDatabase.Where<CompletedTrip>() {
            @Override
            public boolean isTrue(CompletedTrip item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isThisMonth();
            }
        });
    }

    public static int lastMonth(){
        return new CompletedTripsDatabase().countWhere(new LocalDatabase.Where<CompletedTrip>() {
            @Override
            public boolean isTrue(CompletedTrip item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isLastMonth();
            }
        });
    }

    public static long timestampFirst(){
        CompletedTrip result = new CompletedTripsDatabase().first(new Comparator<CompletedTrip>() {
            @Override
            public int compare(CompletedTrip lhs, CompletedTrip rhs) {

                if (lhs.getTimestampCreatedInMillis() < rhs.getTimestampCreatedInMillis()) {
                    return -1;
                } else if (lhs.getTimestampCreatedInMillis() > rhs.getTimestampCreatedInMillis()){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
        return result == null ? 0 : result.getTimestampCreatedInMillis();
    }

    public static long timestampMostRecent() {
        CompletedTrip result = new CompletedTripsDatabase().first(new Comparator<CompletedTrip>() {
            @Override
            public int compare(CompletedTrip lhs, CompletedTrip rhs) {

                if (lhs.getTimestampCreatedInMillis() > rhs.getTimestampCreatedInMillis()) {
                    return -1;
                } else if (lhs.getTimestampCreatedInMillis() < rhs.getTimestampCreatedInMillis()){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
        return result == null ? 0 : result.getTimestampCreatedInMillis();
    }
}
