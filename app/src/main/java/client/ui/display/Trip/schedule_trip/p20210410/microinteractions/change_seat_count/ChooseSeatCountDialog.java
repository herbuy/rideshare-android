package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_seat_count;

import android.content.Context;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.PickerDialog;

public class ChooseSeatCountDialog extends PickerDialog<Integer> {
    public ChooseSeatCountDialog(Context context) {
        super(context);
    }

    @Override
    protected String getDialogTitle() {
        return "Choose number of seats";
    }

    @Override
    protected String getDisplayText(Integer value) {
        return String.format(Locale.ENGLISH,"%d %s",value, (value == 1 ? "seat" : "seats"));
    }

    @Override
    protected void notifyObservers(Integer data) {
        EventBusForTaskUploadTrip.seatCountChanged.notifyObservers(data);
    }

    @Override
    protected List<Integer> createDateList() {
        return Arrays.asList(
                1,2,3,4,5
        );
    }
}
