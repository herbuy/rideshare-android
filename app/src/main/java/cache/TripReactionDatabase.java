package cache;

import core.businessobjects.TripReaction;

public class TripReactionDatabase extends AppDatabase<TripReaction> {

    @Override
    protected Class<TripReaction> getClassOfT() {
        return TripReaction.class;
    }

    @Override
    protected final String getTableName() {
        return "trip_reaction_table";
    }
}
