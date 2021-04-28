package cache;

import core.businessobjects.TripMessage;


public class TripMessageDatabase extends AppDatabase<TripMessage> {

    @Override
    protected Class<TripMessage> getClassOfT() {
        return TripMessage.class;
    }

    @Override
    protected final String getTableName() {
        return "trip_messages_table";
    }
}
