package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;

import java.util.List;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarRegNumber;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import client.ui.display.Trip.schedule_trip.steps.CarRegNumCache;

public class CarRegNumInputDialog extends TextInputDialog<CarRegNumber> {
    public CarRegNumInputDialog(Context context, TripDataInputListener inputListener) {
        super(context, inputListener);
    }

    @Override
    protected String getQuestion() {
        return "Car registration number?";
    }

    @Override
    protected String getHint() {
        return "E.g. UAX 506 T";
    }

    @Override
    protected String getDisplayText(CarRegNumber value) {
        return value.regNumber;
    }

    @Override
    protected void onItemSelected(CarRegNumber newlySelectedItem) {
        inputListener.onCarRegNumChanged(newlySelectedItem);
    }

    @Override
    protected CarRegNumber createListItem(String newValue) {
        CarRegNumber carRegNumber = new CarRegNumber();
        carRegNumber.regNumber = newValue;
        carRegNumber.timestampLastUpdated = System.currentTimeMillis();
        return carRegNumber;
    }

    @Override
    protected void saveListItem(CarRegNumber item) {
        new CarRegNumCache().save(item.regNumber,item);
    }

    @Override
    protected List<CarRegNumber> getListItems() {
        return new CarRegNumCache().selectAll();
    }

    @Override
    protected int getLongCompare(CarRegNumber item2, CarRegNumber item1) {
        return Long.compare(item2.timestampLastUpdated,item1.timestampLastUpdated);
    }

    @Override
    public boolean appliesToPassenger() {
        return false;
    }
}
