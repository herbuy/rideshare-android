package server.admin;

import java.util.Comparator;

import cache.FamilyMemberDatabase;
import core.businessobjects.FamilyMember;
import libraries.data.LocalDatabase;
import libraries.HerbuyCalendar;

public class GetMutualRideShareProposals {

    public static int overall(){
        return new FamilyMemberDatabase().count();
    }
    public static int today(){
        return new FamilyMemberDatabase().countWhere(new LocalDatabase.Where<FamilyMember>() {
            @Override
            public boolean isTrue(FamilyMember item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isToday();
            }
        });
    }

    public static int yesterday(){
        return new FamilyMemberDatabase().countWhere(new LocalDatabase.Where<FamilyMember>() {
            @Override
            public boolean isTrue(FamilyMember item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isYesterday();
            }
        });
    }

    public static int thisMonth(){
        return new FamilyMemberDatabase().countWhere(new LocalDatabase.Where<FamilyMember>() {
            @Override
            public boolean isTrue(FamilyMember item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isThisMonth();
            }
        });
    }

    public static int lastMonth(){
        return new FamilyMemberDatabase().countWhere(new LocalDatabase.Where<FamilyMember>() {
            @Override
            public boolean isTrue(FamilyMember item) {
                return new HerbuyCalendar(item.getTimestampCreatedInMillis()).isLastMonth();
            }
        });
    }

    public static long timestampFirst(){
        FamilyMember result = new FamilyMemberDatabase().first(new Comparator<FamilyMember>() {
            @Override
            public int compare(FamilyMember lhs, FamilyMember rhs) {
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
        FamilyMember result = new FamilyMemberDatabase().first(new Comparator<FamilyMember>() {
            @Override
            public int compare(FamilyMember lhs, FamilyMember rhs) {
                if (lhs.getTimestampCreatedInMillis() < rhs.getTimestampCreatedInMillis()) {
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
