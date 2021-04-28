package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_car_reg_number;

import android.content.Context;
import android.view.View;

import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.ChangeValueTrigger;
import libraries.ObserverList;

public class ChangeCarRegNumTrigger extends ChangeValueTrigger<String> {
    public ChangeCarRegNumTrigger(Context context) {
        super(context);
    }

    @Override
    protected void subscribeToEventBus(ObserverList.Observer<String> calllback) {
        EventBusForTaskUploadTrip.carRegNumberChanged.add(calllback);
    }

    @Override
    protected String getDisplayText(String eventArgs) {
        return eventArgs;
    }

    @Override
    protected String question() {
        return "Car registration number";
    }

    @Override
    protected void onClickChangeValue(View sender) {
        EventBusForTaskUploadTrip.startLoopForChangeCarRegNumber(context);
    }


}
