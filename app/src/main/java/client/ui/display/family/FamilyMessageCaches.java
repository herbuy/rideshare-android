package client.ui.display.family;

import cache.LocalCache;


public class FamilyMessageCaches extends LocalCache<MessageCacheForFamily> {

    @Override
    protected Class<MessageCacheForFamily> getClassOfT() {
        return MessageCacheForFamily.class;
    }

    @Override
    protected final String getTableName() {
        return "familyMessageCache";
    }
}
