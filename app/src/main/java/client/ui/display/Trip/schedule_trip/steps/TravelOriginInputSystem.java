package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;

public abstract class TravelOriginInputSystem extends SingleLocationInputSystem {
    public TravelOriginInputSystem(Context context) {
        super(context);
    }



    @Override
    public String getTitle() {
        return "Set off location";
    }

    @Override
    public String getQuestion() {
        return "Where you will be setting off from";
    }

}
