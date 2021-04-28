package libraries.android;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.util.ArrayList;
import java.util.List;

public class AddressListFromString {
    private boolean found = false;
    String error = "";
    List<Address> addresses = new ArrayList<>();

    public AddressListFromString(final Context context, final String locationNameToSearch) {
        geocode(context,new GeoCodingStrategy(){
            @Override
            public List<android.location.Address> getAddressList(Geocoder geocoder) {
                try{
                    validateStringInput(locationNameToSearch);
                    return geocoder.getFromLocationName(locationNameToSearch, 5);


                    /*List<android.location.Address> firstResultList = geocoder.getFromLocationName(locationNameToSearch, 1);
                    if(firstResultList.size() < 1){
                        return firstResultList;
                    }

                    final android.location.Address firstAddress = firstResultList.get(0);
                    return new GeoCodingStrategy(){
                        @Override
                        public List<android.location.Address> getAddressList(Geocoder geocoder) {
                            try{
                                return geocoder.getFromLocation(firstAddress.getLatitude(),firstAddress.getLongitude(),5);
                            }
                            catch (Throwable t){
                                handleError(t);
                            }
                            return new ArrayList<>();
                        }
                    }.getAddressList(geocoder);*/
                }
                catch(Throwable t){
                    handleError(t);
                }
                return new ArrayList<>();

            }
        });



    }

    public AddressListFromString(Context context, final double lat, final double lng) {
        geocode(context,new GeoCodingStrategy(){
            @Override
            public List<android.location.Address> getAddressList(Geocoder geocoder) {
                try{
                    return geocoder.getFromLocation(lat,lng, 5);
                }
                catch(Throwable t){
                    handleError(t);
                }
                return new ArrayList<>();

            }
        });



    }


    private interface GeoCodingStrategy{
        List<android.location.Address> getAddressList(Geocoder geocoder);
    }



    private void geocode(Context context, GeoCodingStrategy strategy) {
        Geocoder geocoder = new Geocoder(context);
        try{
            List<android.location.Address> addressList = strategy.getAddressList(geocoder);
            if(addressList.size() < 1){
                throw new RuntimeException("No matching addresses");
            }

            found = true;

            addresses = new ArrayList<>();

            for(android.location.Address address : addressList){



                //get admin area = region e.g western region. locality =district/municipality
                Address result = new Address();
                result.setCountryCode(address.getCountryCode());
                result.setCountryName(address.getCountryName());
                result.setAdminArea(address.getAdminArea());
                result.setSubAdminArea(address.getSubAdminArea());
                result.setLocality(address.getLocality());
                result.setSubLocality(address.getSubLocality());
                result.setThoroughfare(address.getThoroughfare());
                result.setSubThoroughfare(address.getSubThoroughfare());
                result.setPremises(address.getPremises());
                result.setFeatureName(address.getFeatureName());
                result.setPhone(address.getPhone());
                result.setUrl(address.getUrl());
                result.setAddressLine(address.getAddressLine(0));
                result.setPostalCode(address.getPostalCode());


                result.setLatitude(address.getLatitude());
                result.setLongitude(address.getLongitude());

                addresses.add(result);
            }


        }
        catch (Throwable t){
            handleError(t);
        }
    }

    private String validateStringInput(String s) {
        if(s == null){
            throw new RuntimeException("input text is null");
        }

        String locationNameToSearch = s.trim();
        if(s.equalsIgnoreCase("")){
            throw new RuntimeException("input text is empty string");
        }

        if(locationNameToSearch.length() < 3){
            throw new RuntimeException("Input text too short");
        }

        if(locationNameToSearch.length() > 255){
            throw new RuntimeException("Input text too long");
        }
        return locationNameToSearch;
    }


    private void handleError(Throwable t) {
        error = t.getMessage();
        found = false;
    }

    public boolean isNotEmpty() {
        return found;
    }

    public boolean isEmpty() {
        return !found;
    }

    public String getError() {
        return error;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public boolean hasError() {
        return !(getError() == null || getError().trim().equalsIgnoreCase(""));
    }


    public static class Address {
        private String countryCode;
        private String countryName;

        private String adminArea;
        private String subAdminArea;
        private String locality;
        private String subLocality;

        private String thoroughfare;
        private String subThoroughfare;
        private String premises;
        private String featureName;

        private double longitude;
        private double latitude;


        private String addressLine;
        private String postalCode;
        private String phone;
        private String url;

        private void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryCode() {
            return countryCode;
        }

        private void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCountryName() {
            return countryName;
        }

        private void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLongitude() {
            return longitude;
        }

        private void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLatitude() {
            return latitude;
        }

        private void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getPostalCode() {
            return postalCode;
        }

        private void setAddressLine(String addressLine) {
            this.addressLine = addressLine;
        }

        public String getAddressLine() {
            return addressLine;
        }

        private void setAdminArea(String adminArea) {
            this.adminArea = adminArea;
        }

        public String getAdminArea() {
            return adminArea;
        }

        private void setSubAdminArea(String subAdminArea) {
            this.subAdminArea = subAdminArea;
        }

        public String getSubAdminArea() {
            return subAdminArea;
        }

        private void setLocality(String locality) {
            this.locality = locality;
        }

        public String getLocality() {
            return locality;
        }

        private void setSubLocality(String subLocality) {
            this.subLocality = subLocality;
        }

        public String getSubLocality() {
            return subLocality;
        }

        private void setThoroughfare(String thoroughfare) {
            this.thoroughfare = thoroughfare;
        }

        public String getThoroughfare() {
            return thoroughfare;
        }

        private void setSubThoroughfare(String subThoroughfare) {
            this.subThoroughfare = subThoroughfare;
        }

        public String getSubThoroughfare() {
            return subThoroughfare;
        }

        private void setPremises(String premises) {
            this.premises = premises;
        }

        public String getPremises() {
            return premises;
        }

        private void setFeatureName(String featureName) {
            this.featureName = featureName;
        }

        public String getFeatureName() {
            return featureName;
        }

        private void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
        }

        private void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        private boolean notNullOrEmpty(String text) {
            return !(text == null || text.trim().equalsIgnoreCase(""));
        }
        public boolean hasCountryName() {
            return notNullOrEmpty(countryName);
        }

        public boolean hasAdminArea() {
            return notNullOrEmpty(adminArea);
        }

        public boolean hasSubAdminArea() {
            return notNullOrEmpty(subAdminArea);
        }

        public boolean hasLocality() {
            return notNullOrEmpty(locality);
        }

        public boolean hasSubLocality() {
            return notNullOrEmpty(subLocality);
        }

        public boolean hasThoroughFare() {
            return notNullOrEmpty(thoroughfare);
        }

        public boolean hasSubThoroughFare() {
            return notNullOrEmpty(subThoroughfare);
        }

        public boolean hasPremises() {
            return notNullOrEmpty(premises);
        }

        public boolean hasFeatureName() {
            return notNullOrEmpty(featureName);
        }

    }



}
