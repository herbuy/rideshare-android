package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;
import android.view.View;

import client.ui.display.Trip.schedule_trip.pickers.MonetaryAmountInputSystem;
import libraries.android.MakeDummy;

public abstract class FuelChargeInputSystem  extends TripSchedulingStep {
    private Context context;

    public FuelChargeInputSystem(Context context) {
        this.context = context;
    }

    @Override
    public String getTitle() {
        return "Fuel Charges";
    }

    @Override
    public String getQuestion() {
        return "How much will you charge (per seat) as fuel contribution";
    }

    @Override
    public boolean isForDriversOnly() {
        return true;
    }

    @Override
    public View getAnswerArea() {
        return MakeDummy.scrollView(timePicker());
    }

    MonetaryAmountInputSystem picker;
    private View timePicker() {

        picker = new MonetaryAmountInputSystem(context){
            @Override
            protected void amountChanged(Integer newAmount) {
                FuelChargeInputSystem.this.amountChanged(newAmount);
            }
        };

        return picker.getView();

    }

    protected abstract void amountChanged(Integer newAmount);

}
