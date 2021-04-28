package client.ui.display.Trip.schedule_trip.p20210410.shared;

import android.content.Context;
import client.ui.display.Location.CSVFromLocation;
import core.businessobjects.Location;

public abstract class ChangeLocationTrigger extends ChangeValueTrigger<Location> {
    public ChangeLocationTrigger(Context context) {
        super(context);
    }

    @Override
    protected String getDisplayText(Location eventArgs) {
        return new CSVFromLocation(eventArgs).getText();
    }

    @Override
    protected boolean isNullOrEmpty(Location eventArgs) {
        if(eventArgs == null){
            return true;
        }
        String text = new CSVFromLocation(eventArgs).getText();
        if(text == null || text.trim().equalsIgnoreCase("")){
            return true;
        }
        return false;
    }
}
