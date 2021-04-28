package client.ui.display.Trip;

import cache.LocalCache;
import core.businessobjects.Trip;

/** represents a list of all trips stored localy
 * shd be cleared whenever account switches
 *  */
public class TripListCache extends LocalCache<Trip> {

    @Override
    protected Class<Trip> getClassOfT() {
        return Trip.class;
    }

    @Override
    protected final String getTableName() {
        return "tripListCache";
    }
}
