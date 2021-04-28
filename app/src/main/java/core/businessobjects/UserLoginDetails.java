package core.businessobjects;

public class UserLoginDetails {
    private String userId;
    private String mobileNumber;
    private String password;


    public UserLoginDetails() {
    }

    public UserLoginDetails(String userId, String mobileNumber, String password) {
        this.userId = userId;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

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
