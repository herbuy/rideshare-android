package client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners;

import core.businessobjects.Location;

public interface DestinationInputListener {
    void onDestinationChanged(Location newValue);

    String getErrorInDestinationOrNull(Location value);
}
