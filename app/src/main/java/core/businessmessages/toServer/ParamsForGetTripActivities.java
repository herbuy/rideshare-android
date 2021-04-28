package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

//an activity is something that happened to the trip
public class ParamsForGetTripActivities extends BusinessMessageToServer{
    private String tripId;
    private String activityType;

}
