package core.businessobjects;

public class FamilyMember {
    //unique key
    private String memberId = ""; //uniquely identifies the record

    //primary key
    private String familyId = ""; //to provide more information about the family
    private String tripId = ""; //the trip that is a member of this family [can send and receive messages from the family
    private long timestampCreatedInMillis;

    //enrichment
    private String memberName = ""; //comes from the owner of the trip
    private String memberProfilePic = ""; //comes from the owner of the trip
    private String userId = ""; //user id of the member, comes from the owner of the trip


    //other factors include date added


    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }

    public boolean isTripId(String tripId) {
        return tripId != null
                && this.tripId != null
                && !tripId.trim().equalsIgnoreCase("")
                && !this.tripId.trim().equalsIgnoreCase("")
                && this.tripId.trim().equalsIgnoreCase(tripId.trim())
                ;
    }

    public boolean isMemberOfFamily(String familyId) {
        return familyId != null
                && this.familyId != null
                && !familyId.trim().equalsIgnoreCase("")
                && !this.familyId.trim().equalsIgnoreCase("")
                && this.familyId.trim().equalsIgnoreCase(familyId.trim())
                ;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberProfilePic(String memberProfilePic) {
        this.memberProfilePic = memberProfilePic;
    }

    public String getMemberProfilePic() {
        return memberProfilePic;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public long getTimestampCreatedInMillis() {
        return timestampCreatedInMillis;
    }

    public void setTimestampCreatedInMillis(long timestampCreatedInMillis) {
        this.timestampCreatedInMillis = timestampCreatedInMillis;
    }

    public boolean isUserId(String userId) {
        return userId != null
                && this.userId != null
                && !userId.trim().equalsIgnoreCase("")
                && !this.userId.trim().equalsIgnoreCase("")
                && this.userId.trim().equalsIgnoreCase(userId.trim())
                ;
    }
}
