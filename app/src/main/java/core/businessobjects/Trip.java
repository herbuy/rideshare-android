package core.businessobjects;


import java.util.HashSet;
import java.util.Set;

public class Trip {
    //trip details: from, to, date, time [only]

    private String tripId = "";


    private String tripDate = "";
    private String tripTime = "";
    private int fuelCharge;
    private long timestampLastUpdatedInMillis;


    private String carModel = "";
    private String carRegNumber = "";
    private int maxConcurrentTTMarriages = 1;

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


    //=========== computed ======================


    private String actorId = "";
    private String forUserId = "";

    private int unseenMessageCount;

    private String catchyTitle; //an attractive description

    //extra fields
    private String actorName = "";
    private String actorProfilePic = "";
    private String forUserName = "";
    private String forUserProfilePic = "";
    private long forUserTimestampLastSeenMillis;


    private String idLastTTRequestToLoggedInUser = "";
    private String familyId = "";

    private int totalOtherFamilyMembers = 0;
    //totals
    private Integer totalUpdates = 0;//these are status updates such as arrived
    private Integer totalReactions = 0;
    private Integer totalRequests = 0;
    private Integer totalComments = 0;
    private Integer totalInquiries = 0;
    private Integer totalReviews = 0;
    private Integer totalStars = 0;
    private Integer totalPhotos = 0;
    private long timestampCreatedInMillis;
    private String passingViaCSV = "";

    private float userRatingOverall;
    private float userRatingTimekeeping;
    private float userRatingFriendliness;
    private float userRatingSafeDriving;
    private float userRatingOtherFactors;

    public void setTotalOtherFamilyMembers(int totalOtherFamilyMembers) {
        this.totalOtherFamilyMembers = totalOtherFamilyMembers;
    }

    public Trip() {
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }


    public String getOriginName() {
        Address address = new Address();
        address.setLocality(fromLocality);
        address.setSubAdminArea(fromSubAdminArea);
        address.setAdminArea(fromAdminArea);
        address.setCountryName(fromCountryName);
        return getTextFromAddress(address);
        //return concat(",", fromLocality, fromSubAdminArea, fromAdminArea, fromCountryName);
    }

    public String getOriginShort(){
        return !(fromLocality == null || fromLocality.trim().equalsIgnoreCase("")) ? fromLocality : fromSubAdminArea;
    }

    public String getDestinationShort(){
        return !(toLocality == null || toLocality.trim().equalsIgnoreCase("")) ? toLocality : toSubAdminArea;
    }


    public String getDestinationName() {
        Address address = new Address();
        address.setLocality(toLocality);
        address.setSubAdminArea(toSubAdminArea);
        address.setAdminArea(toAdminArea);
        address.setCountryName(toCountryName);
        return getTextFromAddress(address);

    }

    public String getPassingViaCSV() {
        return passingViaCSV;
    }

    public String[] getPassingViaArray() {
        return
                getPassingViaCSV() == null ? new String[]{} : getPassingViaCSV().split(",");

    }

    public void setPassingViaCSV(String passingViaCSV) {
        this.passingViaCSV = passingViaCSV;
    }

