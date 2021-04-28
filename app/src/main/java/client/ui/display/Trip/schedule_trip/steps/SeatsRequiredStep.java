package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;
import android.view.View;

import client.ui.display.Trip.schedule_trip.pickers.SeatCountInputSystem;
import client.ui.libraries.Form;

public abstract class SeatsRequiredStep implements Form.Step {
    private Context context;

    public SeatsRequiredStep(Context context) {
        this.context = context;
    }

    @Override
    public String getTitle() {
        return "Seats Wanted";
    }

    @Override
    public String getQuestion() {
        return "How many seats do you want";
    }

    @Override
    public View getAnswerArea() {
        return timePicker();
    }

    SeatCountInputSystem picker;
    private View timePicker() {

        picker = new SeatCountInputSystem(context) {
            @Override
            protected void onItemSelected(Integer count) {
                super.onItemSelected(count);
            }

            @Override
            protected void onSeatCountChanged(Integer count) {
                SeatsRequiredStep.this.seatCountChanged(count);
            }
        };

        return picker.getView();

    }

    protected abstract void seatCountChanged(Integer count);

}
