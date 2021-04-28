package core.businessobjects;

public class NewsFeedItem0 {
    //actor
    private String actorId;
    private String actorName;
    private String actorProfilePic;

    //activity
    private String activityType; //can be any of the supported stream content types e.g recipientScheduledTrip [combines verb + object on which performed, gives clue of what object to fetch for details]
    private String activityId; //the unique identifier of the activity, which can be used to getOrientation more details about it
    private String activityDetailsId; //the id of the object to fetch for details

    //date
    private String actionDate;
    private String actionTime;

    //target
    private String targetUserId;
    private String targetUserName;
    private String targetUserProfilePic;



    //viewer
    private String viewerId;




    /*
    private User actor;
    private User target;
    private String subject;
    private String date;
    private String time;
    private String viewerId;

    public void setActor(User actor) {
        this.actor = actor;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getActor() {
        return actor;
    }

    public User getTarget() {
        return target;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }*/
}
