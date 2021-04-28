package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import client.ui.display.Trip.schedule_trip.SelectableItem;
import client.ui.display.Trip.schedule_trip.pickers.HourOfDayPicker;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;

public abstract class TravelTimeStep extends TripSchedulingStep {
    private Context context;

    public TravelTimeStep(Context context) {
        this.context = context;
    }

    @Override
    public String getTitle() {
        return "Departure Time";
    }

    @Override
    public String getQuestion() {
        return "What time would you prefer to travel?";
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

    HourOfDayPicker picker;
    private View timePicker() {

        picker = new HourOfDayPicker(context){
            @Override
            protected void onItemSelected(Hour hour) {
                indicatedSelectedValue(hour);
                travelTimeChanged(hour);
            }

            @Override
            protected void onItemUnSelected(Hour item, List<SelectableItem<Hour>> selectedItems) {
                travelTimeChanged(null);
            }

            private void indicatedSelectedValue(Hour hour) {
                sectionForSelectedAnswer.setText(Html.fromHtml(
                        String.format("Selected: <font color='#ff0000'>%s</font>",hour.getFriendlyHour())
                ));
            }
        };


        return MakeDummy.scrollView(picker.getView());

    }

    protected abstract void travelTimeChanged(HourOfDayPicker.Hour hour);

    public String getAnswer() {
        return picker.getSelectedValue().toTime();
    }

}
