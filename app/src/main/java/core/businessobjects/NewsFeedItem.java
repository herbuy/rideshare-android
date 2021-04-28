package core.businessobjects;

public class NewsFeedItem {
    //actor
    private String actorId;
    private String actorName;
    private String actorProfilePic;

    //activity
    private String activityType; //can be any of the supported stream content types e.g recipientScheduledTrip [combines verb + object on which performed, gives clue of what object to fetch for details]
    private String activityId; //the unique identifier of the activity, which can be used to getOrientation more details about it
    private String activityDetailsId; //the id of the object to fetch for details

    //date
    private String activityDate;
    private String activityTime;

    //target
    private String targetUserId;
    private String targetUserName;
    private String targetUserProfilePic;



    //viewer
    private String viewerId;

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

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityDetailsId() {
        return activityDetailsId;
    }

    public void setActivityDetailsId(String activityDetailsId) {
        this.activityDetailsId = activityDetailsId;
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
