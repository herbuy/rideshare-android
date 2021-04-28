package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_seat_count;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.recyclerview.widget.GridLayoutManager;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPickerForApplication;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;
import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.DialogActivity;
import resources.TransitionName;

public class ChangeSeatCountActivity extends DialogActivity {
    @Override
    protected String getTransitionName() {
        return TransitionName.seatCount;
    }

    @Override
    protected String getHeaderText() {
        return "Choose number";
    }

    @Override
    protected View getActivityContent() {
        ItemPickerForApplication<Integer> picker = new ItemPickerForApplication<Integer>(this) {
            @Override
            protected void onItemSelected(SelectableItem<Integer> sender, Integer newlySelectedValue, List<Integer> allSelectedItems) {
                EventBusForTaskUploadTrip.seatCountChanged.notifyObservers(newlySelectedValue);
                sender.getView().setTransitionName(TransitionName.seatCount);
                onBackPressed(sender.getView());
            }

            @Override
            protected void onItemUnselected(SelectableItem<Integer> sender, Integer unselectedItem, List<Integer> allSelectedItems) {

            }

            @Override
            public String getDisplayText(Integer value) {
                return String.format(Locale.ENGLISH,"<big><b>%d</b></big><br/><small>%s</small>", value, (value == 1 ? "seat":"seats"));
            }

            @Override
            protected ViewGroup getContainer(Context context) {
                GridLayout gridLayout = new GridLayout(context);
                gridLayout.setColumnCount(3);
                return gridLayout;
            }

            @Override
            public SelectableItem.Padding getPaddingOrNull() {
                return getPadding();
            }
        };
        picker.setData(getData());
        return picker.getView();
    }

    private List<Integer> getData() {
        return Arrays.asList(
                1,2,3,4,5
        );
    }
}