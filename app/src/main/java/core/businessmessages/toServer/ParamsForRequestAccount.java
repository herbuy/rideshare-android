
package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForRequestAccount extends BusinessMessageToServer{

    private String mobileNumber = "";

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }



}
