package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForUpdateLocation extends BusinessMessageToServer{
    private String locationId;
    private String name;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
