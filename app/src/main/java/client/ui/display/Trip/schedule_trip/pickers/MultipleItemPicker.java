package client.ui.display.Trip.schedule_trip.pickers;

import android.content.Context;

public abstract class MultipleItemPicker<ListItemType> extends ItemPicker<ListItemType> {

    public MultipleItemPicker(Context context) {
        super(context);
    }


    @Override
    protected int minItemsCanSelect() {
        return 0;
    }

    @Override
    protected String errorIfSelectionCountCantFallBelowMinimum() {
        return "";
    }

    @Override
    protected void onUnselectFailed(String reason) {

    }

}
