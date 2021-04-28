package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForGetFamilies extends BusinessMessageToServer{

    private String memberUserId = ""; //returns families where userId is a member
    private String completedByUserId = ""; //the user id who should have completed the trip
    private String notCompletedByUserId;
    private String familyId = "";

    public String getMemberUserId() {
        return memberUserId;
    }

    public void setMemberUserId(String memberUserId) {
        this.memberUserId = memberUserId;
    }

    public void setCompletedByUserId(String completedByUserId) {
        this.completedByUserId = completedByUserId;
    }

    public String getCompletedByUserId() {
        return completedByUserId;
    }

    public void setNotCompletedByUserId(String notCompletedByUserId) {
        this.notCompletedByUserId = notCompletedByUserId;
    }

    public String getNotCompletedByUserId() {
        return notCompletedByUserId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getFamilyId() {
        return familyId;
    }
}
