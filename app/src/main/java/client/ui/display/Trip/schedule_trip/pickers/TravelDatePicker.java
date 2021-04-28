package client.ui.display.Trip.schedule_trip.pickers;


import android.content.Context;

import java.util.Arrays;
import java.util.List;

import libraries.android.MessageBox;
import libraries.HerbuyCalendar;

public abstract class TravelDatePicker extends SingleItemPicker<HerbuyCalendar> {
    public TravelDatePicker(Context context) {
        super(context);
    }

    @Override
    protected void onItemSelected(HerbuyCalendar day) {
        MessageBox.show(TravelDatePicker.this.getSelectedValue().getFriendlyDate(),context);
    }

    @Override
    protected String getDisplayText(HerbuyCalendar day) {
        return day.getFriendlyDate();
    }

    @Override
    protected List<HerbuyCalendar> getData() {
        return Arrays.asList(
                new HerbuyCalendar(System.currentTimeMillis()),
                new HerbuyCalendar(System.currentTimeMillis()).nextDay(),
                new HerbuyCalendar(System.currentTimeMillis()).nextDay().nextDay()
        );
    }
}
