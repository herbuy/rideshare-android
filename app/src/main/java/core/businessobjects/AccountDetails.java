package core.businessobjects;

public class AccountDetails {

    private final String accountNumber;
    private final String accountBalance;
    private final String profilePhotoFileName;

    public AccountDetails(String accountNumber, String accountBalance, String profilePhotoFileName) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.profilePhotoFileName = profilePhotoFileName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public String getProfilePhotoFileName() {
        return profilePhotoFileName;
    }


}
