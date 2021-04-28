package client.ui.display.Trip.schedule_trip.steps;

import core.businessmessages.toServer.ParamsForScheduleTrip;
import core.businessobjects.Location;

public class SetDestinationInfo {

    public static void where(ParamsForScheduleTrip paramsForScheduleTrip, Location selectedLocation) {
        if(selectedLocation == null){
            return;
        }
        paramsForScheduleTrip.setToCountryName(selectedLocation.getCountryName());
        paramsForScheduleTrip.setToAdminArea(selectedLocation.getAdminArea());
        paramsForScheduleTrip.setToSubAdminArea(selectedLocation.getSubAdminArea());
        paramsForScheduleTrip.setToLocality(selectedLocation.getLocality());
        paramsForScheduleTrip.setToLatitude(selectedLocation.getLatitude());
        paramsForScheduleTrip.setToLongtitude(selectedLocation.getLongitude());
    }
}
