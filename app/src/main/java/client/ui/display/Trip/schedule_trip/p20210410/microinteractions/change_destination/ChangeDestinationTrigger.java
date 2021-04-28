package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_destination;

import android.content.Context;
import android.view.View;

import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.ChangeLocationTrigger;
import core.businessobjects.Location;
import libraries.ObserverList;


public class ChangeDestinationTrigger extends ChangeLocationTrigger {
    public ChangeDestinationTrigger(Context context) {
        super(context);
    }

    @Override
    protected String question() {
        return "I'm travelling to...";
    }

    @Override
    protected void onClickChangeValue(View sender) {
        EventBusForTaskUploadTrip.startLoopForChangeDestination(sender);
    }

    @Override
    protected void subscribeToEventBus(ObserverList.Observer<Location> calllback) {
        EventBusForTaskUploadTrip.destinationChanged.add(calllback);
    }


}
