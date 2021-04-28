package server.admin;

public class MonthsSinceFirstUser {
    public static int get(){
        return 1 + (int)(DaysSinceFirstUser.get() / 30);
    }
}
