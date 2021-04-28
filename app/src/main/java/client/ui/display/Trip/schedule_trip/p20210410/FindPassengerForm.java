package client.ui.display.Trip.schedule_trip.p20210410;

import android.content.Context;
import android.view.ViewGroup;

import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_car_info.ChangeCarInfoTrigger;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_fuel_contribution.ChangeFuelContributionTrigger;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_seat_count.ChangeSeatCountTrigger;
import client.ui.display.Trip.schedule_trip.steps.SetDestinationInfo;
import core.businessmessages.toServer.ParamsForScheduleTrip;


public class FindPassengerForm extends FindTravelMateForm {
    public FindPassengerForm(Context context) {
        super(context);
    }

    @Override
    protected void addMoreTriggers(ViewGroup viewGroup) {
        viewGroup.addView(new ChangeCarInfoTrigger(context).getView());
        //viewGroup.addView(new ChangeCarModelTrigger(context).getView());
        //viewGroup.addView(new ChangeCarRegNumTrigger(context).getView());
        viewGroup.addView(new ChangeSeatCountTrigger(context, "Seats available").getView());
        viewGroup.addView(new ChangeFuelContributionTrigger(context).getView());
    }

}
