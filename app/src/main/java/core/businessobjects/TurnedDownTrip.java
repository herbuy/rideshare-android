package core.businessobjects;

public class TurnedDownTrip {
    private String recordId;
    private String tripId;
    private String rejectingTripId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getRejectingTripId() {
        return rejectingTripId;
    }

    public void setRejectingTripId(String rejectingTripId) {
        this.rejectingTripId = rejectingTripId;
    }

    public boolean isTripId(String tripId) {
        return tripId != null
                && !tripId.trim().equalsIgnoreCase("")
                && this.tripId != null
                && !this.tripId.trim().equalsIgnoreCase("")
                && this.tripId.trim().equalsIgnoreCase(tripId.trim())
                ;
    }

    public boolean isTurnedDownByTripId(String tripId) {
        return tripId != null
                && !tripId.trim().equalsIgnoreCase("")
                && this.rejectingTripId != null
                && !this.rejectingTripId.trim().equalsIgnoreCase("")
                && this.rejectingTripId.trim().equalsIgnoreCase(tripId.trim())
                ;
    }


}
