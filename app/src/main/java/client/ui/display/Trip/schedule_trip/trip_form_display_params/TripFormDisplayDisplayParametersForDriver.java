package client.ui.display.Trip.schedule_trip.trip_form_display_params;

public class TripFormDisplayDisplayParametersForDriver extends TripFormDisplayParameters {
    @Override
    public String getTitle() {
        return "Find Passengers";
    }

    @Override
    public String getSubTitle() {
        return "Enter your travel details";
    }

    @Override
    public String getOriginLabel() {
        return "From...";
    }

    @Override
    public String getDestinationLabel() {
        return "Where are you going?";
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
        return true;
    }

    @Override
    public boolean isShowCarModelField() {
        return true;
    }

    @Override
    public boolean isShowPassengerCountField() {
        return true;
    }

    @Override
    public boolean isShowPriceField() {
        return true;
    }

    @Override
    public String getCTAText() {
        return "Find";
    }

    @Override
    public String getDestinationPickerTitle() {
        return getDestinationLabel();
    }

    @Override
    public String getOriginPickerTitle() {
        return getOriginLabel();
    }
}
