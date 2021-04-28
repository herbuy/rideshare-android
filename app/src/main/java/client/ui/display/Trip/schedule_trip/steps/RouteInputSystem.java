package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;

import java.util.Collection;

import core.businessobjects.Location;

public abstract class RouteInputSystem extends MultipleLocationPickerStep {

    public RouteInputSystem(Context context) {
        super(context);
    }

    @Override
    public String getTitle() {
        return "Passing Via...";
    }

    @Override
    public String getQuestion() {
        return "Mention places you will be passing via. Helps you maximize chances of finding people to travel with";
    }

    public Collection<? extends Location> getAnswer() {
        return getLocationPicker().getSelectedValues();
    }



    @Override
    protected int maxItemsCanSelect() {
        return 3;
    }
}
