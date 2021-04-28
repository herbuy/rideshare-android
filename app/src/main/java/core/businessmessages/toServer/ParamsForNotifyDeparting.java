package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForNotifyDeparting extends BusinessMessageToServer{
    private String tripId;// the trip that is departing. Must be yo trip among other things to allow send a notification
    //after the trip has been marked as departing, you can choose people you want to notify, else no one will be notified.

}
