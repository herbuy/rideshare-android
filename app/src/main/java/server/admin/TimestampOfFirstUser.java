package server.admin;

import java.util.Comparator;

import cache.UserDatabase;
import core.businessobjects.User;

public class TimestampOfFirstUser {
    public static long get() {
        User result = new UserDatabase().first(new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                if (lhs.getTimestampRegisteredInMillis() < rhs.getTimestampRegisteredInMillis()) {
                    return -1;
                } else if (lhs.getTimestampRegisteredInMillis() > rhs.getTimestampRegisteredInMillis()){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
        return result == null ? 0 : result.getTimestampRegisteredInMillis();
    }
}
