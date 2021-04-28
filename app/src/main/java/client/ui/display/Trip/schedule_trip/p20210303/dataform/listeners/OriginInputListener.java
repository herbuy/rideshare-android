package client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners;

import core.businessobjects.Location;

public interface OriginInputListener {
    void onOriginChanged(Location newValue);
    String getErrorInOriginOrNull(Location value);
}
