package client.ui.display.notification;

import cache.AppDatabase;

//the key is the numeric ID of the sender, and the value is the list of messages from that sender

public class SenderIdToNotifIdMap extends AppDatabase<String> {
    @Override
    protected Class<String> getClassOfT() {
        return String.class;
    }

    @Override
    protected String getTableName() {
        return "sender_id_to_notif_id_map";
    }
}
