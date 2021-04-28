package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_time;

public class DepartureTime {
    private int hour;

    public DepartureTime(int hour) {
        this.hour = hour;
    }

    public int getHourOnTwelveHourClock(){
        int adjustedHour = (hour % 12);
        return adjustedHour == 0 ? 12 : adjustedHour;
    }

    public String getAmOrPm(){
        return hour < 12 ? "am" : "pm";
    }

    public String getFriendlyHour(){
        return getHourOnTwelveHourClock() + getAmOrPm();

    }

    public String toTime() {
        return String.format("%d:00", hour);
    }

    public int get() {
        return hour;
    }
}
