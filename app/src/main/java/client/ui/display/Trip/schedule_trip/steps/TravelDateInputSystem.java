package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import client.ui.display.Trip.schedule_trip.SelectableItem;
import client.ui.display.Trip.schedule_trip.pickers.TravelDatePicker;
import libraries.HerbuyCalendar;

public abstract class TravelDateInputSystem extends TripSchedulingStep {
    private Context context;

    public TravelDateInputSystem(Context context) {
        this.context = context;
    }

    @Override
    public String getTitle() {
        return "Travel Date";
    }

    @Override
    public String getQuestion() {
        return "When do you intend to travel?";
    }

    @Override
    public View getAnswerArea() {
        return timePicker();
    }

    TravelDatePicker travelDatePicker;
    HerbuyCalendar day;
    private View timePicker() {

        travelDatePicker = new TravelDatePicker(context) {
            @Override
            protected void onItemSelected(HerbuyCalendar day) {
                TravelDateInputSystem.this.day = day;
                onTripDateChanged(day);
            }

            @Override
            protected void onItemUnSelected(HerbuyCalendar item, List<SelectableItem<HerbuyCalendar>> selectedItems) {
                onTripDateChanged(null);
            }
        };

        return travelDatePicker.getView();

    }

    protected abstract void onTripDateChanged(HerbuyCalendar newDate);

    public String getAnswer() {
        return String.format("%d-%d-%d", day.getYear(), day.getMonth() + 1, day.getDayOfMonth());
    }

    private class FriendlyTimePicker {
        Context context;

        public FriendlyTimePicker(Context context) {
            this.context = context;
        }

        public View getView(){
            String[] dates = new String[]{
                    "Today",
                    "Tomorrow",
                    "Wednesday"
            };
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            for(String date : dates){
                linearLayout.addView(dateView(date));
            }
            return linearLayout;


        }

        private View dateView(String date) {
            TextView dateView = new TextView(context);
            dateView.setPadding(24,24,24,24);

            dateView.setText(Html.fromHtml(date));
            //dateView.setOnClickListener();
            return dateView;
        }
    }
}
