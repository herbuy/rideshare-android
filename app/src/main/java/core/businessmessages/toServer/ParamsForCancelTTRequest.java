package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForCancelTTRequest extends BusinessMessageToServer {
    private String ttRequestId = "";

    public String getTtRequestId() {
        return ttRequestId;
    }

    public void setTtRequestId(String ttRequestId) {
        this.ttRequestId = ttRequestId;
    }
}
