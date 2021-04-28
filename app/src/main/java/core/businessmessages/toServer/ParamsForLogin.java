package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForLogin extends BusinessMessageToServer{
    private String mobileNumber;
    private String password;

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
