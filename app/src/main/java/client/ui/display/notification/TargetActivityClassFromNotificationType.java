package client.ui.display.notification;

import android.app.Activity;
import android.util.Log;
import com.skyvolt.jabber.MainActivity;
import core.businessobjects.NotificationMessage;

public class TargetActivityClassFromNotificationType {
    public static Class<? extends Activity> where(String notificationType) {
        if(notificationType.equalsIgnoreCase(NotificationMessage.Type.POTENTIAL_MATCH_FOUND)){
            return TripNotificationActivity.class;
        }

        if(notificationType.equalsIgnoreCase(NotificationMessage.Type.FAMILY_MESSAGE)){
            return FamilyMessageNotificationActivity.class;
        }

        if(notificationType.equalsIgnoreCase(NotificationMessage.Type.MATCH_FOUND)){
            return FamilyMessageNotificationActivity.class;
        }

        Log.d(getClassName(),"no activity associated with clicking this type of notification");
        return MainActivity.class;
    }

    public static String getClassName() {
        return TargetActivityClassFromNotificationType.class.getSimpleName();
    }
}
