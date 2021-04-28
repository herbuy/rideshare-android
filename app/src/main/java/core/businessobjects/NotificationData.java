package core.businessobjects;

import java.util.HashMap;

public class NotificationData extends HashMap<String, String> {
    public String getTripId() {
        return this.get("tripId");
    }

    public String setTripId(String tripId) {
        return this.put("tripId",tripId);
    }

    public String getFamilyId() {
        return this.get("familyId");
    }

    public String setFamilyId(String familyId) {
        return this.put("familyId",familyId);
    }
}
