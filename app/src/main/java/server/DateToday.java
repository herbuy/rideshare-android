package server;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateToday {

    public static String get() {
        Calendar calendar = new GregorianCalendar();
        return String.format(
                "%d-%d-%d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

    }
}
