package core.validators;

import core.businessmessages.toServer.ParamsForScheduleTrip;
import core.businessobjects.Location;

public class ValidateParamsForScheduleTrip {

    public static void where(ParamsForScheduleTrip params) throws Exception {
        //destination
        ValidatorFor.destinationDuringTripUpload().throwExceptionIfNotValid(
                new Location(
                        params.getToCountryName(),
                        params.getToAdminArea(),
                        params.getToSubAdminArea(),
                        params.getToLocality(),
                        params.getToLatitude(),
                        params.getToLongtitude()
                )
        );

        //origin
        ValidatorFor.originDuringTripUpload().throwExceptionIfNotValid(
                new Location(
                        params.getFromCountryName(),
                        params.getFromAdminArea(),
                        params.getFromSubAdminArea(),
                        params.getFromLocality(),
                        params.getFromLatitude(),
                        params.getFromLongtitude()
                )
        );

        //passing via
        ValidatorFor.routeDuringTripUpload().throwExceptionIfNotValid(params.getPassingViaLocations());

        ValidatorFor.dateDuringTripUpload().throwExceptionIfNotValid(params.getTripDate());
        ValidatorFor.timeDuringTripUpload().throwExceptionIfNotValid(params.getTripTime());
        ValidatorFor.carModelDuringTripUpload().throwExceptionIfNotValid(params.getVehicleModel());
        ValidatorFor.carRegNumberDuringTripUpload().throwExceptionIfNotValid(params.getVehicleRegNumber());
        ValidatorFor.seatsAvailableDuringTripUpload().throwExceptionIfNotValid(params.getMaxConcurrentTTMarriages());
        ValidatorFor.fuelContributionDuringTripUpload().throwExceptionIfNotValid(params.getFuelCharge());
    }
}
