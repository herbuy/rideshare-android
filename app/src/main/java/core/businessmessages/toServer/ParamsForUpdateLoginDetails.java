package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForUpdateLoginDetails extends BusinessMessageToServer{
    private String userId;
    private String mobileNumber;
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
