package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;
import android.view.View;
import java.util.Arrays;
import java.util.List;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPicker;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPickerForApplication;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;

public class DrivingStatusInputDialog extends TripInputDialog {

    public DrivingStatusInputDialog(Context context, TripDataInputListener inputListener) {
        super(context, inputListener);
    }

    public View getView() {
        return makeWrapper("Are you driving?",body());
    }

    private View body() {
        ItemPicker<Boolean> picker = new ItemPickerForApplication<Boolean>(context) {

            @Override
            protected void onItemSelected(SelectableItem<Boolean> sender, Boolean newlySelectedItem, List<Boolean> allSelectedItems) {
                inputListener.drivingStatusChanged(newlySelectedItem);
            }

            @Override
            protected void onItemUnselected(SelectableItem<Boolean> item, Boolean unselectedItem, List<Boolean> allSelectedItems) {
                inputListener.drivingStatusChanged(null);
            }

            @Override
            public String getDisplayText(Boolean isDriving) {
                return isDriving ? "Yes, I will be driving" : "No, looking for a lift";
            }
        };
        picker.setData(Arrays.asList(true,false));
        return picker.getView();
    }

}
