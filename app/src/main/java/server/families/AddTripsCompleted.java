package server.families;

import cache.CompletedTripsDatabase;
import core.businessobjects.CompletedTrip;

public class AddTripsCompleted {
    public static void where(String familyId, String userId) {
        CompletedTrip completedTrip = CreateTripCompleted.where(familyId, userId);
        new CompletedTripsDatabase().save(completedTrip.getRecordId(),completedTrip);
    }
}
