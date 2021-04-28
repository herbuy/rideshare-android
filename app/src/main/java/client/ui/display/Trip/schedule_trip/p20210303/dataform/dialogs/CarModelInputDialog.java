package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;
import android.content.Context;

import java.util.List;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarModel;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import client.ui.display.Trip.schedule_trip.steps.CarModelCache;

public class CarModelInputDialog extends TextInputDialog<CarModel> {
    public CarModelInputDialog(Context context, TripDataInputListener inputListener) {
        super(context, inputListener);
    }

    @Override
    protected String getQuestion() {
        return "Your car model?";
    }

    @Override
    protected String getHint() {
        return "E.g. Toyota prado";
    }

    @Override
    protected String getDisplayText(CarModel value) {
        return value.model;
    }

    @Override
    protected void onItemSelected(CarModel newlySelectedItem) {
        inputListener.onCarModelChanged(newlySelectedItem);
    }

    @Override
    protected CarModel createListItem(String newValue) {
        CarModel model = new CarModel();
        model.model = newValue;
        model.timestampLastUpdated = System.currentTimeMillis();
        return model;
    }

    @Override
    protected void saveListItem(CarModel item) {
        new CarModelCache().save(item);
    }

    @Override
    protected List<CarModel> getListItems() {
        return new CarModelCache().selectAll();
    }

    @Override
    protected int getLongCompare(CarModel item2, CarModel item1) {
        return Long.compare(item2.timestampLastUpdated, item1.timestampLastUpdated);
    }

    @Override
    public boolean appliesToPassenger() {
        return false;
    }
}
