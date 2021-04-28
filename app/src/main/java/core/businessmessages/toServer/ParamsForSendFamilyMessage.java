package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;
import core.businessobjects.FamilyMessage;

//a copy of the message will be placed in the stream of each party, that is: both the sender and the target
public class ParamsForSendFamilyMessage extends BusinessMessageToServer {
    private String toFamilyId; //to whom the message shd be sent
    private String fromUserId;

    private String messageType; //how the messageText shd be intepreted [or we can use chain of responsibility at the server - but client side specification saves time on server]
    private String messageText;
    private String correlationId;

    public String getToFamilyId() {
        return toFamilyId;
    }

    public void setToFamilyId(String toFamilyId) {
        this.toFamilyId = toFamilyId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        if(messageType != null && FamilyMessage.Type.contains(messageType)){
            this.messageType = messageType;
        }


    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}
