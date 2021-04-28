package client.ui.display.Location;

import core.businessobjects.Location;
import libraries.android.AddressListFromString;

public class GetLocationFromAddress {

    public static Location where(final AddressListFromString.Address address){
        final Location location = new Location();
        location.setCountryCode(address.getCountryCode());
        location.setCountryName(address.getCountryName());
        location.setAdminArea(address.getAdminArea());
        location.setSubAdminArea(address.getSubAdminArea());
        location.setLocality(address.getLocality());
        location.setSubLocality(address.getSubLocality());

        location.setThoroughfare(address.getThoroughfare());
        location.setSubThoroughfare(address.getSubThoroughfare());
        location.setPremises(address.getPremises());
        location.setFeatureName(address.getFeatureName());
        location.setLatitude(address.getLatitude());
        location.setLongitude(address.getLongitude());

        location.setAddressLine(address.getAddressLine());
        location.setPostalCode(address.getPostalCode());
        location.setPhone(address.getPhone());
        location.setUrl(address.getUrl());
        return location;
    }
}
