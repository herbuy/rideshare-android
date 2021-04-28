package cache;

import core.businessobjects.SessionDetails;

public class SessionDatabase extends AppDatabase<SessionDetails> {

    @Override
    protected Class<SessionDetails> getClassOfT() {
        return SessionDetails.class;
    }

    @Override
    protected final String getTableName() {
        return "sessions_table";
    }
}
