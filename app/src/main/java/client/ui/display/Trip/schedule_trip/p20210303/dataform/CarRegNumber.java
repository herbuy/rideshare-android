package client.ui.display.Trip.schedule_trip.p20210303.dataform;

public class CarRegNumber {
    public String regNumber = "";
    public long timestampLastUpdated = System.currentTimeMillis();

    public CarRegNumber() {
    }

    public CarRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }
}
