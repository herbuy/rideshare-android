package server.admin.performace_reports;

import core.businessobjects.admin.PerformanceData;
import server.admin.GetAccountsCreated;
import server.admin.GetMutualRideShareProposals;
import server.admin.GetProposalsSent;
import server.admin.GetRidesShared;
import server.admin.GetSearchesConducted;
import server.admin.GetTripsCompleted;

public class PerformaceReportForToday extends PerformanceData {

    public PerformaceReportForToday() {
        setReportDescription("Today");

        setAccountsCreated(GetAccountsCreated.today());
        setSearchesConducted(GetSearchesConducted.today());
        setProposalsSent(GetProposalsSent.today());
        setProposalsApproved(GetMutualRideShareProposals.today());
        setRidesShared(GetRidesShared.today());
        setTripsCompleted(GetTripsCompleted.today());
    }
}
