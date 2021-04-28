package client.ui.display.Trip.schedule_trip.pickers;


import android.content.Context;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import client.ui.display.Trip.schedule_trip.steps.CarRegNumCache;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarRegNumber;

public abstract class CarRegNumPicker extends SingleItemPicker<CarRegNumber> {
    public CarRegNumPicker(Context context) {
        super(context);
    }

    @Override
    protected String getDisplayText(CarRegNumber item) {
        return item.regNumber;
    }

    @Override
    protected List<CarRegNumber> getData() {
        List<CarRegNumber> list = new CarRegNumCache().selectAll();
        Collections.sort(list,comparator());
        return list;
    }

    protected abstract Comparator<? super CarRegNumber> comparator();
}
