package client.ui.display.Trip.schedule_trip.pickers;


import android.content.Context;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarModel;
import client.ui.display.Trip.schedule_trip.steps.CarModelCache;

public abstract class CarModelPicker extends SingleItemPicker<CarModel> {
    public CarModelPicker(Context context) {
        super(context);
    }

    @Override
    protected String getDisplayText(CarModel item) {
        return item.model;
    }

    @Override
    protected List<CarModel> getData() {
        List<CarModel> list = new CarModelCache().selectAll();
        Collections.sort(list,comparator());
        return list;
    }

    protected abstract Comparator<? super CarModel> comparator();
}
