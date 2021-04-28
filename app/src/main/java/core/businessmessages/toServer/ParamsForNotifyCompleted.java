package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForNotifyCompleted extends BusinessMessageToServer{
    private String familyId;

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }
}
