package core.businessobjects;

public class User {
    private String userId;
    private String userName;
    private String profilePic;
    private String mobileNumber;

    //others
    private int tripsCompleted; //from trips where is completed = true
    private float averageRating; //avergae of all the ratings assigned to this user
    private int totalRaters; //we value deiversity of opinion - u could find that all rating are from same person
    private int totalReviews; //reasons people give for the rating they give to a user
    private int previousTripsTogether; //families where both of us are family members and family/group trip is departed [even if not completed]

    //these fields are to be displayed below the "INTERESTED" button when swipping through the list of travellers to find those you are interested in
    private String familyIdMostRecentTripTogether = "";//where departed [else not yet departed]
    private String destinationMostRecentTripTogether = "";//where were u going
    private String originMostRecentTripTogether = "";//where u were coming from
    private String dateMostRecentTripTogether = "";//when did u travel
    private int yourRatingMostRecentTripTogether;//your rating of your most recent trip together
    private String yourRatingReasonMostRecentTripTogether;//your rating of your most recent trip together

    private int peoplePreviouslyTravelledWithUser; //returns the number of people who have previously travelled with this user [in same family and departed]
    private long timestampRegisteredInMillis = 0;
    //you have not yet travelled with a user until you depart //if u just have a trip together, you are just travelling, not yet travelled
    //trips which indicate departed can change tense to is currently travelling. if they reached, says, travelled with...


    public User() {
    }

    public User(String userId, String userName, String profilePic) {
        this.userId = userId;
        this.userName = userName;
        this.profilePic = profilePic;
    }

    public User(String mobileNumber, String userId, String userName, String profilePic) {
        this.mobileNumber = mobileNumber;
        this.userId = userId;
        this.userName = userName;
        this.profilePic = profilePic;
    }

    public boolean isUser(String userId) {
        return
                this.userId != null
                        && userId != null
                        && !this.userId.trim().equalsIgnoreCase("")
                        && !userId.trim().equalsIgnoreCase("")
                        && this.userId.trim().equalsIgnoreCase(userId.trim());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getMobileNumber() {
        return mobileNumber;

    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public long getTimestampRegisteredInMillis() {
        return timestampRegisteredInMillis;
    }

    public void setTimestampRegisteredInMillis(long timestampRegisteredInMillis) {
        this.timestampRegisteredInMillis = timestampRegisteredInMillis;
    }
}
