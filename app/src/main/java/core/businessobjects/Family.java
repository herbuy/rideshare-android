package core.businessobjects;

import java.util.HashSet;
import java.util.Set;

public class Family {

    private String familyId = "";
    private String driverTripId = "";
    private long timestampCreatedInMillis;
    private long timestampLastUpdatedInMillis;

    //computed
    private String driverId = "";
    private String driverName = "";
    private String driverProfilePic = "";

    private String tripDate = "";
    private String tripTime = "";


    private int unseenMessageCount = 0;
    private String lastMessageType = "";
    private String lastMessageText = "";

    private boolean isCompleted;

    private String fromCountryName = ""; //country, admin area, sub admin area will be used to filter
    private String fromAdminArea = "";
    private String fromSubAdminArea = "";
    private String fromLocality = ""; //locality will be used to provide more specific location
    private double fromLatitude;
    private double fromLongtitude; //latitude and longtitude will be reverse geocoded to give us the name of the location in CSV


    private String toCountryName = ""; //country, admin area, sub admin area will be used to filter
    private String toAdminArea = "";
    private String toSubAdminArea = "";
    private String toLocality = ""; //locality will be used to provide more specific location
    private double toLatitude;
    private double toLongtitude; //latitude and longtitude will be reverse geocoded to give us the name of the location in CSV


    private String passengersCSV = "";
    private String passingViaCSV = "";

    private String carModel = "";
    private String carRegNumber = "";

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getDriverTripId() {
        return driverTripId;
    }

    public void setDriverTripId(String driverTripId) {
        this.driverTripId = driverTripId;
    }


    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }

    public String getOriginShort(){
        return !(fromLocality == null || fromLocality.trim().equalsIgnoreCase("")) ? fromLocality : fromSubAdminArea;
    }

    public String getDestinationShort(){
        return !(toLocality == null || toLocality.trim().equalsIgnoreCase("")) ? toLocality : toSubAdminArea;
    }

    public String getOriginName() {
        Address address = new Address();
        address.setLocality(fromLocality);
        address.setSubAdminArea(fromSubAdminArea);
        address.setAdminArea(fromAdminArea);
        address.setCountryName(fromCountryName);
        return getTextFromAddress(address);
    }


    public String getDestinationName() {
        Address address = new Address();
        address.setLocality(toLocality);
        address.setSubAdminArea(toSubAdminArea);
        address.setAdminArea(toAdminArea);
        address.setCountryName(toCountryName);
        return getTextFromAddress(address);
    }

    private String getTextFromAddress(Address address) {
        Set<String> addedText = new HashSet<>();
        String text = "";
        String separator = "";
        String commandSeparator = ", ";


        String newText = address.getPremises();
        if(address.hasPremises() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getFeatureName();
        if(address.hasFeatureName() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getSubThoroughfare();
        if(address.hasSubThoroughFare() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getThoroughfare();
        if(address.hasThoroughFare() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }


        newText = address.getSubLocality();
        if(address.hasSubLocality() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getLocality();
        if(address.hasLocality() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getSubAdminArea();
        if(address.hasSubAdminArea() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getAdminArea();
        if(address.hasAdminArea() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getCountryName();
        if(address.hasCountryName() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }
        return text;
    }


    public boolean driverIsTripId(String tripId) {
        return tripId != null
                && !tripId.trim().equalsIgnoreCase("")
                && driverTripId != null
                && !driverTripId.trim().equalsIgnoreCase("")
                && driverTripId.trim().equalsIgnoreCase(tripId.trim())
                ;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverProfilePic(String driverProfilePic) {
        this.driverProfilePic = driverProfilePic;
    }

    public String getDriverProfilePic() {
        return driverProfilePic;
    }

    public void setUnseenMessageCount(int unseenMessageCount) {
        this.unseenMessageCount = unseenMessageCount;
    }

    public int getUnseenMessageCount() {
        return unseenMessageCount;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public long getTimestampCreatedInMillis() {
        return timestampCreatedInMillis;
    }

    public void setTimestampCreatedInMillis(long timestampCreatedInMillis) {
        this.timestampCreatedInMillis = timestampCreatedInMillis;
    }

    public long getTimestampLastUpdatedInMillis() {
        return timestampLastUpdatedInMillis;
    }

    public void setTimestampLastUpdatedInMillis(long timestampLastUpdatedInMillis) {
        this.timestampLastUpdatedInMillis = timestampLastUpdatedInMillis;
    }



    public String getFromCountryName() {
        return fromCountryName;
    }

    public void setFromCountryName(String fromCountryName) {
        this.fromCountryName = fromCountryName;
    }

    public String getFromAdminArea() {
        return fromAdminArea;
    }

    public void setFromAdminArea(String fromAdminArea) {
        this.fromAdminArea = fromAdminArea;
    }

    public String getFromSubAdminArea() {
        return fromSubAdminArea;
    }

    public void setFromSubAdminArea(String fromSubAdminArea) {
        this.fromSubAdminArea = fromSubAdminArea;
    }

    public String getFromLocality() {
        return fromLocality;
    }

    public void setFromLocality(String fromLocality) {
        this.fromLocality = fromLocality;
    }

    public double getFromLatitude() {
        return fromLatitude;
    }

    public void setFromLatitude(double fromLatitude) {
        this.fromLatitude = fromLatitude;
    }

    public double getFromLongtitude() {
        return fromLongtitude;
    }

    public void setFromLongtitude(double fromLongtitude) {
        this.fromLongtitude = fromLongtitude;
    }

    public String getToCountryName() {
        return toCountryName;
    }

    public void setToCountryName(String toCountryName) {
        this.toCountryName = toCountryName;
    }

    public String getToAdminArea() {
        return toAdminArea;
    }

    public void setToAdminArea(String toAdminArea) {
        this.toAdminArea = toAdminArea;
    }

    public String getToSubAdminArea() {
        return toSubAdminArea;
    }

    public void setToSubAdminArea(String toSubAdminArea) {
        this.toSubAdminArea = toSubAdminArea;
    }

    public String getToLocality() {
        return toLocality;
    }

    public void setToLocality(String toLocality) {
        this.toLocality = toLocality;
    }

    public double getToLatitude() {
        return toLatitude;
    }

    public void setToLatitude(double toLatitude) {
        this.toLatitude = toLatitude;
    }

    public double getToLongtitude() {
        return toLongtitude;
    }

    public void setToLongtitude(double toLongtitude) {
        this.toLongtitude = toLongtitude;
    }

    public String getPassengersCSV() {
        return passengersCSV;
    }

    public void setPassengersCSV(String passengersCSV) {
        this.passengersCSV = passengersCSV;
    }

    public String getPassingViaCSV() {
        return passingViaCSV;
    }

    public void setPassingViaCSV(String passingViaCSV) {
        this.passingViaCSV = passingViaCSV;
    }

    public String getLastMessageType() {
        return lastMessageType;
    }

    public void setLastMessageType(String lastMessageType) {
        this.lastMessageType = lastMessageType;
    }

    public String getLastMessageText() {
        return lastMessageText;
    }

    public void setLastMessageText(String lastMessageText) {
        this.lastMessageText = lastMessageText;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarRegNumber() {
        return carRegNumber;
    }

    public void setCarRegNumber(String carRegNumber) {
        this.carRegNumber = carRegNumber;
    }





    private class Address {
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
