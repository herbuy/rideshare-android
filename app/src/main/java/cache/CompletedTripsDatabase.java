package cache;

import core.businessobjects.CompletedTrip;

public class CompletedTripsDatabase extends AppDatabase<CompletedTrip> {

    @Override
    protected Class<CompletedTrip> getClassOfT() {
        return CompletedTrip.class;
    }

    @Override
    protected final String getTableName() {
        return "trips_completed_table";
    }
}
