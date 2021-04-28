package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_date;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPickerForApplication;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;
import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.DialogActivity;
import layers.render.Atom;
import libraries.HerbuyCalendar;
import resources.TransitionName;

public class ChangeTravelDateActivity extends DialogActivity {
    protected String getTransitionName() {
        return TransitionName.date;
    }
    protected String getHeaderText() {
        return "Your travel date";
    }
    protected View getActivityContent() {
        final Context context = this;
        ItemPickerForApplication<HerbuyCalendar> picker = new ItemPickerForApplication<HerbuyCalendar>(this) {

            @Override
            public SelectableItem.Padding getPaddingOrNull() {
                return getPadding();
            }

            @Override
            protected void onItemSelected(SelectableItem<HerbuyCalendar> sender, HerbuyCalendar newlySelectedValue, List<HerbuyCalendar> allSelectedItems) {
                EventBusForTaskUploadTrip.travelDateChanged.notifyObservers(newlySelectedValue);
                onBackPressed(sender.getView());
            }

            @Override
            protected void onItemUnselected(SelectableItem<HerbuyCalendar> sender, HerbuyCalendar unselectedItem, List<HerbuyCalendar> allSelectedItems) {

            }

            @Override
            public String getDisplayText(HerbuyCalendar value) {
                return value.getFriendlyDate();
            }

            @Override
            protected ViewGroup getContainer(Context context) {
                GridLayout layout = new GridLayout(context);
                layout.setColumnCount(2);
                return layout;
            }



            @Override
            public ItemRenderer<HerbuyCalendar> getItemRenderer() {

                return new ItemRenderer<HerbuyCalendar>() {


                    @Override
                    public View render(HerbuyCalendar item, boolean isSelected) {

                        String friendlyDate = String.format(
                                Locale.ENGLISH,
                                "<b>%s</b> <br/><small>%d %s %d</small>",
                                item.getFriendlyDayNameLong(),
                                item.getDayOfMonth(),
                                item.getMonthNameShort(),
                                item.getYear()

                        );

                        TextView container = Atom.textViewPrimaryNormal(context,friendlyDate);
                        return container;
                    }

                };
            }
        };

        picker.setData(getData());

        return picker.getView();
    }

    private List<HerbuyCalendar> getData() {
        HerbuyCalendar today = new HerbuyCalendar(System.currentTimeMillis());

        return Arrays.asList(
                today,
                today.nextDay(),
                today.nextDay().nextDay(),
                today.nextDay().nextDay().nextDay(),
                today.nextDay().nextDay().nextDay().nextDay(),
                today.nextDay().nextDay().nextDay().nextDay().nextDay(),
                today.nextDay().nextDay().nextDay().nextDay().nextDay().nextDay()
        );
    }
}