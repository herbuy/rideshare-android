package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForUpdateUser extends BusinessMessageToServer{
    private String userId = "";
    private String userName = "";
    private String mobileNumber = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
