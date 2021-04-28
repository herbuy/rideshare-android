package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_date;

import android.content.Context;
import android.view.View;

import java.util.Locale;

import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.ChangeValueTrigger;
import libraries.HerbuyCalendar;
import libraries.ObserverList;

public class ChangeDateTrigger extends ChangeValueTrigger<HerbuyCalendar> {
    public ChangeDateTrigger(Context context) {
        super(context);
    }

    @Override
    protected void subscribeToEventBus(ObserverList.Observer<HerbuyCalendar> calllback) {
        EventBusForTaskUploadTrip.travelDateChanged.add(calllback);
    }

    @Override
    protected String getDisplayText(HerbuyCalendar item) {
        String friendlyDate = String.format(
                Locale.ENGLISH,
                "<b>%s</b> <br/><small>%d %s %d</small>",
                item.getFriendlyDayNameLong(),
                item.getDayOfMonth(),
                item.getMonthNameShort(),
                item.getYear()

        );
        return friendlyDate;

        //return eventArgs.getFriendlyDate();
    }

    @Override
    protected String question() {
        return "Travel Date";
    }

    @Override
    protected void onClickChangeValue(View sender) {
        EventBusForTaskUploadTrip.startLoopForChangeTravelDate(sender);
    }
}