package client.ui.display.Location.location_picker;

import cache.AppDatabase;
import libraries.android.AddressListFromString;
import libraries.data.LocalDatabase;

public class AddressSearchHistory extends AppDatabase<AddressListFromString.Address> {

    @Override
    protected Class<AddressListFromString.Address> getClassOfT() {
        return AddressListFromString.Address.class;
    }

    @Override
    protected final String getTableName() {
        return "address_search_history";
    }


    public boolean hasAddress(final AddressListFromString.Address address) {
        return new AddressSearchHistory().selectFirst(
                new LocalDatabase.Where<AddressListFromString.Address>() {
                    @Override
                    public boolean isTrue(AddressListFromString.Address object) {
                        return
                                object.getCountryName() != null
                                        && object.getCountryName().trim().equalsIgnoreCase(address.getCountryName())
                                        && object.getAdminArea() != null && object.getAdminArea().trim().equalsIgnoreCase(address.getAdminArea())
                                        && object.getSubAdminArea() != null && object.getSubAdminArea().trim().equalsIgnoreCase(address.getSubAdminArea())
                                        && object.getLocality() != null && object.getLocality().trim().equalsIgnoreCase(address.getLocality())

                                ;
                    }
                }
        ) != null;
    }
}
