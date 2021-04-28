package client.ui.display.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.skyvolt.jabber.MainActivity;
import com.skyvolt.jabber.R;

import java.util.List;
import java.util.Map;

import client.ui.IntentExtras;
import core.businessobjects.NotificationMessage;
import libraries.android.GetBitmapFromUrl;
import libraries.JsonEncoder;
import shared.MakeFilePath;

public class DisplayInBoxStyleNotification {
    public static void where(Context context, NotificationMessage notificationMessage) {
        where(context, notificationMessage, MainActivity.class);

    }

    public static void where(Context context, NotificationMessage notificationMessage, Class<? extends Activity> onClick) {
        String notificationId = notificationMessage.getSenderNumericId().toString();
        notificationMessage.getData().put("notification_id",notificationId);

        List<NotificationMessage> messageGroupToShow = new NotificationCache().getByKey(notificationId);
        if (messageGroupToShow == null || messageGroupToShow.size() < 1) {
            return;
        }

        //properties of the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.notif_channel_id));

        if (senderProfilePicProvided(notificationMessage)) {
            builder.setLargeIcon(GetBitmapFromUrl.where(MakeFilePath.fromFileShortName(notificationMessage.getSenderProfilePic())));
        }


        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle(
                builder
                        .setTicker(notificationMessage.getSenderMessage())
                        .setSmallIcon(R.drawable.ic_notification)
                        //when
                        .setContentTitle(notificationMessage.getSenderName())
                        .setContentText(notificationMessage.getSenderMessage())
                        .setNumber(messageGroupToShow.size())
                        .setContentIntent(pendingIntent(context, onClick, notificationMessage.getData()))
                        .setGroup("INBOX_MESSAGE")
                        //.setDeleteIntent(getDeleteIntent(notificationMessage.getSenderName()))
                        //.setVibrate(new long[]{100L, 500L})
                        //.setAutoCancel(true)
                        //.setDefaults(Notification.DEFAULT_ALL)
        )
                .setBigContentTitle(notificationMessage.getSenderName())
                .setSummaryText("")


        ;


        for (NotificationMessage cachedNotificationMessage : messageGroupToShow) {
            inboxStyle.addLine(cachedNotificationMessage.getSenderMessage());
        }

        //builder.setStyle(inboxStyle);

        //send the notification for display
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // notificationID or type allows you to update the notification later on.
        mNotificationManager.notify(Integer.valueOf(notificationId), inboxStyle.build());
    }

    private static PendingIntent pendingIntent(Context context, Class<? extends Activity> gotoActivityClass, Map<String, String> data) {
        Intent intent = new Intent(context, gotoActivityClass);
        intent.putExtra(IntentExtras.data, JsonEncoder.encode(data));
        return
                TaskStackBuilder
                        .create(context)
                        .addParentStack(gotoActivityClass)
                        .addNextIntent(intent)// Adds the Intent that starts the Activity to the top of the stack
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
                ;


    }

    private static boolean senderProfilePicProvided(NotificationMessage notificationMessage) {
        return notificationMessage.getSenderProfilePic() != null && !notificationMessage.getSenderProfilePic().trim().equalsIgnoreCase("");
    }
}
