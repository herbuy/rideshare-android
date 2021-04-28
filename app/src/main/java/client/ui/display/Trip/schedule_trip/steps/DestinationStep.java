package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;

public abstract class DestinationStep extends SingleLocationInputSystem {
    public DestinationStep(Context context) {
        super(context);
    }

    @Override
    public String getTitle() {
        return "Am going to...";
    }

    @Override
    public String getQuestion() {
        return "Pick or search for destination";
    }
}
