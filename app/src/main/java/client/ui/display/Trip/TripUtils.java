package client.ui.display.Trip;

import client.data.LocalSession;
import core.businessobjects.Trip;

public class TripUtils {
    public static boolean isMyTrip(Trip trip) {
        String myUserId = LocalSession.instance().getUserId();

        return
                trip != null && trip.getActorId() != null && myUserId != null && (trip.getActorId().equalsIgnoreCase(myUserId));

    }
}
