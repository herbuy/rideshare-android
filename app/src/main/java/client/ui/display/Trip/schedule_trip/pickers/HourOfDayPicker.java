package client.ui.display.Trip.schedule_trip.pickers;


import android.content.Context;
import java.util.Arrays;
import java.util.List;

import client.ui.display.Trip.schedule_trip.SelectableItem;

public abstract class HourOfDayPicker extends SingleItemPicker<HourOfDayPicker.Hour> {
    public HourOfDayPicker(Context context) {
        super(context);
    }

    @Override
    protected String getDisplayText(Hour day) {
        return day.getFriendlyHour();
    }

    @Override
    protected List<Hour> getData() {
        return Arrays.asList(
                new Hour(6),
                new Hour(7),
                new Hour(8),
                new Hour(9),
                new Hour(10),
                new Hour(11),
                new Hour(12),
                new Hour(13),
                new Hour(14),
                new Hour(15),
                new Hour(16),
                new Hour(17),
                new Hour(18),
                new Hour(19),
                new Hour(20),
                new Hour(21),
                new Hour(22),
                new Hour(23),
                new Hour(0),
                new Hour(1),
                new Hour(2),
                new Hour(3),
                new Hour(4),
                new Hour(5)
        );
    }

    public static class Hour {
        private int hour;

        public Hour(int hour) {
            this.hour = hour;
        }

        public String getFriendlyHour(){
            if(hour == 0){
                return "12am";
            }
            else if(hour < 12){
                return hour + "am";
            }
            else if(hour == 12){
                return "12pm";
            }
            else{
                return (hour - 12) + "pm";
            }

        }

        public String toTime() {
            return String.format("%d:00", hour);
        }

        public int get() {
            return hour;
        }
    }
}
