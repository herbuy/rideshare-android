package core.businessobjects;

public class FamilyMemberRating {

    //unique key
    private String recordId = "";

    //primary key
    private String memberIdRated = "";
    private String memberIdThatRated = "";

    private long timestampRatedInMillis;

    //calculated
    private String familyId = ""; //allows us return details about the family

    //data
    private float ratingTimeKeeping;
    private float ratingFriendliness;
    private float ratingSafeDriving;
    private float ratingOtherFactors;

    //=================


    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getMemberIdRated() {
        return memberIdRated;
    }

    public void setMemberIdRated(String memberIdRated) {
        this.memberIdRated = memberIdRated;
    }

    public String getMemberIdThatRated() {
        return memberIdThatRated;
    }

    public void setMemberIdThatRated(String memberIdThatRated) {
        this.memberIdThatRated = memberIdThatRated;
    }

    public float getRatingTimeKeeping() {
        return ratingTimeKeeping;
    }

    public void setRatingTimeKeeping(float ratingTimeKeeping) {
        this.ratingTimeKeeping = ratingTimeKeeping;
    }

    public float getRatingFriendliness() {
        return ratingFriendliness;
    }

    public void setRatingFriendliness(float ratingFriendliness) {
        this.ratingFriendliness = ratingFriendliness;
    }

    public float getRatingSafeDriving() {
        return ratingSafeDriving;
    }

    public void setRatingSafeDriving(float ratingSafeDriving) {
        this.ratingSafeDriving = ratingSafeDriving;
    }

    public float getRatingOtherFactors() {
        return ratingOtherFactors;
    }

    public void setRatingOtherFactors(float ratingOtherFactors) {
        this.ratingOtherFactors = ratingOtherFactors;
    }

    public long getTimestampRatedInMillis() {
        return timestampRatedInMillis;
    }

    public void setTimestampRatedInMillis(long timestampRatedInMillis) {
        this.timestampRatedInMillis = timestampRatedInMillis;
    }


}
