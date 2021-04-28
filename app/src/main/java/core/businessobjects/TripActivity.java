package core.businessobjects;

public class TripActivity {

    //activity [that was performed e.g scheduled trip
    private String activityId;
    private String activityType;
    private String activityDate;
    private String activityTime;

    //object [upon which the activity was performed] e.g a trip
    private String objectId; //the object to which the activity pertains, if applicable
    private String objectType; //the type of object, such as trip, etc
    private String objectOwnerId;

    //actor [the perosn who performed the activity]
    private String actorId;
    private String actorName;
    private String actorProfilePic;

    //target [to other person in the activity, if applicable
    private String targetUserId;
    private String targetUserName;
    private String targetUserProfilePic;

    //viewer id [the person authorized to view the activity
    private String viewerId;

    //trip details
    private String tripId;
    private String tripDate;
    private String tripTime;
    private String tripDestinationId;
    private String tripDestinationName;
    private String tripOriginId;
    private String tripOriginName;
    private String travellerId;
    private String travellerName;
    private String travellerProfilePic;

    private String ttRequestId;//if someone sent a ttrequest, so we can accept that tt request or reject or revoke
    private String ttRequestDate; //can be different from the notification date
    private String ttRequestTime;



    //a list of the different types of activities
    public static class Type{

        public static class Trip{
            public static final String RECIPIENT_SCHEDULED_TRIP = "RECIPIENT_SCHEDULED_TRIP";
            public static final String ACTOR_SCHEDULED_TRIP = "ACTOR_SCHEDULED_TRIP";

            public static final String ACTOR_SENT_TT_REQUEST = "ACTOR_SENT_TT_REQUEST";
            public static final String RECIPIENT_SENT_TT_REQUEST = "RECIPIENT_SENT_TT_REQUEST";

            public static final String ACCEPTED_TT_REQUEST = "ACCEPTED_TT_REQUEST";
            public static final String SENT_MESSAGE = "SENT_MESSAGE";

        }
    }

    public static class SupportedObjectTypes{

        public static class Trip{
            public static final String TRIP = "TRIP";

        }
    }


    public TripActivity(String activityId, String activityType, String activityDate, String activityTime, String objectId, String objectType, String objectOwnerId, String actorId, String actorName, String actorProfilePic, String targetUserId, String targetUserName, String targetUserProfilePic, String viewerId) {
        this.activityId = activityId;
        this.activityType = activityType;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
        this.objectId = objectId;
        this.objectType = objectType;
        this.objectOwnerId = objectOwnerId;
        this.actorId = actorId;
        this.actorName = actorName;
        this.actorProfilePic = actorProfilePic;
        this.targetUserId = targetUserId;
        this.targetUserName = targetUserName;
        this.targetUserProfilePic = targetUserProfilePic;
        this.viewerId = viewerId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectOwnerId() {
        return objectOwnerId;
    }

    public void setObjectOwnerId(String objectOwnerId) {
        this.objectOwnerId = objectOwnerId;
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

    public String getViewerId() {
        return viewerId;
    }

    public void setViewerId(String viewerId) {
        this.viewerId = viewerId;
    }
}
