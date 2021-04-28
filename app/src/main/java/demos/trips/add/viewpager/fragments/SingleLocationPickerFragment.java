package demos.trips.add.viewpager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

import client.ui.display.Location.CSVFromLocation;
import client.ui.display.Location.location_picker.LocationCache;
import client.ui.display.Trip.schedule_trip.SelectableItem;
import client.ui.display.Trip.schedule_trip.pickers.SingleItemPicker;
import core.businessobjects.Location;

public abstract class SingleLocationPickerFragment extends FragmentForAddTripDialog<Location> {
    public SingleLocationPickerFragment(Parameters<Location> params) {
        super(params);
    }

    public View makePicker() {
        return new SingleItemPicker<Location>(params.getContext()) {

            @Override
            protected void onItemSelected(Location item) {
                params.onValueChanged(item);
            }

            @Override
            protected void onItemUnSelected(Location item, List<SelectableItem<Location>> selectedItems) {
                params.onValueChanged(null);
            }

            @Override
            protected String getDisplayText(Location item) {
                return new CSVFromLocation(item).getText();
            }

            @Override
            protected List<Location> getData() {
                return new LocationCache().selectAll();
            }
        }.getView();
    }
}
