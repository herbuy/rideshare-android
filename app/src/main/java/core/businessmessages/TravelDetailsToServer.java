package core.businessmessages;

public class TravelDetailsToServer {
    private String destinationId = "";
    private String originId = "";
    private String travelDate = "";
    private String prefferedTravelTime = "";

    private String tentativeReturnDate = "";
    private String travelPurposeId = "";
    private String hasCar = "";

    private String preferredMateGenderId = "";
    private String prefferedMateAge = "";
    private String matePurposeId = "";
    private String groupIdToNotify = "";

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getPrefferedTravelTime() {
        return prefferedTravelTime;
    }

    public void setPrefferedTravelTime(String prefferedTravelTime) {
        this.prefferedTravelTime = prefferedTravelTime;
    }

    public String getTentativeReturnDate() {
        return tentativeReturnDate;
    }

    public void setTentativeReturnDate(String tentativeReturnDate) {
        this.tentativeReturnDate = tentativeReturnDate;
    }

    public String getTravelPurposeId() {
        return travelPurposeId;
    }

    public void setTravelPurposeId(String travelPurposeId) {
        this.travelPurposeId = travelPurposeId;
    }

    public String getHasCar() {
        return hasCar;
    }

    public void setHasCar(String hasCar) {
        this.hasCar = hasCar;
    }

    public String getPreferredMateGenderId() {
        return preferredMateGenderId;
    }

    public void setPreferredMateGenderId(String preferredMateGenderId) {
        this.preferredMateGenderId = preferredMateGenderId;
    }

    public String getPrefferedMateAge() {
        return prefferedMateAge;
    }

    public void setPrefferedMateAge(String prefferedMateAge) {
        this.prefferedMateAge = prefferedMateAge;
    }

    public String getMatePurposeId() {
        return matePurposeId;
    }

    public void setMatePurposeId(String matePurposeId) {
        this.matePurposeId = matePurposeId;
    }

    public String getGroupIdToNotify() {
        return groupIdToNotify;
    }

    public void setGroupIdToNotify(String groupIdToNotify) {
        this.groupIdToNotify = groupIdToNotify;
    }
}
