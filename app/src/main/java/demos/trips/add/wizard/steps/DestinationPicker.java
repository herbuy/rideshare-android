package demos.trips.add.wizard.steps;

import android.content.Context;
import android.view.View;

import java.util.List;

import client.ui.display.Location.CSVFromLocation;
import client.ui.display.Location.location_picker.LocationCache;
import client.ui.display.Trip.schedule_trip.SelectableItem;
import client.ui.display.Trip.schedule_trip.pickers.SingleItemPicker;
import core.businessobjects.Location;

public class DestinationPicker {
    SingleItemPicker<Location> valuePicker;

    public DestinationPicker(Context context) {

        valuePicker = new SingleItemPicker<Location>(context) {

            @Override
            protected void onItemSelected(Location item) {
            }

            @Override
            protected void onItemUnSelected(Location item, List<SelectableItem<Location>> selectedItems) {

            }

            @Override
            protected String getDisplayText(Location item) {
                return new CSVFromLocation(item).getText();
            }

            @Override
            protected List<Location> getData() {
                return new LocationCache().selectAll();
            }
        };
    }

    public View getView() {
        return valuePicker.getView();
    }
}