    public boolean hasProfilePic() {
        return forUserProfilePic != null && !forUserProfilePic.equalsIgnoreCase("");
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

    /* allows us present the origin & destination info in a more friendly way */
    private String getTextFromAddress(Address address) {
        Set<String> addedText = new HashSet<>();
        String text = "";
        String separator = "";
        String commandSeparator = ", ";


        String newText = address.getPremises();
        if (address.hasPremises() && !addedText.contains(newText)) {

            text += String.format("%s%s", separator, newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getFeatureName();
        if (address.hasFeatureName() && !addedText.contains(newText)) {

            text += String.format("%s%s", separator, newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getSubThoroughfare();
        if (address.hasSubThoroughFare() && !addedText.contains(newText)) {

            text += String.format("%s%s", separator, newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getThoroughfare();
        if (address.hasThoroughFare() && !addedText.contains(newText)) {

            text += String.format("%s%s", separator, newText);
            addedText.add(newText);
            separator = commandSeparator;
        }


        newText = address.getSubLocality();
        if (address.hasSubLocality() && !addedText.contains(newText)) {

            text += String.format("%s%s", separator, newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getLocality();
        if (address.hasLocality() && !addedText.contains(newText)) {

            text += String.format("%s%s", separator, newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getSubAdminArea();
        if (address.hasSubAdminArea() && !addedText.contains(newText)) {

            text += String.format("%s%s", separator, newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getAdminArea();
        if (address.hasAdminArea() && !addedText.contains(newText)) {

            text += String.format("%s%s", separator, newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getCountryName();
        if (address.hasCountryName() && !addedText.contains(newText)) {

            text += String.format("%s%s", separator, newText);
            addedText.add(newText);
            separator = commandSeparator;
        }
        return text;
    }


    private String concat(String delimiter, String... strings) {
        if (strings == null) {
            return "";
        }
        if (delimiter == null) {
            delimiter = ",";
        }

        String result = "";
        String currentDelimiter = "";
        for (String item : strings) {
            if (item != null && !item.trim().equalsIgnoreCase("")) {
                result += String.format("%s%s", currentDelimiter, item.trim());
                currentDelimiter = delimiter + " ";
            }

        }
        return result;
    }


    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }

    public String getTripTime() {
        return tripTime;
    }

    public int getFuelCharge() {
        return fuelCharge;
    }

    public void setFuelCharge(int fuelCharge) {
        this.fuelCharge = fuelCharge;
    }

    public long getTimestampLastUpdatedInMillis() {
        return timestampLastUpdatedInMillis;
    }

    public void setTimestampLastUpdatedInMillis(long timestampLastUpdatedInMillis) {
        this.timestampLastUpdatedInMillis = timestampLastUpdatedInMillis;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorProfilePic() {
        return actorProfilePic;
    }

    public void setActorProfilePic(String actorProfilePic) {
        this.actorProfilePic = actorProfilePic;
    }

    public String getForUserId() {
        return forUserId;
    }

    public void setForUserId(String forUserId) {
        this.forUserId = forUserId;
    }

    public String getForUserName() {
        return forUserName;
    }

    public void setForUserName(String forUserName) {
        this.forUserName = forUserName;
    }

    public String getForUserProfilePic() {
        return forUserProfilePic;
    }

    public void setForUserProfilePic(String forUserProfilePic) {
        this.forUserProfilePic = forUserProfilePic;
    }

    public Integer getMaxConcurrentTTMarriages() {
        return maxConcurrentTTMarriages;
    }

    public void setMaxConcurrentTTMarriages(int maxConcurrentTTMarriages) {
        this.maxConcurrentTTMarriages = maxConcurrentTTMarriages;
    }

    public int getUnseenMessageCount() {
        return unseenMessageCount;
    }

    public void setUnseenMessageCount(int unseenMessageCount) {
        this.unseenMessageCount = unseenMessageCount;
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

    public Integer getTotalUpdates() {
        return totalUpdates;
    }

    public void setTotalUpdates(Integer totalUpdates) {
        this.totalUpdates = totalUpdates;
    }

    public Integer getTotalReactions() {
        return totalReactions;
    }

    public void setTotalReactions(Integer totalReactions) {
        this.totalReactions = totalReactions;
    }

    public Integer getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(Integer totalRequests) {
        this.totalRequests = totalRequests;
    }

    public Integer getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
    }

    public Integer getTotalInquiries() {
        return totalInquiries;
    }

    public void setTotalInquiries(Integer totalInquiries) {
        this.totalInquiries = totalInquiries;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public Integer getTotalStars() {
        return totalStars;
    }

    public void setTotalStars(Integer totalStars) {
        this.totalStars = totalStars;
    }

    public Integer getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(Integer totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public boolean wasPostedByuser(String userId) {
        return userId != null
                && !userId.trim().equalsIgnoreCase("")
                && actorId != null
                && !actorId.trim().equalsIgnoreCase("")
                && actorId.trim().equalsIgnoreCase(userId.trim())
                ;
    }

    public boolean isFromSameOrigin(Trip trip) {
        return isFromSameCountry(trip) && isFromSameAdminArea(trip) && isFromSameSubAdminArea(trip);
    }

    public boolean isFromSameLocality(Trip trip) {

        return trip != null
                && trip.getFromLocality() != null
                && !trip.getFromLocality().trim().equalsIgnoreCase("")
                && this.fromLocality != null
                && !this.fromLocality.trim().equalsIgnoreCase("")
                && this.fromLocality.trim().equalsIgnoreCase(trip.getFromLocality().trim())
                ;
    }

    public boolean isToSameLocality(Trip trip) {

        return trip != null
                && trip.getToLocality() != null
                && !trip.getToLocality().trim().equalsIgnoreCase("")
                && this.toLocality != null
                && !this.toLocality.trim().equalsIgnoreCase("")
                && this.toLocality.trim().equalsIgnoreCase(trip.getToLocality().trim())
                ;
    }

    private boolean isFromSameCountry(Trip trip) {
        return trip != null
                && trip.getFromCountryName() != null
                && !trip.getFromCountryName().trim().equalsIgnoreCase("")
                && this.fromCountryName != null
                && !this.fromCountryName.trim().equalsIgnoreCase("")
                && this.fromCountryName.trim().equalsIgnoreCase(trip.getFromCountryName().trim())
                ;
    }


    private boolean isFromSameAdminArea(Trip trip) {
        return trip != null
                && trip.getFromAdminArea() != null
                && !trip.getFromAdminArea().trim().equalsIgnoreCase("")
                && this.fromAdminArea != null
                && !this.fromAdminArea.trim().equalsIgnoreCase("")
                && this.fromAdminArea.trim().equalsIgnoreCase(trip.getFromAdminArea().trim())
                ;
    }

    private boolean isFromSameSubAdminArea(Trip trip) {
        return trip != null
                && trip.getFromSubAdminArea() != null
                && !trip.getFromSubAdminArea().trim().equalsIgnoreCase("")
                && this.fromSubAdminArea != null
                && !this.fromSubAdminArea.trim().equalsIgnoreCase("")
                && this.fromSubAdminArea.trim().equalsIgnoreCase(trip.getFromSubAdminArea().trim())
                ;
    }

    public boolean isToSameDestination(Trip trip) {
        return isToSameCountry(trip) && isToSameAdminArea(trip) && isToSameSubAdminArea(trip);
    }

    private boolean isToSameCountry(Trip trip) {
        return trip != null
                && trip.getToCountryName() != null
                && !trip.getToCountryName().trim().equalsIgnoreCase("")
                && this.toCountryName != null
                && !this.toCountryName.trim().equalsIgnoreCase("")
                && this.toCountryName.trim().equalsIgnoreCase(trip.getToCountryName().trim())
                ;
    }

    private boolean isToSameAdminArea(Trip trip) {
        return trip != null
                && trip.getToAdminArea() != null
                && !trip.getToAdminArea().trim().equalsIgnoreCase("")
                && this.toAdminArea != null
                && !this.toAdminArea.trim().equalsIgnoreCase("")
                && this.toAdminArea.trim().equalsIgnoreCase(trip.getToAdminArea().trim())
                ;
    }

    private boolean isToSameSubAdminArea(Trip trip) {
        return trip != null
                && trip.getToSubAdminArea() != null
                && !trip.getToSubAdminArea().trim().equalsIgnoreCase("")
                && this.toSubAdminArea != null
                && !this.toSubAdminArea.trim().equalsIgnoreCase("")
                && this.toSubAdminArea.trim().equalsIgnoreCase(trip.getToSubAdminArea().trim())
                ;
    }

    public boolean isOnSameDate(Trip trip) {
        return trip != null
                && trip.getTripDate() != null
                && !trip.getTripDate().trim().equalsIgnoreCase("")
                && this.tripDate != null
                && !this.tripDate.trim().equalsIgnoreCase("")
                && this.tripDate.trim().equalsIgnoreCase(trip.getTripDate().trim())
                ;
    }

    public boolean isForSameUser(Trip trip) {
        return trip != null
                && trip.getForUserId() != null
                && !trip.getForUserId().trim().equalsIgnoreCase("")
                && this.forUserId != null
                && !this.forUserId.trim().equalsIgnoreCase("")
                && this.forUserId.trim().equalsIgnoreCase(trip.getForUserId().trim())
                ;
    }

    public boolean isNotForSameUser(Trip trip) {
        return !isForSameUser(trip);
    }

    public boolean isOnDate(String date) {
        return date != null
                && this.tripDate != null
                && !date.trim().equalsIgnoreCase("")
                && !this.tripDate.trim().equalsIgnoreCase("")
                && this.tripDate.trim().equalsIgnoreCase(date.trim())
                ;
    }

    public boolean isForUser(String userId) {
        return userId != null
                && this.forUserId != null
                && !userId.trim().equalsIgnoreCase("")
                && !this.forUserId.trim().equalsIgnoreCase("")
                && this.forUserId.trim().equalsIgnoreCase(userId.trim())
                ;
    }

    public boolean isTripId(String tripId) {
        return tripId != null
                && this.tripId != null
                && !tripId.trim().equalsIgnoreCase("")
                && !this.tripId.trim().equalsIgnoreCase("")
                && this.tripId.trim().equalsIgnoreCase(tripId.trim())
                ;
    }

    public void setCarRegNumber(String carRegNumber) {
        this.carRegNumber = carRegNumber;
    }

    public String getCarRegNumber() {
        return carRegNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setIdLastTTRequestToLoggedInUser(String idLastTTRequestToLoggedInUser) {
        this.idLastTTRequestToLoggedInUser = idLastTTRequestToLoggedInUser;
    }

    public String getIdLastTTRequestToLoggedInUser() {
        return idLastTTRequestToLoggedInUser;
    }

    public boolean isDriver() {
        return carRegNumber != null && !carRegNumber.trim().equalsIgnoreCase("");
    }

    public boolean isPassenger() {
        return !isDriver();
    }

    public boolean isReachedMaritalLimit() {
        return totalOtherFamilyMembers >= maxConcurrentTTMarriages;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }


    public boolean isMarriedTo(Trip trip) {
        return trip != null
                && trip.getFamilyId() != null
                && !trip.getFamilyId().trim().equalsIgnoreCase("")
                && this.familyId != null
                && !this.familyId.trim().equalsIgnoreCase("")
                && this.familyId.trim().equalsIgnoreCase(trip.getFamilyId().trim());
    }

    public boolean isSameSex(Trip trip) {
        return isDriver() && trip.isDriver() || (!isDriver() && !trip.isDriver());
    }

    public boolean isNotSameSex(Trip trip) {
        return !isSameSex(trip);
    }

    //could be used for sorting
    public boolean isMoreRecentlyUpdatedThan(Trip trip) {
        return trip != null && this.getTimestampLastUpdatedInMillis() > trip.getTimestampLastUpdatedInMillis();
    }

    public long getTimestampCreatedInMillis() {
        return timestampCreatedInMillis;
    }

    public void setTimestampCreatedInMillis(long timestampCreatedInMillis) {
        this.timestampCreatedInMillis = timestampCreatedInMillis;
    }

    public long getForUserTimestampLastSeenMillis() {
        return forUserTimestampLastSeenMillis;
    }

    public void setForUserTimestampLastSeenMillis(long forUserTimestampLastSeenMillis) {
        this.forUserTimestampLastSeenMillis = forUserTimestampLastSeenMillis;
    }

    public float getUserRatingOverall() {
        return userRatingOverall;
    }

    public void setUserRatingOverall(float userRatingOverall) {
        this.userRatingOverall = userRatingOverall;
    }

    public float getUserRatingTimekeeping() {
        return userRatingTimekeeping;
    }

    public void setUserRatingTimekeeping(float userRatingTimekeeping) {
        this.userRatingTimekeeping = userRatingTimekeeping;
    }

    public float getUserRatingFriendliness() {
        return userRatingFriendliness;
    }

    public void setUserRatingFriendliness(float userRatingFriendliness) {
        this.userRatingFriendliness = userRatingFriendliness;
    }

    public float getUserRatingSafeDriving() {
        return userRatingSafeDriving;
    }

    public void setUserRatingSafeDriving(float userRatingSafeDriving) {
        this.userRatingSafeDriving = userRatingSafeDriving;
    }

    public float getUserRatingOtherFactors() {
        return userRatingOtherFactors;
    }

    public void setUserRatingOtherFactors(float userRatingOtherFactors) {
        this.userRatingOtherFactors = userRatingOtherFactors;
    }




}
