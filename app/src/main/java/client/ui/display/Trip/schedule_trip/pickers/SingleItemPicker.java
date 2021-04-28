package client.ui.display.Trip.schedule_trip.pickers;

import android.content.Context;

public abstract class SingleItemPicker<ListItemType> extends ItemPicker<ListItemType> {
    public SingleItemPicker(Context context) {
        super(context);
    }

    @Override
    protected int maxItemsCanSelect() {
        return 1;
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

    @Override
    protected void onSelectFailed(String reason) {

    }

    @Override
    protected String errorOnExceedSelectionLimit() {
        return "";
    }
}
