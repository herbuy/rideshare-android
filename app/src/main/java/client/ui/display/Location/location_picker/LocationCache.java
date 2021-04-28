package client.ui.display.Location.location_picker;

import cache.AppDatabase;
import core.businessobjects.Location;
import libraries.data.LocalDatabase;

public class LocationCache extends AppDatabase<Location> {

    @Override
    protected Class<Location> getClassOfT() {
        return Location.class;
    }

    @Override
    protected final String getTableName() {
        return "location_cache";
    }


    public boolean hasLocation(final Location address) {
        return new LocationCache().selectFirst(whereMatch(address)) != null;
    }

    public Where<Location> whereMatch(final Location address) {
        return new Where<Location>() {
            @Override
            public boolean isTrue(Location object) {
                return isSameLocation(object, address);
            }
        };
    }

    public void deleteLocation(final Location location){
        delete(whereMatch(location));
    }

    public boolean isSameLocation(Location object, Location address) {
        return object.getCountryName() != null
                && object.getCountryName().trim().equalsIgnoreCase(address.getCountryName())
                && object.getAdminArea() != null && object.getAdminArea().trim().equalsIgnoreCase(address.getAdminArea())
                && object.getSubAdminArea() != null && object.getSubAdminArea().trim().equalsIgnoreCase(address.getSubAdminArea())
                && object.getLocality() != null && object.getLocality().trim().equalsIgnoreCase(address.getLocality());
    }
}
