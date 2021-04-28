package client.ui.display.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.skyvolt.jabber.R;

import java.util.List;

import core.businessobjects.NotificationMessage;

public class DisplaySimpleStyleNotification {
    public static void where(Context context, NotificationMessage notificationMessage, Bitmap senderIcon, Class<? extends Activity> gotoActivityClass) {
        String notificationId = notificationMessage.getSenderNumericId().toString();
        List<NotificationMessage> messageGroupToShow = new NotificationCache().getByKey(notificationId);
        if (messageGroupToShow == null || messageGroupToShow.size() < 1) {
            return;
        }


        //properties of the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.notif_channel_id));
        builder
                .setSmallIcon(R.drawable.ic_menu_bell_black)
                .setContentTitle(notificationMessage.getSenderName())
                .setContentText(notificationMessage.getSenderMessage())
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)

        ;
        if(senderIcon != null){
            builder.setLargeIcon(senderIcon);
        }

        //activity to go to on click
        Intent resultIntent = new Intent(context, gotoActivityClass);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(gotoActivityClass);


        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        //send the notification for display
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // notificationID or type allows you to update the notification later on.
        mNotificationManager.notify(Integer.parseInt(notificationId), builder.build());

    }

    public interface OnGetSenderIcon{
        Bitmap run();
    }

    private static PendingIntent pendingIntent(Context context, Class<? extends Activity> gotoActivityClass) {
        return
                TaskStackBuilder
                        .create(context)
                        .addParentStack(gotoActivityClass)
                        .addNextIntent(new Intent(context, gotoActivityClass))// Adds the Intent that starts the Activity to the top of the stack
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
                ;


    }

    private static boolean senderProfilePicProvided(NotificationMessage notificationMessage) {
        return notificationMessage.getSenderProfilePic() != null && !notificationMessage.getSenderProfilePic().trim().equalsIgnoreCase("");
    }
}
