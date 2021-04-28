package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_time;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPickerForApplication;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;
import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.DialogActivity;
import layers.render.Atom;
import resources.Dp;
import resources.TransitionName;

public class ChangeDepartureTimeActivity extends DialogActivity {

    protected String getTransitionName() {
        return TransitionName.time;
    }

    protected String getHeaderText() {
        return "Your Departure time";
    }

    @Override
    protected SelectableItem.Padding getPadding() {
        return new SelectableItem.Padding() {
            @Override
            public int left() {
                return Dp.normal();
            }

            @Override
            public int top() {
                return Dp.normal();
            }

            @Override
            public int right() {
                return Dp.normal();
            }

            @Override
            public int bottom() {
                return Dp.normal();
            }
        };
    }

    protected View getActivityContent() {
        final Context context = this;
        ItemPickerForApplication<DepartureTime> picker = new ItemPickerForApplication<DepartureTime>(context) {
            @Override
            public SelectableItem.Padding getPaddingOrNull() {
                return getPadding();
            }

            @Override
            protected void onItemSelected(SelectableItem<DepartureTime> sender, DepartureTime newlySelectedValue, List<DepartureTime> allSelectedItems) {
                EventBusForTaskUploadTrip.departureTimeChanged.notifyObservers(newlySelectedValue);
                onBackPressed(sender.getView());
            }

            @Override
            protected void onItemUnselected(SelectableItem<DepartureTime> sender, DepartureTime unselectedItem, List<DepartureTime> allSelectedItems) {

            }

            @Override
            public String getDisplayText(DepartureTime value) {

                return String.format(
                        Locale.ENGLISH,
                        "<font color='#242424'><b>%d</b></font><small>%s</small>",
                        value.getHourOnTwelveHourClock(),
                        value.getAmOrPm()
                );

            }

            @Override
            public ItemRenderer<DepartureTime> getItemRenderer() {
                return new ItemRenderer<DepartureTime>() {
                    @Override
                    public View render(DepartureTime item, boolean isSelected) {

                        TextView container = Atom.textView(context,getDisplayText(item));
                        container.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                        return container;
                    }
                };
            }

            @Override
            protected ViewGroup getContainer(Context context) {
                GridLayout layout = new GridLayout(context);
                layout.setColumnCount(4);
                return layout;
            }

        };
        picker.setData(getData());
        return picker.getView();
    }

    private List<DepartureTime> getData() {
        return Arrays.asList(
                new DepartureTime(6),
                new DepartureTime(7),
                new DepartureTime(8),
                new DepartureTime(9),
                new DepartureTime(10),
                new DepartureTime(11),
                new DepartureTime(12),
                new DepartureTime(13),
                new DepartureTime(14),
                new DepartureTime(15),
                new DepartureTime(16),
                new DepartureTime(17),
                new DepartureTime(18),
                new DepartureTime(19),
                new DepartureTime(20),
                new DepartureTime(21),
                new DepartureTime(22),
                new DepartureTime(23),
                new DepartureTime(0),
                new DepartureTime(1),
                new DepartureTime(2),
                new DepartureTime(3),
                new DepartureTime(4),
                new DepartureTime(5)
        );
    }
}