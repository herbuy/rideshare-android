package server.backendobjects;

import android.util.Log;

import java.util.UUID;

import cache.TripDatabase;
import core.businessmessages.toServer.ParamsForScheduleTrip;
import core.businessobjects.Trip;
import core.businessobjects.User;
import libraries.JsonEncoder;
import libraries.throwException;
import server.StoredProcedures;
import server.trip.NotifyOtherTrips;

public class BackEndTripFromParams extends Trip {
    User forUser;



    public BackEndTripFromParams(ParamsForScheduleTrip params, User currentUser) {

        forUser = StoredProcedures.getUserDetailsOrDie(params.getForUserId(), "Need to specify whose trip you are schedulling");


        String tripId = UUID.randomUUID().toString();
        setTripId(tripId);
        setTripDate(params.getTripDate());
        setActorId(currentUser.getUserId());
        setForUserId(params.getForUserId());
        setCarModel(params.getVehicleModel());
        setCarRegNumber(params.getVehicleRegNumber());
        setTripTime(params.getTripTime());
        setFuelCharge(params.getFuelCharge());
        setTimestampLastUpdatedInMillis(System.currentTimeMillis());

        setToCountryName(params.getToCountryName());
        setToAdminArea(params.getToAdminArea());
        setToSubAdminArea(params.getToSubAdminArea());
        setToLocality(params.getToLocality());
        setToLatitude(params.getToLatitude());
        setToLongtitude(params.getToLongtitude());

        setFromCountryName(params.getFromCountryName());
        setFromAdminArea(params.getFromAdminArea());
        setFromSubAdminArea(params.getFromSubAdminArea());
        setFromLocality(params.getFromLocality());
        setFromLatitude(params.getFromLatitude());
        setFromLongtitude(params.getFromLongtitude());

        setTimestampCreatedInMillis(System.currentTimeMillis());

        if(isDriver()){
           setMaxConcurrentTTMarriages(
                   Math.max(
                           1,
                           Math.min(
                                   65, //maximum of a bus capacity
                                   params.getMaxConcurrentTTMarriages()
                           )
                   )
           );
        }
        else{
            setMaxConcurrentTTMarriages(1);
        }

        //validate
        String validationError = getValidationError();
        throwException.ifNotNullOrEmpty(validationError, validationError);

        Log.d("TRIP DATA",JsonEncoder.encode(this));
        save();
        NotifyOtherTrips.acceptingProposalsFromTrip(this);
    }

    public String getValidationError() {
        //missing values
        if(getTripId() == null || getTripId().trim().equalsIgnoreCase("")){
            return "Trip Id missing";
        }
        if(getTripDate() == null || getTripDate().trim().equalsIgnoreCase("")){
            return "Trip Date not specified";
        }

        //check location info
        if(getFromCountryName() == null || getFromCountryName().trim().equalsIgnoreCase("")){
            return "Specify country of location";
        }
        if(getFromAdminArea() == null || getFromAdminArea().trim().equalsIgnoreCase("")){
            return "Specify location admin area";
        }
        if(getFromSubAdminArea() == null || getFromSubAdminArea().trim().equalsIgnoreCase("")){
            return "Specify location sub-admin area";
        }

        if(getFromLatitude() == 0){
            return "Specify location latitude";
        }
        if(getFromLongtitude() == 0){
            return "Specify location longtitude";
        }

        //check destination info
        if(getToCountryName() == null || getToCountryName().trim().equalsIgnoreCase("")){
            return "Specify destination country";
        }
        if(getToAdminArea() == null || getToAdminArea().trim().equalsIgnoreCase("")){
            return "Specify destination admin area";
        }
        if(getToSubAdminArea() == null || getToSubAdminArea().trim().equalsIgnoreCase("")){
            return "Specify destination sub-admin area";
        }
        if(getToLatitude() == 0){
            return "Specify destination latitude";
        }
        if(getToLongtitude() == 0){
            return "Specify destination longtitude";
        }

        //check that location not same as destination
        if(destinationIsSameAsOrigin()){
            return "Destination can not be similar to location";

        }

        if(getActorId() == null || getActorId().trim().equalsIgnoreCase("")){
            return "You need to login";
        }
        if(getForUserId() == null || getForUserId().trim().equalsIgnoreCase("")){
            return "Specify person for whom trim is being scheduled";
        }
        //vehicle registration number is optional
        //invalid values or selections

        if(!tripDateValid()){
            return "Invalid trip date";
        }

        return "";
    }

    private boolean destinationIsSameAsOrigin() {
        return getToCountryName().trim().equalsIgnoreCase(getFromAdminArea())
                && getToAdminArea().trim().equalsIgnoreCase(getFromAdminArea())
                && getToSubAdminArea().trim().equalsIgnoreCase(getFromSubAdminArea())
                && getToLocality().trim().equalsIgnoreCase(getFromLocality())
                ;
    }


    private boolean tripDateValid() {
        return true;
    }

    private void save() {
        Trip trip = JsonEncoder.decode(JsonEncoder.encode(this),Trip.class);
        new TripDatabase().save(trip.getTripId(), trip);

    }

    public User getForUser() {
        return forUser;
    }


}
