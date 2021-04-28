package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import client.ui.display.Trip.schedule_trip.SelectableItem;
import client.ui.display.Trip.schedule_trip.pickers.DrivingStatusPicker;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;

public abstract class DrivingStatusInputSystem  extends TripSchedulingStep {
    private Context context;

    public DrivingStatusInputSystem(Context context) {
        this.context = context;
    }

    @Override
    public String getTitle() {
        return "Car Information";
    }

    @Override
    public String getQuestion() {
        return "Are you driving?";
    }

    TextView sectionForSelectedAnswer;
    @Override
    public View getAnswerArea() {
        sectionForSelectedAnswer = Atom.textView(context,"Selected: None");
        sectionForSelectedAnswer.setPadding(paddingHorizontal(),0,paddingHorizontal(),0);
        return MakeDummy.linearLayoutVertical(
                context,
                sectionForSelectedAnswer,
                timePicker()
        );

    }

    private int paddingHorizontal() {
        return Dp.one_point_5_em();
    }

    DrivingStatusPicker picker;
    private View timePicker() {

        picker = new DrivingStatusPicker(context){
            @Override
            protected void onItemSelected(Boolean hour) {
                drivingStatusChanged(hour);
            }

            @Override
            protected void onItemUnSelected(Boolean item, List<SelectableItem<Boolean>> selectedItems) {
                drivingStatusChanged(false);
            }

        };


        return MakeDummy.scrollView(picker.getView());

    }

    protected abstract void drivingStatusChanged(Boolean isDriving);


}
