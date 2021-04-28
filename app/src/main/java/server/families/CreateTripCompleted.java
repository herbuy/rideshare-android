package server.families;

import java.util.UUID;

import core.businessobjects.CompletedTrip;

public class CreateTripCompleted {

    public static CompletedTrip where(String familyId, String userId) {
        CompletedTrip completedTrip = new CompletedTrip();
        completedTrip.setRecordId(UUID.randomUUID().toString());
        completedTrip.setFamilyIdCompleted(familyId);
        completedTrip.setUserIdCompleted(userId);
        completedTrip.setTimestampCompletedInMillis(System.currentTimeMillis());

        return completedTrip;
    }
}
