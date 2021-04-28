package demos.trips.add.viewpager.fragments;

import android.view.View;

import java.util.Arrays;
import java.util.List;

import client.ui.display.Trip.schedule_trip.SelectableItem;
import client.ui.display.Trip.schedule_trip.pickers.SingleItemPicker;
import libraries.HerbuyCalendar;

public class TravelDatePicker  extends FragmentForAddTripDialog<HerbuyCalendar>  {
    public TravelDatePicker(Parameters<HerbuyCalendar> params) {
        super(params);
    }

    @Override
    protected View makePicker() {
        return new SingleItemPicker<HerbuyCalendar>(params.getContext()){
            @Override
            protected void onItemSelected(HerbuyCalendar item) {
                params.onValueChanged(item);

            }

            @Override
            protected void onItemUnSelected(HerbuyCalendar item, List<SelectableItem<HerbuyCalendar>> selectedItems) {
                params.onValueChanged(null);
            }

            @Override
            protected String getDisplayText(HerbuyCalendar item) {
                return item.getFriendlyDate();
            }

            @Override
            protected List<HerbuyCalendar> getData() {
                return Arrays.asList(
                        new HerbuyCalendar(System.currentTimeMillis()),
                        new HerbuyCalendar(System.currentTimeMillis()).nextDay(),
                        new HerbuyCalendar(System.currentTimeMillis()).nextDay().nextDay()
                );
            }
        }.getView();
    }

    @Override
    protected String question() {
        return "When do you intend to travel?";
    }
}
