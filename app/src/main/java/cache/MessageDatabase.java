package cache;

import core.businessobjects.ChatMessage;

public class MessageDatabase extends AppDatabase<ChatMessage> {

    @Override
    protected Class<ChatMessage> getClassOfT() {
        return ChatMessage.class;
    }

    @Override
    protected final String getTableName() {
        return "messages_table";
    }
}
