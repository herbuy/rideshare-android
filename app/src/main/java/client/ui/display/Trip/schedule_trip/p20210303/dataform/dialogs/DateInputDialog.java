package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import java.util.Arrays;
import java.util.List;

import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPicker;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPickerForApplication;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.HerbuyCalendar;

public class DateInputDialog extends TripInputDialog {
    public DateInputDialog(Context context, TripDataInputListener inputListener) {
        super(context, inputListener);
    }

    public View getView() {
        ItemPicker<HerbuyCalendar> picker = new ItemPickerForApplication<HerbuyCalendar>(context) {

            @Override
            protected void onItemSelected(SelectableItem<HerbuyCalendar> sender, HerbuyCalendar newlySelectedItem, List<HerbuyCalendar> allSelectedItems) {
                inputListener.onDateChanged(newlySelectedItem);
            }

            @Override
            protected void onItemUnselected(SelectableItem<HerbuyCalendar> item, HerbuyCalendar unselectedItem, List<HerbuyCalendar> allSelectedItems) {
                inputListener.onDateChanged(null);
            }

            @Override
            public String getDisplayText(HerbuyCalendar value) {
                return value.getFriendlyDate();
            }
        };
        picker.setData(potentialDates());

        return makeWrapper(
                "Your travel date",
                picker.getView()
        );
    }


    private View header() {
        LinearLayout header = MakeDummy.linearLayoutVertical(
                context,
                Atom.textViewPrimaryBold(context,"Travel date"),
                Atom.textView(context,"When do you intend to travel?")
        );
        return header;
    }

    private List<HerbuyCalendar> potentialDates() {
        HerbuyCalendar today = new HerbuyCalendar(System.currentTimeMillis());
        HerbuyCalendar tomorrow = today.nextDay();
        return Arrays.asList(
                today,
                tomorrow,
                tomorrow.nextDay(),
                tomorrow.nextDay().nextDay(),
                tomorrow.nextDay().nextDay().nextDay(),
                tomorrow.nextDay().nextDay().nextDay().nextDay(),
                tomorrow.nextDay().nextDay().nextDay().nextDay().nextDay()
        );
    }
}
