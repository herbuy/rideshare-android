package client.ui.display.notification;

import cache.AppDatabase;

//the key is the numeric ID of the sender, and the value is the list of messages from that sender

public class NotifIdToSenderIdMap extends AppDatabase<String> {
    @Override
    protected Class<String> getClassOfT() {
        return String.class;
    }

    @Override
    protected String getTableName() {
        return "notif_id_to_sender_id_map";
    }
}
