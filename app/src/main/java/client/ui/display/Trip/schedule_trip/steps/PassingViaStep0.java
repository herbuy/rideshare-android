package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import client.ui.display.Location.CSVFromLocation;
import client.ui.display.Location.location_picker.LocationPicker;
import client.ui.libraries.Form;
import core.businessobjects.Location;
import layers.render.Atom;
import libraries.android.MakeDummy;

public class PassingViaStep0 implements Form.Step {
    private Context context;

    public PassingViaStep0(Context context) {
        this.context = context;
    }

    @Override
    public String getTitle() {
        return "Passing Via";
    }

    @Override
    public String getQuestion() {
        return "Mention places you will be passing via. Helps you maximize chances of finding people to travel with";
    }

    @Override
    public View getAnswerArea() {
        return makeAnswerArea();
    }

    LocationPicker picker;
    List<Location> selectedLocations = new ArrayList<>();

    private View makeAnswerArea() {
        overallContainer = MakeDummy.linearLayoutVertical(context);
        updateUI();
        return overallContainer;

    }

    LinearLayout overallContainer;
    private void updateUI() {
        LinearLayout child = MakeDummy.linearLayoutVertical(
                context,
                selectedItemsView(),
                pickerView()
        );
        overallContainer.removeAllViews();
        overallContainer.addView(child);
    }

    LinearLayout selectedItemsView;
    private View selectedItemsView() {
        selectedItemsView = MakeDummy.linearLayoutHorizontal(context);
        for(int i = 0; i < selectedLocations.size(); i++){
            Location item = selectedLocations.get(i);
            selectedItemsView.addView(selectedItem(item,i, selectedItemsView));
        }

        return selectedItemsView;
    }

    private View selectedItem(final Location item, int i, final LinearLayout selectedItemsLinLayout) {
        final String text = item.getLocality() != null && !item.getLocality().trim().equalsIgnoreCase("") ? item.getLocality() : item.getSubAdminArea();

        final TextView textView = Atom.textViewSecondary(context,text);
        textView.setPadding(24,8,24,8);

        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.animate().scaleY(0).scaleX(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        TransitionManager.beginDelayedTransition(overallContainer);
                        selectedItemsLinLayout.removeView(textView);
                    }
                });
                selectedLocations.remove(item);
                //updateUI();
            }
        });

        if(i == selectedLocations.size() - 1){
            textView.setScaleX(0);
            textView.setScaleY(0);
            textView.animate().scaleX(1).scaleY(1);
        }
        return textView;
    }

    private View pickerView() {
        picker = new LocationPicker(context){
            @Override
            public void onLocationSelected(Location location) {
                selectedLocations.remove(location);
                selectedLocations.add(location);
                //updateUI();
                TransitionManager.beginDelayedTransition(overallContainer);
                selectedItemsView.addView(selectedItem(location,selectedItemsView.getChildCount(), selectedItemsView));
            }

            @Override
            protected String renderLocationText(Location location) {
                return new CSVFromLocation(location).getText();
            }
        };

        return picker.getView();

    }

    public Collection<? extends Location> getAnswer() {
        return selectedLocations;
    }
}
