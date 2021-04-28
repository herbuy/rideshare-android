package client.ui.display.Trip.schedule_trip.pickers;


import android.content.Context;

import java.util.Arrays;
import java.util.List;

public abstract class DrivingStatusPicker extends SingleItemPicker<Boolean> {
    public DrivingStatusPicker(Context context) {
        super(context);
    }

    @Override
    protected String getDisplayText(Boolean isDriving) {
        return isDriving ? "Yes, am driving" : "No, seeking for a lift";
    }

    @Override
    protected List<Boolean> getData() {
        return Arrays.asList(
                true,false
        );
    }
}
