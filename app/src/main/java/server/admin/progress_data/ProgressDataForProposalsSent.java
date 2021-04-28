package server.admin.progress_data;

import cache.ProposalDatabase;
import core.businessobjects.Proposal;
import core.businessobjects.admin.ProgressData;
import libraries.data.LocalDatabase;
import server.admin.DaysSinceFirstUser;
import server.admin.GetProposalsSent;
import server.admin.MonthsSinceFirstUser;

public class ProgressDataForProposalsSent extends ProgressData {
    public ProgressDataForProposalsSent() {
        new ProposalDatabase().forEach(new LocalDatabase.ForEach<Proposal>() {
            @Override
            public void run(Proposal object) {
                if (object.getTimestampCreatedInMillis() == 0) {
                    object.setTimestampCreatedInMillis(System.currentTimeMillis());
                    new ProposalDatabase().save(object.getProposalId(), object);
                }
            }
        });

        setIndicatorDescription("Travel Proposals sent");
        setOverall(GetProposalsSent.overall());
        setToday(GetProposalsSent.today());
        setYesterday(GetProposalsSent.yesterday());
        setThisMonth(GetProposalsSent.thisMonth());
        setLastMonth(GetProposalsSent.lastMonth());

        long timestampOldest = GetProposalsSent.timestampFirst();
        long timestampMostRecent = GetProposalsSent.timestampMostRecent();

        long daysCovered = DaysSinceFirstUser.get(); //if it was all on same day, then it is one day

        int totalProposalSent = new ProposalDatabase().count();
        if(daysCovered > 0){
            int perDay = (int)(totalProposalSent / daysCovered);
            setPerDay(perDay);

            setTimestampFirst(timestampOldest);
            setTimestampMostRecent(timestampMostRecent);
            setPerMonth(totalProposalSent / MonthsSinceFirstUser.get());
        }


    }
}
