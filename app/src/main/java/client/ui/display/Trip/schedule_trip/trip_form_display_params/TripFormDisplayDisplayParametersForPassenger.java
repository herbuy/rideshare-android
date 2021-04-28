package client.ui.display.Trip.schedule_trip.trip_form_display_params;

public class TripFormDisplayDisplayParametersForPassenger extends TripFormDisplayParameters {
    @Override
    public String getTitle() {
        return "Find A Ride";
    }

    @Override
    public String getSubTitle() {
        return "Enter your travel details";
    }

    @Override
    public String getOriginLabel() {
        return "From";
    }

    @Override
    public String getDestinationLabel() {
        return "To";
    }

    @Override
    public String getDateLabel() {
        return "Travelling On";
    }

    @Override
    public String getTimeLabel() {
        return "At around";
    }

    @Override
    public boolean isShowCarPlateField() {
        return false;
    }

    @Override
    public boolean isShowCarModelField() {
        return false;
    }



    @Override
    public boolean isShowPassengerCountField() {
        return false;
    }

    @Override
    public boolean isShowPriceField() {
        return false;
    }

    @Override
    public String getCTAText() {
        return "Find";
    }

    @Override
    public String getDestinationPickerTitle() {
        return "Where are you going";
    }

    @Override
    public String getOriginPickerTitle() {
        return getOriginLabel();
    }
}
