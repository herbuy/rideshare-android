package cache;

import core.businessobjects.Trip;


public class TripDatabase extends AppDatabase<Trip> {

    @Override
    protected Class<Trip> getClassOfT() {
        return Trip.class;
    }

    @Override
    protected final String getTableName() {
        return "trips_table";
    }
}
