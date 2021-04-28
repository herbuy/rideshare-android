package core.businessobjects;

public class Proposal extends MessageToTripActor {

    //when
    private String proposalId = "";
    private String proposalDate = "";

    private String proposalStatus = Status.PENDING;
    private long timestampCreatedInMillis;
    private String familyId = "";

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getProposalDate() {
        return proposalDate;
    }

    public void setProposalDate(String proposalDate) {
        this.proposalDate = proposalDate;
    }

    public String getProposalStatus() {
        return proposalStatus;
    }

    public void setProposalStatus(String proposalStatus) {
        this.proposalStatus = proposalStatus;
    }

    public boolean isStatus(String requestStatus) {
        return
                requestStatus != null
                        && proposalStatus != null
                        && !requestStatus.trim().equalsIgnoreCase("")
                        && !proposalStatus.trim().equalsIgnoreCase("")
                        && proposalStatus.trim().equalsIgnoreCase(requestStatus.trim());
    }


    public boolean isPending(){
        return isStatus(Status.PENDING);
    }
    public boolean isAccepted(){
        return isStatus(Status.ACCEPTED);
    }
    public boolean isRejected(){
        return isStatus(Status.REJECTED);
    }
    public boolean isExpired(){
        return isStatus(Status.EXPIRED);
    }
    public boolean isCancelled(){
        return isStatus(Status.CANCELLED);
    }

    public void accept() {
        this.proposalStatus = Status.ACCEPTED;
    }

    public void reject() {
        this.proposalStatus = Status.REJECTED;
    }

    public void cancel() {
        this.proposalStatus = Status.CANCELLED;
    }

    public void expire() {
        this.proposalStatus = Status.EXPIRED;
    }

    public long getTimestampCreatedInMillis() {
        return timestampCreatedInMillis;
    }

    public void setTimestampCreatedInMillis(long timestampCreatedInMillis) {
        this.timestampCreatedInMillis = timestampCreatedInMillis;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }




    public interface Status {
        String PENDING = "PENDING";
        String ACCEPTED = "ACCEPTED";
        String REJECTED = "REJECTED";
        String EXPIRED = "EXPIRED";
        String CANCELLED = "CANCELLED";
    }

}
