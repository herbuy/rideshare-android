package client.ui.display.Trip.schedule_trip.pickers;


import android.content.Context;

import java.util.Arrays;
import java.util.List;

import client.ui.display.Trip.schedule_trip.SelectableItem;

public abstract class SeatCountInputSystem extends SingleItemPicker<Integer> {
    public SeatCountInputSystem(Context context) {
        super(context);
    }

    @Override
    protected void onItemSelected(Integer count) {
        onSeatCountChanged(count);
    }

    @Override
    protected void onItemUnSelected(Integer item, List<SelectableItem<Integer>> selectedItems) {
        onSeatCountChanged(null);
    }

    protected abstract void onSeatCountChanged(Integer count);

    @Override
    protected String getDisplayText(Integer day) {
        return day.toString() +  (day == 1 ? " seat": " seats");
    }

    @Override
    protected List<Integer> getData() {
        return Arrays.asList(
                1,
                2,
                3,
                4,
                5
        );
    }
}
