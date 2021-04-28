package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;
import android.view.View;

import client.ui.display.Trip.schedule_trip.pickers.SeatCountInputSystem;
import client.ui.libraries.Form;

public abstract class SeatsAvailableInputSystem  extends TripSchedulingStep {
    private Context context;

    public SeatsAvailableInputSystem(Context context) {
        this.context = context;
    }

    @Override
    public String getTitle() {
        return "Seats available";
    }

    @Override
    public String getQuestion() {
        return "How many seats are available";
    }

    @Override
    public View getAnswerArea() {
        return timePicker();
    }

    @Override
    public boolean isForDriversOnly() {
        return true;
    }

    SeatCountInputSystem picker;
    private View timePicker() {

        picker = new SeatCountInputSystem(context){
            @Override
            protected void onSeatCountChanged(Integer count) {
                SeatsAvailableInputSystem.this.onSeatCountChanged(count);
            }
        };

        return picker.getView();

    }

    protected abstract void onSeatCountChanged(Integer count);

    public int getAnswer() {
        return picker.getSelectedValue();
    }
}
