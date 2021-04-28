package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;

import java.util.List;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import core.businessobjects.Location;

public class OriginInputDialog extends LocationInputDialog{

    public OriginInputDialog(Context context, TripDataInputListener inputListener) {
        super(context, inputListener);
    }

    @Override
    protected String getPurposeAndUsage() {
        return "We will find you someone leaving your location to your destination";
    }

    @Override
    protected String getQuestion() {
        return "Setting off from";
    }

    @Override
    protected boolean isMultipleItemPicker() {
        return false;
    }


    @Override
    protected void onItemSelected(Location newlySelectedItem, List<Location> allSelectedItems) {
        inputListener.onOriginChanged(newlySelectedItem);
    }

    @Override
    protected String getReasonToRejectSelectOrNull(Location value) {
        return inputListener.getErrorInOriginOrNull(value);
    }
}
