package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForRateFamilyMember extends BusinessMessageToServer{
    private String familyId = "";
    private String memberIdToRate = "";
    private float ratingTimeKeeping;
    private float ratingSafeDriving ;
    private float ratingFriendliness;
    private float ratingOtherFactors;

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getMemberIdToRate() {
        return memberIdToRate;
    }

    public void setMemberIdToRate(String memberIdToRate) {
        this.memberIdToRate = memberIdToRate;
    }

    public float getRatingTimeKeeping() {
        return ratingTimeKeeping;
    }

    public void setRatingTimeKeeping(float ratingTimeKeeping) {
        this.ratingTimeKeeping = ratingTimeKeeping;
    }

    public float getRatingSafeDriving() {
        return ratingSafeDriving;
    }

    public void setRatingSafeDriving(float ratingSafeDriving) {
        this.ratingSafeDriving = ratingSafeDriving;
    }

    public float getRatingFriendliness() {
        return ratingFriendliness;
    }

    public void setRatingFriendliness(float ratingFriendliness) {
        this.ratingFriendliness = ratingFriendliness;
    }

    public float getRatingOtherFactors() {
        return ratingOtherFactors;
    }

    public void setRatingOtherFactors(float ratingOtherFactors) {
        this.ratingOtherFactors = ratingOtherFactors;
    }
}
