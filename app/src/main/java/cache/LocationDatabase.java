package cache;

import core.businessobjects.Location;

public class LocationDatabase extends AppDatabase<Location> {

    @Override
    protected Class<Location> getClassOfT() {
        return Location.class;
    }

    @Override
    protected final String getTableName() {
        return "location_database";
    }
}
