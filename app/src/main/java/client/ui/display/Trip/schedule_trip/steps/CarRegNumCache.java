package client.ui.display.Trip.schedule_trip.steps;

import cache.AppDatabase;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarRegNumber;

public class CarRegNumCache extends AppDatabase<CarRegNumber> {
    @Override
    protected Class<CarRegNumber> getClassOfT() {
        return CarRegNumber.class;
    }

    @Override
    protected String getTableName() {
        return "car_reg_num_cache";
    }

}
