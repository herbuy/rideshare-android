package client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners;

import java.util.List;

import core.businessobjects.Location;

public interface RouteInputListener {
    void onRouteChanged(List<Location> newValue);
    String getErrorRouteItem(Location value);
}
