package core.businessobjects;

public class CompletedTrip {
    private String recordId;
    private String familyIdCompleted = "";
    private String userIdCompleted = "";
    private long timestampCompletedInMillis;



    //after enrich
    private String userNameCompleted = "";
    private String userProfilePicCompleted = "";
    private long timestampCreatedInMillis;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getFamilyIdCompleted() {
        return familyIdCompleted;
    }

    public void setFamilyIdCompleted(String familyIdCompleted) {
        this.familyIdCompleted = familyIdCompleted;
    }

    public String getUserIdCompleted() {
        return userIdCompleted;
    }

    public void setUserIdCompleted(String userIdCompleted) {
        this.userIdCompleted = userIdCompleted;
    }

    public boolean isFamilyId(String familyId) {
        return familyId != null
                && !familyId.trim().equalsIgnoreCase("")
                && familyIdCompleted != null
                && !familyIdCompleted.trim().equalsIgnoreCase("")
                && familyIdCompleted.trim().equalsIgnoreCase(familyId.trim())
                ;
    }

    public boolean isCompletedByUserId(String userId) {
        return userId != null
                && !userId.trim().equalsIgnoreCase("")
                && userIdCompleted != null
                && !userIdCompleted.trim().equalsIgnoreCase("")
                && userIdCompleted.trim().equalsIgnoreCase(userId.trim())
                ;
    }

    public void setTimestampCompletedInMillis(long timestampCompletedInMillis) {
        this.timestampCompletedInMillis = timestampCompletedInMillis;
    }

    public void setUserNameCompleted(String userNameCompleted) {
        this.userNameCompleted = userNameCompleted;
    }

    public String getUserNameCompleted() {
        return userNameCompleted;
    }

    public void setUserProfilePicCompleted(String userProfilePicCompleted) {
        this.userProfilePicCompleted = userProfilePicCompleted;
    }

    public String getUserProfilePicCompleted() {
        return userProfilePicCompleted;
    }

    public long getTimestampCreatedInMillis() {
        return timestampCreatedInMillis;
    }

    public void setTimestampCreatedInMillis(long timestampCreatedInMillis) {
        this.timestampCreatedInMillis = timestampCreatedInMillis;
    }
}
