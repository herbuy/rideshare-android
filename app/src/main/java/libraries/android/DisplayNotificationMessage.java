package libraries.android;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;


public class DisplayNotificationMessage {
    /**
     * notification type identifies a group or category of notifications, so posting to this group will replace any existing notification in that group
     * channel yo posting to must already exist. if note, create it, preferably in the oncreate method of your main activity.
     */
    public static void where(Context context, int notificationType, String channelId, int appLogoResId, String title, String message, Class<? extends Activity> gotoActivityClass, OnGetSenderIcon onGetSenderIcon) {

        //properties of the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder
                .setSmallIcon(appLogoResId)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)

        ;
        if(onGetSenderIcon != null){
            Bitmap senderIcon = onGetSenderIcon.run();
            if(senderIcon != null){
                builder.setLargeIcon(senderIcon);
            }

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
        mNotificationManager.notify(notificationType, builder.build());


    }

    public interface OnGetSenderIcon{
        Bitmap run();
    }
}
