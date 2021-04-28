package server.admin.progress_data;

import core.businessobjects.admin.ProgressData;
import server.admin.DaysSinceFirstUser;
import server.admin.GetMutualRideShareProposals;
import server.admin.MonthsSinceFirstUser;

public class ProgressDataForRideShareProposalsApproved extends ProgressData {
    public ProgressDataForRideShareProposalsApproved() {

        setIndicatorDescription("Ride Share Proposals approved");
        setOverall(GetMutualRideShareProposals.overall());
        setToday(GetMutualRideShareProposals.today());
        setYesterday(GetMutualRideShareProposals.yesterday());
        setThisMonth(GetMutualRideShareProposals.thisMonth());
        setLastMonth(GetMutualRideShareProposals.lastMonth());

        long timestampOldest = GetMutualRideShareProposals.timestampFirst();
        long timestampMostRecent = GetMutualRideShareProposals.timestampMostRecent();

        long daysCovered = DaysSinceFirstUser.get(); //if it was all on same day, then it is one day

        int totalMatchesMade = GetMutualRideShareProposals.overall();
        if(daysCovered > 0){
            int perDay = (int)(totalMatchesMade / daysCovered);
            setPerDay(perDay);

            setTimestampFirst(timestampOldest);
            setTimestampMostRecent(timestampMostRecent);

            setPerMonth(totalMatchesMade / MonthsSinceFirstUser.get());
        }


    }
}
