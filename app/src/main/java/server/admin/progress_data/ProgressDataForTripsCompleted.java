package server.admin.progress_data;

import cache.CompletedTripsDatabase;
import core.businessobjects.CompletedTrip;
import core.businessobjects.admin.ProgressData;
import libraries.data.LocalDatabase;
import server.admin.DaysSinceFirstUser;
import server.admin.GetTripsCompleted;
import server.admin.MonthsSinceFirstUser;

public class ProgressDataForTripsCompleted extends ProgressData {

    public ProgressDataForTripsCompleted() {

        new CompletedTripsDatabase().forEach(new LocalDatabase.ForEach<CompletedTrip>() {
            @Override
            public void run(CompletedTrip object) {
                if(object.getTimestampCreatedInMillis() == 0){
                    object.setTimestampCreatedInMillis(System.currentTimeMillis());
                    new CompletedTripsDatabase().save(object.getRecordId(),object);
                }
            }
        });

        setIndicatorDescription("Trips Completed");
        setOverall(GetTripsCompleted.overall());
        setToday(GetTripsCompleted.today());
        setYesterday(GetTripsCompleted.yesterday());
        setThisMonth(GetTripsCompleted.thisMonth());
        setLastMonth(GetTripsCompleted.lastMonth());

        long timestampOldest = GetTripsCompleted.timestampFirst();
        long timestampMostRecent = GetTripsCompleted.timestampMostRecent();

        long daysCovered = DaysSinceFirstUser.get();

        int totalTripsCompleted = GetTripsCompleted.overall();
        if(daysCovered > 0){
            int perDay = (int)(totalTripsCompleted / daysCovered);
            setPerDay(perDay);

            setTimestampFirst(timestampOldest);
            setTimestampMostRecent(timestampMostRecent);

            setPerMonth(totalTripsCompleted / MonthsSinceFirstUser.get());
        }


    }
}
