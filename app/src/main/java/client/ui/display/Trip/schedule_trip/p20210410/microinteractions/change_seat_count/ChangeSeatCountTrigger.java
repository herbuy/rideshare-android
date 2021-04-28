package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_seat_count;

import android.content.Context;
import android.view.View;

import java.util.Locale;

import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.ChangeValueTrigger;
import libraries.ObserverList;

public class ChangeSeatCountTrigger extends ChangeValueTrigger<Integer> {
    private String question;
    public ChangeSeatCountTrigger(Context context, String question) {
        super(context);
        this.question = question;
    }

    @Override
    protected void subscribeToEventBus(ObserverList.Observer<Integer> calllback) {
        EventBusForTaskUploadTrip.seatCountChanged.add(calllback);
    }

    @Override
    protected String getDisplayText(Integer eventArgs) {
        return String.format(Locale.ENGLISH,"<b>%d</b> %s", eventArgs, (eventArgs == 1 ? "seat":"seats"));
    }

    @Override
    protected String question() {
        return question;
    }

    @Override
    protected void onClickChangeValue(View sender) {
        EventBusForTaskUploadTrip.startLoopForChangeSeatCount(sender);
    }
}
