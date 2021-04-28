package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_origin;

import android.content.Context;
import android.view.View;

import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.ChangeLocationTrigger;
import core.businessobjects.Location;
import libraries.ObserverList;


public class ChangeOriginTrigger extends ChangeLocationTrigger {
    public ChangeOriginTrigger(Context context) {
        super(context);
    }

    @Override
    protected String question() {
        return "From...";
    }

    @Override
    protected void onClickChangeValue(View sender) {
        EventBusForTaskUploadTrip.startLoopForChangeOrigin(sender);
    }

    @Override
    protected void subscribeToEventBus(ObserverList.Observer<Location> calllback) {
        EventBusForTaskUploadTrip.originChanged.add(calllback);
    }


}
