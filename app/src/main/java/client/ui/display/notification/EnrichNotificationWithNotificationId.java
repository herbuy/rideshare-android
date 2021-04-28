package client.ui.display.notification;

import java.util.Random;

import core.businessobjects.NotificationMessage;

public class EnrichNotificationWithNotificationId {
    public static void where(NotificationMessage notificationMessage) {

        if(!previouslyReceivedNotificationFromSender(notificationMessage.getSenderId())){
            Integer senderNotificationId = generateUniqueNotificationIdForSender();
            saveMappingsBetweenSenderIdAndNotifId(notificationMessage.getSenderId(), senderNotificationId);
            notificationMessage.setSenderNumericId(senderNotificationId);
        }
        else{
            String notifId = new SenderIdToNotifIdMap().getByKey(notificationMessage.getSenderId());
            notificationMessage.setSenderNumericId(Integer.valueOf(notifId));
        }
    }

    private static void saveMappingsBetweenSenderIdAndNotifId(String senderId, Integer senderNotificationId) {
        new SenderIdToNotifIdMap().save(senderId,senderNotificationId.toString());
        new NotifIdToSenderIdMap().save(senderNotificationId.toString(),senderId);
    }

    private static int generateUniqueNotificationIdForSender() {
        int uniqueNotificationId = Math.abs(new Random().nextInt());
        if(notificationIdExists(uniqueNotificationId)){
            return generateUniqueNotificationIdForSender();
        }
        return uniqueNotificationId;
    }

    private static boolean notificationIdExists(Integer notificationId) {
        return new NotifIdToSenderIdMap().hasKey(notificationId.toString());
    }

    private static boolean previouslyReceivedNotificationFromSender(String senderId) {
        return new SenderIdToNotifIdMap().hasKey(senderId);
    }
}
