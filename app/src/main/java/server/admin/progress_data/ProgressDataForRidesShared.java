package server.admin.progress_data;

import core.businessobjects.admin.ProgressData;
import server.admin.DaysSinceFirstUser;
import server.admin.GetRidesShared;
import server.admin.MonthsSinceFirstUser;

public class ProgressDataForRidesShared extends ProgressData {

    public ProgressDataForRidesShared() {

        setIndicatorDescription("Rides Shared");
        setOverall(GetRidesShared.overall());
        setToday(GetRidesShared.today());
        setYesterday(GetRidesShared.yesterday());
        setThisMonth(GetRidesShared.thisMonth());
        setLastMonth(GetRidesShared.lastMonth());

        long timestampOldest = GetRidesShared.timestampFirst();
        long timestampMostRecent = GetRidesShared.timestampMostRecent();

        long daysCovered = DaysSinceFirstUser.get();

        int totalRidesShared = GetRidesShared.overall();
        if(daysCovered > 0){
            int perDay = (int)(totalRidesShared / daysCovered);
            setPerDay(perDay);

            setTimestampFirst(timestampOldest);
            setTimestampMostRecent(timestampMostRecent);

            setPerMonth(totalRidesShared / MonthsSinceFirstUser.get());
        }


    }
}
