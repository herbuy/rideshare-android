package server.admin.progress_data;

import cache.TripDatabase;
import core.businessobjects.admin.ProgressData;
import server.admin.DaysSinceFirstUser;
import server.admin.GetSearchesConducted;
import server.admin.MonthsSinceFirstUser;
;

public class ProgressDataForSearchesConducted extends ProgressData {
    public ProgressDataForSearchesConducted() {


        setIndicatorDescription("Searches conducted");
        setOverall(GetSearchesConducted.overall());
        setToday(GetSearchesConducted.today());
        setYesterday(GetSearchesConducted.yesterday());
        setThisMonth(GetSearchesConducted.thisMonth());
        setLastMonth(GetSearchesConducted.lastMonth());

        long timestampOldest = GetSearchesConducted.timestampFirst();
        long timestampMostRecent = GetSearchesConducted.timestampMostRecent();

        long daysCovered = DaysSinceFirstUser.get(); //if it was all on same day, then it is one day

        int totalSearchesConducted = new TripDatabase().count();
        if(daysCovered > 0){
            int perDay = (int)(totalSearchesConducted / daysCovered);
            setPerDay(perDay);

            setTimestampFirst(timestampOldest);
            setTimestampMostRecent(timestampMostRecent);

            setPerMonth(totalSearchesConducted / MonthsSinceFirstUser.get());
        }


    }
}
