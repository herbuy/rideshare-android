package server.admin;

public class DaysSinceFirstUser {
    public static long get(){
        return 1 + (TimestampSinceFirstUser.get() / 1000 / 3600 / 24);
    }
}
