package client.ui.display.notifications;

import android.util.Log;

import java.util.List;
import java.util.Map;

import client.ui.display.notification.NotificationService;
import core.businessobjects.NotificationMessage;

public class NotifyForegroundListeners {

    public static void where(
            NotificationMessage notificationMessage,
            Map<String,List<NotificationService.Callback>> notificationListeners,
            String senderClassName
    ) {
        int totalNotified = 0;

        List<NotificationService.Callback> toNotify = notificationListeners.get(notificationMessage.getNotificationType());
        if(toNotify == null || toNotify.size() < 1){
            Log.d(NotifyForegroundListeners.class.getName(),"No callback to notify");
            return;
        }

        for(NotificationService.Callback callback : toNotify){
            if (!tryNotify(notificationMessage, callback)) continue;
            totalNotified += 1;
        }
        if(totalNotified < toNotify.size()){
            Log.d(NotifyForegroundListeners.class.getName(),"Some callbacks were not notified");
        }
    }

    public static boolean tryNotify(NotificationMessage notificationMessage, NotificationService.Callback callback) {
        try{
            callback.run(notificationMessage);
            return true;
        }
        catch (Exception ex){
            Log.d(NotifyForegroundListeners.class.getName(),ex.getMessage());
            return false;
        }
    }

}
