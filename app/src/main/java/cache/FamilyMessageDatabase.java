package cache;

import core.businessobjects.FamilyMessage;


public class FamilyMessageDatabase extends AppDatabase<FamilyMessage> {

    @Override
    protected Class<FamilyMessage> getClassOfT() {
        return FamilyMessage.class;
    }

    @Override
    protected final String getTableName() {
        return "family_messages_table";
    }
}
