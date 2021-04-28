package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForGetFamilyMessages extends BusinessMessageToServer {
    private String familyId = "";
    private String forUserId = "";
    private boolean seen = false; //if true, the messages will be marked as seen

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public void setForUserId(String forUserId) {
        this.forUserId = forUserId;
    }

    public String getForUserId() {
        return forUserId;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isSeen() {
        return seen;
    }
}
