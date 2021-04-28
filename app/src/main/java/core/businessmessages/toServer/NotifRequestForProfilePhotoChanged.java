package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

//subscriptions are for events we may not know who exactly to notify unless they have explicitly expressed interest
//an example here is the profile photo changed subscription
//the owner is automatically notified bse we know who they are since they require
//a session id to change the picture, which can tell us the user
//but others might need to express interest explicitly
public class NotifRequestForProfilePhotoChanged extends BusinessMessageToServer {
    public String forUserId = "";
    public NotifRequestForProfilePhotoChanged() {
        className = getClass().getSimpleName();
    }
}
