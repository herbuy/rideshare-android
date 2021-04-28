package core.businessobjects.admin;


public class PerformanceData {

    private String reportDescription; //e.g today, this month, OnMondays, In Jan, Monring Performance, last 7 days
    private int accountsCreated;
    private int searchesConducted;
    private int proposalsSent; //select where month is this month
    private int proposalsApproved;
    private int ridesShared;
    private int tripsCompleted;

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public int getAccountsCreated() {
        return accountsCreated;
    }

    public void setAccountsCreated(int accountsCreated) {
        this.accountsCreated = accountsCreated;
    }

    public int getSearchesConducted() {
        return searchesConducted;
    }

    public void setSearchesConducted(int searchesConducted) {
        this.searchesConducted = searchesConducted;
    }

    public int getProposalsSent() {
        return proposalsSent;
    }

    public void setProposalsSent(int proposalsSent) {
        this.proposalsSent = proposalsSent;
    }

    public int getProposalsApproved() {
        return proposalsApproved;
    }

    public void setProposalsApproved(int proposalsApproved) {
        this.proposalsApproved = proposalsApproved;
    }

    public int getRidesShared() {
        return ridesShared;
    }

    public void setRidesShared(int ridesShared) {
        this.ridesShared = ridesShared;
    }

    public int getTripsCompleted() {
        return tripsCompleted;
    }

    public void setTripsCompleted(int tripsCompleted) {
        this.tripsCompleted = tripsCompleted;
    }
}
