package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_car_model;

import android.content.Context;
import android.view.View;

import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.ChangeValueTrigger;
import libraries.ObserverList;

public class ChangeCarModelTrigger extends ChangeValueTrigger<String> {
    public ChangeCarModelTrigger(Context context) {
        super(context);
    }

    @Override
    protected void subscribeToEventBus(ObserverList.Observer<String> calllback) {
        EventBusForTaskUploadTrip.carModelChanged.add(calllback);
    }

    @Override
    protected String getDisplayText(String eventArgs) {
        return eventArgs;
    }

    @Override
    protected String question() {
        return "Your car model";
    }

    @Override
    protected void onClickChangeValue(View sender) {
        EventBusForTaskUploadTrip.startLoopForChangeCarModel(context);
    }
}
