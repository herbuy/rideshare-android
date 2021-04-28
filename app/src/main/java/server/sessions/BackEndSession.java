package server.sessions;

import cache.SessionDatabase;
import core.businessobjects.SessionDetails;
import libraries.JsonEncoder;

public class BackEndSession extends SessionDetails {

    private boolean found = false;

    private BackEndSession(){

    }

    public static BackEndSession where(String sessionId){
        if (sessionId != null && !sessionId.trim().equalsIgnoreCase("")) {
            SessionDetails sessionDetails = new SessionDatabase().getByKey(sessionId);
            if (sessionDetails != null) {
                BackEndSession decorator = JsonEncoder.decode(JsonEncoder.encode(sessionDetails), BackEndSession.class);
                decorator.found = true;
                return decorator;
            }
        }
        return new BackEndSession();
    }

    public boolean isFound() {
        return found;
    }
}
