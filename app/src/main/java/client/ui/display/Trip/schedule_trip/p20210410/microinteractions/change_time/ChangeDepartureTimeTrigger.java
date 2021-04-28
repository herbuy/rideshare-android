package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_time;

import android.content.Context;
import android.view.View;

import java.util.Locale;

import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.ChangeValueTrigger;
import libraries.ObserverList;

public class ChangeDepartureTimeTrigger extends ChangeValueTrigger<DepartureTime> {
    public ChangeDepartureTimeTrigger(Context context) {
        super(context);
    }

    @Override
    protected void subscribeToEventBus(ObserverList.Observer<DepartureTime> calllback) {
        EventBusForTaskUploadTrip.departureTimeChanged.add(calllback);
    }

    @Override
    protected String getDisplayText(DepartureTime eventArgs) {
        return String.format(
                Locale.ENGLISH,
                "<b>%d</b>%s",
                eventArgs.getHourOnTwelveHourClock(),
                eventArgs.getAmOrPm()
        );
    }

    @Override
    protected String question() {
        return "Departure time";
    }

    @Override
    protected void onClickChangeValue(View sender) {
        EventBusForTaskUploadTrip.startLoopForChangeDepartureTime(sender);

    }
}
