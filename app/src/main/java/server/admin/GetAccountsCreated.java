package server.admin;

import java.util.Comparator;

import cache.UserDatabase;
import core.businessobjects.User;
import libraries.data.LocalDatabase;
import libraries.HerbuyCalendar;

public class GetAccountsCreated {

    public static int overall(){
        return new UserDatabase().count();
    }
    public static int today(){
        return new UserDatabase().countWhere(new LocalDatabase.Where<User>() {
            @Override
            public boolean isTrue(User user) {
                return new HerbuyCalendar(user.getTimestampRegisteredInMillis()).isToday();
            }
        });
    }

    public static int yesterday(){
        return new UserDatabase().countWhere(new LocalDatabase.Where<User>() {
            @Override
            public boolean isTrue(User user) {
                return new HerbuyCalendar(user.getTimestampRegisteredInMillis()).isYesterday();
            }
        });
    }

    public static int thisMonth(){
        return new UserDatabase().countWhere(new LocalDatabase.Where<User>() {
            @Override
            public boolean isTrue(User user) {
                return new HerbuyCalendar(user.getTimestampRegisteredInMillis()).isThisMonth();
            }
        });
    }

    public static int lastMonth(){
        return new UserDatabase().countWhere(new LocalDatabase.Where<User>() {
            @Override
            public boolean isTrue(User user) {
                return new HerbuyCalendar(user.getTimestampRegisteredInMillis()).isLastMonth();
            }
        });
    }

    public static long timestampFirst(){
        return TimestampOfFirstUser.get();
    }

    public static long timestampMostRecent() {
        User result = new UserDatabase().first(new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                if (lhs.getTimestampRegisteredInMillis() > rhs.getTimestampRegisteredInMillis()) {
                    return -1;
                } else if (lhs.getTimestampRegisteredInMillis() < rhs.getTimestampRegisteredInMillis()){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
        return result == null ?0 : result.getTimestampRegisteredInMillis();
    }
}
