package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;
import java.util.List;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import core.businessobjects.Location;

public class DestinationInputDialog extends LocationInputDialog {
    public DestinationInputDialog(Context context, TripDataInputListener inputListener) {
        super(context, inputListener);
    }

    @Override
    protected String getPurposeAndUsage() {
        return "You will be matched with other people heading to your destination";
    }

    @Override
    protected String getQuestion() {
        return "Am going to?";
    }

    @Override
    protected boolean isMultipleItemPicker() {
        return false;
    }

    @Override
    protected void onItemSelected(Location newlySelectedItem, List<Location> allSelectedItems) {
        inputListener.onDestinationChanged(newlySelectedItem);
    }

    @Override
    protected String getReasonToRejectSelectOrNull(Location value) {
        return inputListener.getErrorInDestinationOrNull(value);
    }
}
