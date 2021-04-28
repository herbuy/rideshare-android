package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;
import android.view.View;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPicker;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPickerForApplication;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;

public class SeatsAvailableInputDialog extends TripInputDialog{
    public SeatsAvailableInputDialog(Context context, TripDataInputListener inputListener) {
        super(context, inputListener);
    }

    TitleSetCallback.TitleChangerLink titleChangerLink;
    public View getView() {
        return makeWrapper(driverTitleText(), body(), new TitleSetCallback() {
            @Override
            public void receiveLink(TitleChangerLink titleChangerLink) {
                SeatsAvailableInputDialog.this.titleChangerLink = titleChangerLink;
            }
        });
    }


    private View body() {
        ItemPicker<Integer> picker = new ItemPickerForApplication<Integer>(context) {

            @Override
            protected void onItemSelected(SelectableItem<Integer> sender, Integer newlySelectedItem, List<Integer> allSelectedItems) {
                inputListener.onSeatsAvailableChanged(newlySelectedItem);
            }

            @Override
            protected void onItemUnselected(SelectableItem<Integer> item, Integer unselectedItem, List<Integer> allSelectedItems) {
                inputListener.onSeatsAvailableChanged(null);
            }

            @Override
            public String getDisplayText(Integer value) {
                return String.format(Locale.ENGLISH,"%d %s",value,value == 1 ? "seat" : "seats");
            }
        };
        picker.setData(getData());
        return picker.getView();
    }

    protected List<Integer> getData() {
        return Arrays.asList(
                1,
                2,
                3,
                4,
                5
        );
    }

    @Override
    public boolean appliesToPassenger() {
        return false;
    }

    public String driverTitleText() {
        return "Seats available";
    }
    public String passengerTitleText() {
        return "How many seats do you want?";
    }

    public void changeViewToPassenger() {
        titleChangerLink.changeTitle(passengerTitleText());
    }
    public void changeViewToDriver() {
        titleChangerLink.changeTitle(driverTitleText());
    }
}
