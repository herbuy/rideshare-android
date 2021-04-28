package core.businessobjects;

public class MutualProposal {
    private String recordId = "";
    private String tripIdFirstToPropose = "";
    private String tripIdSecondToPropose = "";
    private long timestampCreatedInMillis;


    public String getTripIdFirstToPropose() {
        return tripIdFirstToPropose;
    }

    public void setTripIdFirstToPropose(String tripIdFirstToPropose) {
        this.tripIdFirstToPropose = tripIdFirstToPropose;
    }

    public String getTripIdSecondToPropose() {
        return tripIdSecondToPropose;
    }

    public void setTripIdSecondToPropose(String tripIdSecondToPropose) {
        this.tripIdSecondToPropose = tripIdSecondToPropose;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordId() {
        return recordId;
    }

    public boolean firstToProposeIsTripId(String tripIdFirstToPropose) {
        return tripIdFirstToPropose != null
                && !tripIdFirstToPropose.trim().equalsIgnoreCase("")
                && this.tripIdFirstToPropose != null
                && !this.tripIdFirstToPropose.trim().equalsIgnoreCase("")
                && this.tripIdFirstToPropose.trim().equalsIgnoreCase(tripIdFirstToPropose.trim())
                ;
    }

    public boolean secondToProposeIsTripId(String tripIdSecondToPropose) {
        return tripIdSecondToPropose != null
                && !tripIdSecondToPropose.trim().equalsIgnoreCase("")
                && this.tripIdSecondToPropose != null
                && !this.tripIdSecondToPropose.trim().equalsIgnoreCase("")
                && this.tripIdSecondToPropose.trim().equalsIgnoreCase(tripIdSecondToPropose.trim())
                ;
    }

    public void setTimestampCreatedInMillis(long timestampCreatedInMillis) {
        this.timestampCreatedInMillis = timestampCreatedInMillis;
    }

    public long getTimestampCreatedInMillis() {
        return timestampCreatedInMillis;
    }
}
