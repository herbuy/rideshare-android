package cache;

import core.businessobjects.Family;

public class FamilyDatabase extends AppDatabase<Family> {

    @Override
    protected Class<Family> getClassOfT() {
        return Family.class;
    }

    @Override
    protected final String getTableName() {
        return "group_trip_table";
    }
}
