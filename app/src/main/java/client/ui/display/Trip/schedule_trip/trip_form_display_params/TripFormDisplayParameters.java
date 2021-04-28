package client.ui.display.Trip.schedule_trip.trip_form_display_params;

public abstract class TripFormDisplayParameters {
    public static final String PASSENGER = "PASSENGER";
    public static final String DRIVER = "DRIVER";


    public abstract String getTitle();
    public abstract String getSubTitle();
    public abstract String getOriginLabel();
    public abstract String getDestinationLabel();
    public abstract String getDateLabel();
    public abstract String getTimeLabel();
    public abstract boolean isShowCarPlateField();
    public abstract boolean isShowCarModelField();
    public abstract boolean isShowPassengerCountField();
    public abstract boolean isShowPriceField();
    public abstract String getCTAText();

    public abstract String getDestinationPickerTitle();
    public abstract String getOriginPickerTitle();
}
