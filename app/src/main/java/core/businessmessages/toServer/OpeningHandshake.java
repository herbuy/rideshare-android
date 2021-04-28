package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class OpeningHandshake extends BusinessMessageToServer {
    public OpeningHandshake() {
        className = getClass().getSimpleName();
    }
}
