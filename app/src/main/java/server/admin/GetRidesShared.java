package server.admin;

import java.util.Comparator;
import cache.FamilyDatabase;
import core.businessobjects.Family;
import libraries.data.LocalDatabase;
import libraries.HerbuyCalendar;

public class GetRidesShared {

    public static int overall(){
        return new FamilyDatabase().count();
    }
    public static int today(){
        return new FamilyDatabase().countWhere(new LocalDatabase.Where<Family>() {
            @Override
            public boolean isTrue(Family item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isToday();
            }
        });
    }

    public static int yesterday(){
        return new FamilyDatabase().countWhere(new LocalDatabase.Where<Family>() {
            @Override
            public boolean isTrue(Family item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isYesterday();
            }
        });
    }

    public static int thisMonth(){
        return new FamilyDatabase().countWhere(new LocalDatabase.Where<Family>() {
            @Override
            public boolean isTrue(Family item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isThisMonth();
            }
        });
    }

    public static int lastMonth(){
        return new FamilyDatabase().countWhere(new LocalDatabase.Where<Family>() {
            @Override
            public boolean isTrue(Family item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isLastMonth();
            }
        });
    }

    public static long timestampFirst(){
        Family result = new FamilyDatabase().first(new Comparator<Family>() {
            @Override
            public int compare(Family lhs, Family rhs) {
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
        Family result = new FamilyDatabase().first(new Comparator<Family>() {
            @Override
            public int compare(Family lhs, Family rhs) {
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
