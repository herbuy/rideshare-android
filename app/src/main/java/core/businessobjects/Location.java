package core.businessobjects;


public class Location {
    private String id = "";
    private String name = "";
    private long timeStampLastModifiedInSeconds;

    //========================
    private String countryCode = "";
    private String countryName = "";

    private String adminArea = "";
    private String subAdminArea = "";
    private String locality = "";
    private String subLocality = "";

    private String thoroughfare = "";
    private String subThoroughfare = "";
    private String premises = "";
    private String featureName = "";

    private double longitude;
    private double latitude;


    private String addressLine = "";
    private String postalCode = "";
    private String phone = "";
    private String url = "";

    //============================

    public Location() {
    }

    public Location(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Location(String countryName, String adminArea, String subAdminArea, String locality, double latitude, double longtitude) {

        this.countryName = countryName;
        this.adminArea = adminArea;
        this.subAdminArea = subAdminArea;
        this.locality = locality;
        this.latitude = latitude;
        this.longitude = longtitude;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimeStampLastModifiedInSeconds() {
        return timeStampLastModifiedInSeconds;
    }

    public void setTimeStampLastModifiedInSeconds(long timeStampLastModifiedInSeconds) {
        this.timeStampLastModifiedInSeconds = timeStampLastModifiedInSeconds;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getAdminArea() {
        return adminArea;
    }

    public void setAdminArea(String adminArea) {
        this.adminArea = adminArea;
    }

    public String getSubAdminArea() {
        return subAdminArea;
    }

    public void setSubAdminArea(String subAdminArea) {
        this.subAdminArea = subAdminArea;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getSubLocality() {
        return subLocality;
    }

    public void setSubLocality(String subLocality) {
        this.subLocality = subLocality;
    }

    public String getThoroughfare() {
        return thoroughfare;
    }

    public void setThoroughfare(String thoroughfare) {
        this.thoroughfare = thoroughfare;
    }

    public String getSubThoroughfare() {
        return subThoroughfare;
    }

    public void setSubThoroughfare(String subThoroughfare) {
        this.subThoroughfare = subThoroughfare;
    }

    public String getPremises() {
        return premises;
    }

    public void setPremises(String premises) {
        this.premises = premises;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public boolean isCountry(String countryName) {
        return hasCountryName() && this.countryName.trim().equals(countryName);
    }
    public boolean isAdminArea(String adminArea) {
        return hasAdminArea() && this.adminArea.trim().equals(adminArea);
    }

    public boolean isSubAdminArea(String subAdminArea) {
        return hasSubAdminArea() && this.subAdminArea.trim().equals(subAdminArea);
    }

    public boolean isLocality(String locality) {
        return hasLocality() && this.locality.trim().equals(locality);
    }

    public boolean matchesUptoSubAdmin(Location value) {
        if(value == null){
            return false;
        }

        return isCountry(value.countryName)
                && isAdminArea(value.adminArea)
                && isSubAdminArea(value.subAdminArea)
                ;
    }

    public String getNameShort() {
        return hasLocality() ? locality : subAdminArea;
    }
}
