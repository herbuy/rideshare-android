package client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners;

import client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs.TimeInputDialog;

//receives events and commands/notifications related to time input
public interface TimeInputListener {
    void onTimeChanged(TimeInputDialog.Hour newValue);
}
