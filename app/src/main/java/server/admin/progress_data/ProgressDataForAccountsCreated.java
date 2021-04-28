package server.admin.progress_data;

import cache.UserDatabase;
import core.businessobjects.admin.ProgressData;
import server.admin.DaysSinceFirstUser;
import server.admin.GetAccountsCreated;
import server.admin.MonthsSinceFirstUser;

public class ProgressDataForAccountsCreated extends ProgressData {
    public ProgressDataForAccountsCreated() {

        setIndicatorDescription("Accounts created");
        setOverall(GetAccountsCreated.overall());
        setToday(GetAccountsCreated.today());
        setYesterday(GetAccountsCreated.yesterday());
        setThisMonth(GetAccountsCreated.thisMonth());
        setLastMonth(GetAccountsCreated.lastMonth());

        long timestampOldest = GetAccountsCreated.timestampFirst();
        long timestampNewestSignup = GetAccountsCreated.timestampMostRecent();

        long daysCovered = DaysSinceFirstUser.get(); //if it was all on same day, then it is one day

        int totalSignups = new UserDatabase().count();
        if(daysCovered > 0){
            int signupsPerDay = (int)(totalSignups / daysCovered);
            setPerDay(signupsPerDay);

            setTimestampFirst(timestampOldest);
            setTimestampMostRecent(timestampNewestSignup);

            setPerMonth(totalSignups / MonthsSinceFirstUser.get());
        }


    }
}
