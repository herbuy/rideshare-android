package client.ui.display.Trip.schedule_trip.p20210410;

import android.content.Context;
import android.view.ViewGroup;

import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_seat_count.ChangeSeatCountTrigger;
import core.businessmessages.toServer.ParamsForScheduleTrip;

public class FindARideForm extends FindTravelMateForm {
    public FindARideForm(Context context) {
        super(context);
    }

    @Override
    protected void addMoreTriggers(ViewGroup viewGroup) {
        viewGroup.addView(new ChangeSeatCountTrigger(context,"Seats wanted").getView());
    }

    @Override
    protected void processBeforeUpload(ParamsForScheduleTrip params) {
        params.setVehicleModel("");
        params.setVehicleRegNumber("");
        params.setFuelCharge(0);
    }
}
