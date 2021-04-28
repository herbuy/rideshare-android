package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;

import java.util.List;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import core.businessobjects.Location;

public class RouteInputDialog extends LocationInputDialog {
    public RouteInputDialog(Context context, TripDataInputListener inputListener) {
        super(context, inputListener);
    }

    @Override
    protected String getPurposeAndUsage() {
        return "Though you can skip this page, mentioning the districts via which you will be passing will increase your chances of finding someone to travel with";
    }

    @Override
    protected String getQuestion() {
        return passengerQuestion();
    }

    private String passengerQuestion() {
        return "Show drivers stopping in... <br/><small><small>(mention one or more districts)</small></small>";
    }

    private String driverQuestion() {
        return "Pick up passengers in... <br/><small><small>(mention one or more districts)</small></small>";
    }

    @Override
    protected String getReasonToRejectSelectOrNull(Location value) {
        return inputListener.getErrorRouteItem(value);
    }

    @Override
    protected boolean isMultipleItemPicker() {
        return true;
    }

    @Override
    protected void onItemSelected(Location newlySelectedItem, List<Location> allSelectedItems) {
        inputListener.onRouteChanged(allSelectedItems);
    }

    public void changeViewToPassenger() {
        titleChangerLink.changeTitle(passengerQuestion());
    }

    public void changeViewToDriver() {
        titleChangerLink.changeTitle(driverQuestion());
    }


}
