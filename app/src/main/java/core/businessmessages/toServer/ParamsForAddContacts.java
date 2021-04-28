package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForAddContacts extends BusinessMessageToServer{

    private String contactId = "";
    private String toUserId = "";

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }
}
