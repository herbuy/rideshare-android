package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_car_info;

import android.content.Context;
import android.view.View;

import java.util.Locale;

import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.ChangeValueTrigger;
import libraries.ObserverList;

public class ChangeCarInfoTrigger extends ChangeValueTrigger<CarInfo> {
    public ChangeCarInfoTrigger(Context context) {
        super(context);
    }

    @Override
    protected void subscribeToEventBus(ObserverList.Observer<CarInfo> calllback) {
        EventBusForTaskUploadTrip.carInfoChanged.add(calllback);
    }

    @Override
    protected String getDisplayText(CarInfo eventArgs) {
        return String.format(
                Locale.ENGLISH,
                "<b>%s</b><br/>%s",
                eventArgs.carModel,
                eventArgs.carRegNumber
        );
    }

    @Override
    protected String question() {
        return "Car details";
    }

    @Override
    protected void onClickChangeValue(View sender) {
        EventBusForTaskUploadTrip.startLoopForChangeCarInfo(sender);
    }
}
