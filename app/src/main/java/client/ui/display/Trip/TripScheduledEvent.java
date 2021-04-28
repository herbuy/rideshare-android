package client.ui.display.Trip;

import core.businessobjects.Trip;
import libraries.ObserverList;

public class TripScheduledEvent {
    private static ObserverList<Trip> observerList = new ObserverList<>();

    public static ObserverList<Trip> instance(){
        return observerList;
    }
}
