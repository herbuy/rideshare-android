package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForSendSafeJourney extends BusinessMessageToServer {
    private String targetUserId; //must be someone who is travelling on that trip e.g organizer, or travel mates he has approved and they notified friends
    private String tripId;
}
