package client.ui.display.Trip.schedule_trip.p20210303.dataform;

public class CarModel {
    public String model = "";
    public long timestampLastUpdated = System.currentTimeMillis();

    public CarModel() {
    }

    public CarModel(String model) {
        this.model = model;
    }
}
