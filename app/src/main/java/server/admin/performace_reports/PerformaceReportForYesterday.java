package server.admin.performace_reports;

import core.businessobjects.admin.PerformanceData;
import server.admin.GetAccountsCreated;
import server.admin.GetMutualRideShareProposals;
import server.admin.GetProposalsSent;
import server.admin.GetRidesShared;
import server.admin.GetSearchesConducted;
import server.admin.GetTripsCompleted;

public class PerformaceReportForYesterday extends PerformanceData {

    public PerformaceReportForYesterday() {
        setReportDescription("Yesterday");

        setAccountsCreated(GetAccountsCreated.yesterday());
        setSearchesConducted(GetSearchesConducted.yesterday());
        setSearchesConducted(GetSearchesConducted.yesterday());
        setProposalsSent(GetProposalsSent.yesterday());
        setProposalsApproved(GetMutualRideShareProposals.yesterday());
        setRidesShared(GetRidesShared.yesterday());
        setTripsCompleted(GetTripsCompleted.yesterday());
    }
}
