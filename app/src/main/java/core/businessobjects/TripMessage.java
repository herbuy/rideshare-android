package core.businessobjects;


public class TripMessage {

    private String messageId = "";
    private String toTripId = "";
    private String fromTripId = "";
    private String messageType = "";
    private String messageText = "";
    private boolean systemMessage = true;
    private String transactionId = ""; //identifies a group of messages sent in one activity, e.g for deleting
    private boolean seen = false;
    private long timestampCreated;


    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getToTripId() {
        return toTripId;
    }

    public void setToTripId(String toTripId) {
        this.toTripId = toTripId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public boolean isSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(boolean systemMessage) {
        this.systemMessage = systemMessage;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setFromTripId(String fromTripId) {
        this.fromTripId = fromTripId;
    }

    public String getFromTripId() {
        return fromTripId;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public long getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(long timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public boolean isToTripId(String tripId) {
        return tripId != null
                && !tripId.trim().equalsIgnoreCase("")
                && this.toTripId != null
                && !this.toTripId.trim().equalsIgnoreCase("")
                && this.toTripId.trim().equalsIgnoreCase(tripId.trim())
                ;
    }

    public boolean isNotSeen(){
        return !isSeen();
    }

    public class Type {
        public static final String TRIP_SCHEDULED = "TRIP_SCHEDULED";
    }
}
