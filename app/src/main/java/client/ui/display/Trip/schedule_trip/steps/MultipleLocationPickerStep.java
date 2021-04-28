package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;
import java.util.List;
import java.util.Locale;
import client.ui.display.Trip.schedule_trip.SelectableItem;
import client.ui.display.Trip.schedule_trip.pickers.ItemPicker;
import client.ui.display.Trip.schedule_trip.pickers.MultipleItemPicker;
import core.businessobjects.Location;

public abstract class MultipleLocationPickerStep extends LocationPickerStep {
    public MultipleLocationPickerStep(Context context) {
        super(context);
    }

    @Override
    protected ItemPicker<Location> makeLocationPicker() {
        return new MultipleItemPicker<Location>(context){

            @Override
            protected void onItemSelected(Location item) {
                getThis().locationSelected(item);
                selectedLocationsChanged(getSelectedValues());

            }

            @Override
            protected void onItemUnSelected(Location item, List<SelectableItem<Location>> selectedItems) {
                selectedLocationsChanged(getSelectedValues());
            }

            @Override
            protected String getDisplayText(Location item) {
                return getThis().getDisplayText(item);

            }

            @Override
            protected List<Location> getData() {
                return getThis().getData();
            }

            @Override
            protected int maxItemsCanSelect() {
                return MultipleLocationPickerStep.this.maxItemsCanSelect();
            }

            @Override
            protected void onSelectFailed(String reason) {
                getThis().selectFailed(reason);
            }

            @Override
            protected String errorOnExceedSelectionLimit() {
                return String.format(Locale.ENGLISH,"Should not exceed %d",maxItemsCanSelect());
            }
        };
    }

    protected abstract void selectedLocationsChanged(List<Location> selectedValues);

    protected abstract int maxItemsCanSelect();
}
