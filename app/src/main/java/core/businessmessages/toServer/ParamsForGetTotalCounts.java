package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForGetTotalCounts extends BusinessMessageToServer{

    //the user whose counts you want to return
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
