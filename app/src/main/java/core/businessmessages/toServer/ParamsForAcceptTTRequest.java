package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForAcceptTTRequest extends BusinessMessageToServer {
    private String ttRequestId;

    public void setTtRequestId(String ttRequestId) {
        this.ttRequestId = ttRequestId;
    }

    public String getTtRequestId() {
        return ttRequestId;
    }
}
