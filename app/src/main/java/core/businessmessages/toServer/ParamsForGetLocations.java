package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForGetLocations extends BusinessMessageToServer{

    private String locationIdEquals;

    public String getLocationIdEquals() {
        return locationIdEquals;
    }

    public void setLocationIdEquals(String locationIdEquals) {
        this.locationIdEquals = locationIdEquals;
    }
}
