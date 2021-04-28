package client.ui.display.Trip.schedule_trip.pickers;


import android.content.Context;

import java.util.Arrays;
import java.util.List;

import client.ui.display.Trip.schedule_trip.SelectableItem;

public abstract class MonetaryAmountInputSystem extends SingleItemPicker<Integer> {
    public MonetaryAmountInputSystem(Context context) {
        super(context);
    }

    @Override
    protected void onItemSelected(Integer amount) {
        amountChanged(amount);
    }

    @Override
    protected void onItemUnSelected(Integer item, List<SelectableItem<Integer>> selectedItems) {
        amountChanged(null);
    }

    protected abstract void amountChanged(Integer newAmount);

    @Override
    protected String getDisplayText(Integer day) {
        return (day == 0 ? "To be discussed": day + " Ushs");
    }

    @Override
    protected List<Integer> getData() {
        return Arrays.asList(
                0,
                5000,
                10000,
                15000,
                20000,
                25000,
                30000,
                35000,
                40000,
                45000,
                50000,
                55000,
                60000
        );
    }
}
