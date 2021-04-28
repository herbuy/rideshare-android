package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForGetChatMessages extends BusinessMessageToServer {
    private String forUserId = "";
    private String contactId = "";

    public String getForUserId() {
        return forUserId;
    }

    public void setForUserId(String forUserId) {
        this.forUserId = forUserId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactId() {
        return contactId;
    }
}
