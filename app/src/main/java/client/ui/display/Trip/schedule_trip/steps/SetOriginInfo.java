package client.ui.display.Trip.schedule_trip.steps;

import core.businessmessages.toServer.ParamsForScheduleTrip;
import core.businessobjects.Location;

public class SetOriginInfo {
    public static void where(ParamsForScheduleTrip paramsForScheduleTrip, Location selectedLocation) {
        if(selectedLocation == null){
            return;
        }
        paramsForScheduleTrip.setFromCountryName(selectedLocation.getCountryName());
        paramsForScheduleTrip.setFromAdminArea(selectedLocation.getAdminArea());
        paramsForScheduleTrip.setFromSubAdminArea(selectedLocation.getSubAdminArea());
        paramsForScheduleTrip.setFromLocality(selectedLocation.getLocality());
        paramsForScheduleTrip.setFromLatitude(selectedLocation.getLatitude());
        paramsForScheduleTrip.setFromLongtitude(selectedLocation.getLongitude());
    }
}
