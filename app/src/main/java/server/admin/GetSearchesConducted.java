package server.admin;

import java.util.Comparator;

import cache.TripDatabase;
import core.businessobjects.Trip;
import libraries.data.LocalDatabase;
import libraries.HerbuyCalendar;

public class GetSearchesConducted {

    public static int overall(){
        return new TripDatabase().count();
    }
    public static int today(){
        return new TripDatabase().countWhere(new LocalDatabase.Where<Trip>() {
            @Override
            public boolean isTrue(Trip item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isToday();
            }
        });
    }

    public static int yesterday(){
        return new TripDatabase().countWhere(new LocalDatabase.Where<Trip>() {
            @Override
            public boolean isTrue(Trip item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isYesterday();
            }
        });
    }

    public static int thisMonth(){
        return new TripDatabase().countWhere(new LocalDatabase.Where<Trip>() {
            @Override
            public boolean isTrue(Trip item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isThisMonth();
            }
        });
    }

    public static int lastMonth(){
        return new TripDatabase().countWhere(new LocalDatabase.Where<Trip>() {
            @Override
            public boolean isTrue(Trip item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isLastMonth();
            }
        });
    }

    public static long timestampFirst(){
        Trip result = new TripDatabase().first(new Comparator<Trip>() {
            @Override
            public int compare(Trip lhs, Trip rhs) {
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
        Trip result = new TripDatabase().first(new Comparator<Trip>() {
            @Override
            public int compare(Trip lhs, Trip rhs) {
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
