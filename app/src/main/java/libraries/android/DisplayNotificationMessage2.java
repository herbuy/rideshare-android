package libraries.android;


public class DisplayNotificationMessage2 {
    /**
     * notification type identifies a group or category of notifications, so posting to this group will replace any existing notification in that group
     * channel yo posting to must already exist. if note, create it, preferably in the oncreate method of your main activity.
     */

    /*
    public static void where(Context context, int notificationType, String channelId, int appLogoResId, String title, String message, String activityOpenedOnClick, OnGetSenderIcon onGetSenderIcon) {


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

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(title).setSummaryText("Inbox");
        List<String> messagesUnder
        inboxStyle.addLine("hello");

        builder
                .setStyle(inboxStyle)
        ;

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
    }*/
}
