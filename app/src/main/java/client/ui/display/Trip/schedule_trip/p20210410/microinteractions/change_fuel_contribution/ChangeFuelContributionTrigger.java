package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_fuel_contribution;

import android.content.Context;
import android.view.View;

import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.ChangeValueTrigger;
import libraries.ObserverList;

public class ChangeFuelContributionTrigger extends ChangeValueTrigger<Integer> {
    public ChangeFuelContributionTrigger(Context context) {
        super(context);
    }

    @Override
    protected void subscribeToEventBus(ObserverList.Observer<Integer> calllback) {
        EventBusForTaskUploadTrip.fuelContributionChanged.add(calllback);
    }

    @Override
    protected String getDisplayText(Integer eventArgs) {
        return "<b>Ugx</b> "+eventArgs.toString();
    }

    @Override
    protected String question() {
        return "Fuel Contribution";
    }

    @Override
    protected void onClickChangeValue(View sender) {
        EventBusForTaskUploadTrip.startLoopForChangeFuelContribution(sender);
    }
}
