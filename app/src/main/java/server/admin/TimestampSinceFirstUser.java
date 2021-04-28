package server.admin;

public class TimestampSinceFirstUser {
    public static long get(){
        return System.currentTimeMillis() - TimestampOfFirstUser.get();
    }
}
