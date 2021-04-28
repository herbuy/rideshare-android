package client.ui.display.notification;

import core.businessobjects.NotificationMessage;

public class SaveNotificationToCache {

    public static void where(NotificationMessage notificationMessage) {
        String targetNotificationId = notificationMessage.getSenderNumericId().toString();
        NotificationMessageList listToAppendTo;

        if(new NotificationCache().hasKey(targetNotificationId)){
            listToAppendTo = new NotificationCache().getByKey(targetNotificationId);
        }
        else{
            listToAppendTo = new NotificationMessageList();
        }
        listToAppendTo.add(notificationMessage);

        new NotificationCache().save(targetNotificationId,listToAppendTo);
    }
}
