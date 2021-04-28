package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForGetFamilyMembers extends BusinessMessageToServer{
    private String familyId = "";
    private String onlyIfCanBeRatedByUserId = "";

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public void setOnlyIfCanBeRatedByUserId(String onlyIfCanBeRatedByUserId) {
        this.onlyIfCanBeRatedByUserId = onlyIfCanBeRatedByUserId;
    }

    public String getOnlyIfCanBeRatedByUserId() {
        return onlyIfCanBeRatedByUserId;
    }
}
