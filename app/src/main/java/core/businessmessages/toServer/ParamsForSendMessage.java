package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

//a copy of the message will be placed in the stream of each party, that is: both the sender and the target
public class ParamsForSendMessage extends BusinessMessageToServer {
    private String targetUserId; //to whom the message shd be sent
    private String contentType; //how the content shd be intepreted [or we can use chain of responsibility at the server - but client side specification saves time on server]
    private String content;
    private String correlationId;

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}
