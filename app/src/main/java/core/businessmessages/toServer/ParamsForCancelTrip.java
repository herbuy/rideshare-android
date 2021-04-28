package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForCancelTrip extends BusinessMessageToServer {
    private String tripId = "";

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}
