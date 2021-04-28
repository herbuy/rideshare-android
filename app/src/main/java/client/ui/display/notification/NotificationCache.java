package client.ui.display.notification;

import cache.AppDatabase;

//the key is the numeric ID of the sender, and the value is the list of messages from that sender

public class NotificationCache extends AppDatabase<NotificationMessageList> {
    @Override
    protected Class<NotificationMessageList> getClassOfT() {
        return NotificationMessageList.class;
    }

    @Override
    protected String getTableName() {
        return "notifications_table";
    }
}
