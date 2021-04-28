package server.families;

import cache.CompletedTripsDatabase;
import core.businessobjects.CompletedTrip;
import libraries.data.LocalDatabase;

public class TripIsCompletedByUser {

    public static boolean where(final String familyId, final String userId) {
        CompletedTrip result = new CompletedTripsDatabase().selectFirst(new LocalDatabase.Where<CompletedTrip>() {
            @Override
            public boolean isTrue(CompletedTrip completedTrip) {
                return completedTrip.isFamilyId(familyId) && completedTrip.isCompletedByUserId(userId);
            }
        });
        return result != null;
    }
}
