package core.businessobjects;

public class MessageToTripActor {
    //actor
    private String actorId = "";
    private String actorName = "";
    private String actorProfilePic = "";

    //target
    private String targetUserId = "";
    private String targetUserName = "";
    private String targetUserProfilePic = "";

    //trip
    private String toTripId = "";
    private String fromTripId = "";

    private String tripOriginId = "";
    private String tripOriginName = "";

    private String tripDestinationId = "";
    private String tripDestinationName = "";

    private String tripDate = "";


    public boolean isSentByUser(String userId) {
        //none of the bad conditions is true and the condition we want is true
        return !(null == userId || actorId == null || userId.trim().equalsIgnoreCase("") || actorId.trim().equalsIgnoreCase("")) && actorId.trim().equalsIgnoreCase(userId.trim());
    }

    public boolean isSentToUser(String userId) {
        return !(null == userId || targetUserId == null || userId.trim().equalsIgnoreCase("") || targetUserId.trim().equalsIgnoreCase("")) && targetUserId.trim().equalsIgnoreCase(userId.trim());
    }

    public boolean isToTrip(String passedTripId){
        return passedTripId != null
                && !passedTripId.trim().equalsIgnoreCase("")
                && toTripId != null
                && !toTripId.trim().equalsIgnoreCase("")
                && toTripId.trim().equalsIgnoreCase(passedTripId.trim())
                ;

    }
    public boolean isFromTrip(String passedTripId){
        return passedTripId != null
                && !passedTripId.trim().equalsIgnoreCase("")
                && fromTripId != null
                && !fromTripId.trim().equalsIgnoreCase("")
                && fromTripId.trim().equalsIgnoreCase(passedTripId.trim())
                ;

    }

    public String getFromTripId() {
        return fromTripId;
    }

    public void setFromTripId(String fromTripId) {
        this.fromTripId = fromTripId;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorProfilePic() {
        return actorProfilePic;
    }

    public void setActorProfilePic(String actorProfilePic) {
        this.actorProfilePic = actorProfilePic;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }

    public String getTargetUserProfilePic() {
        return targetUserProfilePic;
    }

    public void setTargetUserProfilePic(String targetUserProfilePic) {
        this.targetUserProfilePic = targetUserProfilePic;
    }

    public String getToTripId() {
        return toTripId;
    }

    public void setToTripId(String toTripId) {
        this.toTripId = toTripId;
    }

    public String getTripOriginId() {
        return tripOriginId;
    }

    public void setTripOriginId(String tripOriginId) {
        this.tripOriginId = tripOriginId;
    }

    public String getTripOriginName() {
        return tripOriginName;
    }

    public void setTripOriginName(String tripOriginName) {
        this.tripOriginName = tripOriginName;
    }

    public String getTripDestinationId() {
        return tripDestinationId;
    }

    public void setTripDestinationId(String tripDestinationId) {
        this.tripDestinationId = tripDestinationId;
    }

    public String getTripDestinationName() {
        return tripDestinationName;
    }

    public void setTripDestinationName(String tripDestinationName) {
        this.tripDestinationName = tripDestinationName;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }
}
