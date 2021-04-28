package server;
import java.util.List;
import cache.SessionDatabase;
import cache.FamilyDatabase;
import cache.TripDatabase;
import cache.UserDatabase;
import core.businessobjects.SessionDetails;
import core.businessobjects.FamilyMember;
import core.businessobjects.Family;
import core.businessobjects.Trip;
import core.businessobjects.User;
import libraries.JsonEncoder;
import libraries.throwException;
import server.backendobjects.BackEndTrip;
import server.families.FamilyMatesOfTrip;

public class StoredProcedures {
    public static User getCurrentUserOrThrowException(String sessionId){

        String myUserId = getUserIdFromSessionId(sessionId);
        throwException.ifNullOrEmpty(myUserId, "You might need to login or pass session info");
        User me = new UserDatabase().getByKey(myUserId);
        throwException.ifNull(me,"Cant find user corresponding to this session");
        return me;

    }

    public static Trip getTripOrDie(String tripId, String message) {
        Trip trip = new TripDatabase().getByKey(tripId);
        throwException.ifNull(trip,message);
        return trip;
    }

    public static BackEndTrip getBackEndTripOrDie(String tripId, String message) {
        Trip trip = getTripOrDie(tripId,message);
        BackEndTrip backEndTrip = JsonEncoder.decode(JsonEncoder.encode(trip), BackEndTrip.class);
        throwException.ifNull(backEndTrip,message);
        return backEndTrip;
    }


    public static List<FamilyMember> getOtherFamilyMembers(final String tripId) {
        return FamilyMatesOfTrip.where(tripId).getMates();
    }

    public static Family getFamilyOrDie(String familyId, String message) {
        return new FamilyDatabase().getByKeyOrDie(familyId,message);

    }

    public static String getUserIdFromSessionId(String sessionId) {
        if (null == sessionId) {
            return "";
        }

        SessionDetails sessionDetails = new SessionDatabase().getByKey(sessionId);
        if (null == sessionDetails) {
            return "";
        }

        if (sessionDetails.getUserId() == null) {
            return "";
        }

        return sessionDetails.getUserId();

    }


    public static User getUserDetailsOrDie(String userId, String errorMessage) {
        User user = new UserDatabase().getByKey(userId);
        throwException.ifTrue(user == null, errorMessage);
        return user;
    }
}
