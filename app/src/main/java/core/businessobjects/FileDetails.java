
package core.businessobjects;


public class FileDetails {
    private String shortNameWithExtension = "";
    private String ownerAccountNumber = "";

    public FileDetails(String shortNameWithExtension, String ownerAccountNumber) {
        this.shortNameWithExtension = shortNameWithExtension;
        this.ownerAccountNumber = ownerAccountNumber;
    }

    public String getShortNameWithExtension() {
        return shortNameWithExtension;
    }

    public String getOwnerAccountNumber() {
        return ownerAccountNumber;
    }


}
