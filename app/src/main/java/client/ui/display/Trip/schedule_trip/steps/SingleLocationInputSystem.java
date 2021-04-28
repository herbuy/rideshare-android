package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;
import java.util.List;

import client.ui.display.Trip.schedule_trip.SelectableItem;
import client.ui.display.Trip.schedule_trip.pickers.ItemPicker;
import client.ui.display.Trip.schedule_trip.pickers.SingleItemPicker;
import core.businessobjects.Location;

public abstract class SingleLocationInputSystem extends LocationPickerStep {
    public SingleLocationInputSystem(Context context) {
        super(context);

    }


    @Override
    protected ItemPicker<Location> makeLocationPicker() {
        return new SingleItemPicker<Location>(context){
            @Override
            protected void onItemSelected(Location item) {
                getThis().locationSelected(item);
                SingleLocationInputSystem.this.onLocationSelected(item);
            }

            @Override
            protected void onItemUnSelected(Location item, List<SelectableItem<Location>> selectedItems) {
                SingleLocationInputSystem.this.onLocationUnSelected(item);
            }

            @Override
            protected String getDisplayText(Location item) {
                return getThis().getDisplayText(item);

            }

            @Override
            protected List<Location> getData() {
                return getThis().getData();
            }

        };
    }

    protected abstract void onLocationSelected(Location item);
    protected abstract void onLocationUnSelected(Location item);


}
