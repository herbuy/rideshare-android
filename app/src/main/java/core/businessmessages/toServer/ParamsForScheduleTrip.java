package core.businessmessages.toServer;

import java.util.ArrayList;
import java.util.List;

import core.businessmessages.BusinessMessageToServer;
import core.businessobjects.Location;

public class ParamsForScheduleTrip extends BusinessMessageToServer {
    private String forUserId = "";
    private String tripDate = "";
    private String vehicleRegNumber = "";
    private int maxConcurrentTTMarriages = 1;
    private String tripTime = "";
    private int fuelCharge;

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
    private String vehicleModel = "";

    private List<Location> passingViaLocations = new ArrayList<>();


    public String getForUserId() {
        return forUserId;
    }

    public void setForUserId(String forUserId) {
        this.forUserId = forUserId;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public int getMaxConcurrentTTMarriages() {
        return maxConcurrentTTMarriages;
    }

    public void setMaxConcurrentTTMarriages(int maxConcurrentTTMarriages) {
        this.maxConcurrentTTMarriages = maxConcurrentTTMarriages;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setFuelCharge(int fuelCharge) {
        this.fuelCharge = fuelCharge;
    }

    public int getFuelCharge() {
        return fuelCharge;
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

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public List<Location> getPassingViaLocations() {
        return passingViaLocations;
    }
}
