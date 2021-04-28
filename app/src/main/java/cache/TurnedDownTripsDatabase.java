package cache;

import core.businessobjects.TurnedDownTrip;

public class TurnedDownTripsDatabase extends AppDatabase<TurnedDownTrip> {

    @Override
    protected Class<TurnedDownTrip> getClassOfT() {
        return TurnedDownTrip.class;
    }

    @Override
    protected final String getTableName() {
        return "trips_turned_down_table";
    }
}
