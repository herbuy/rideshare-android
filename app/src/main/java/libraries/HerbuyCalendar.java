package libraries;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class HerbuyCalendar extends GregorianCalendar {

    public String getFriendlyDate() {
        return String.format(
                Locale.ENGLISH,
                "%s %d %s, %d",
                getFriendlyDayName(),
                getDayOfMonth(),
                getMonthNameShort(),
                getYear()
        );
    }

    private String getFriendlyDayName(boolean getFullName){
        if(isToday()){
            return "Today";
        }
        else if(isTomorrow()){
            return "Tomorrow";
        }
        else if(isYesterday()){
            return "Yesterday";
        }
        else if(getFullName){
            return getDayNameLong();
        }
        else{
            return getDayNameShort();
        }
    }

    public String getFriendlyDayName() {
        return getFriendlyDayName(false);
    }

    public String getFriendlyDayNameLong() {
        return getFriendlyDayName(true);
    }


    public boolean isToday() {
        Calendar calendarToday = new GregorianCalendar();
        return get(YEAR) == calendarToday.get(YEAR) &&
                get(MONTH) == calendarToday.get(MONTH) &&
                get(DAY_OF_MONTH) == calendarToday.get(DAY_OF_MONTH)
                ;
    }
    public boolean isTomorrow() {
        Calendar calendarToday = new GregorianCalendar();
        calendarToday.add(DAY_OF_MONTH, 1);

        return get(YEAR) == calendarToday.get(YEAR) &&
                get(MONTH) == calendarToday.get(MONTH) &&
                get(DAY_OF_MONTH) == calendarToday.get(DAY_OF_MONTH)
                ;
    }

    public boolean isYesterday() {
        Calendar calendarToday = new GregorianCalendar();
        calendarToday.add(DAY_OF_MONTH, -1);

        return get(YEAR) == calendarToday.get(YEAR) &&
                get(MONTH) == calendarToday.get(MONTH) &&
                get(DAY_OF_MONTH) == calendarToday.get(DAY_OF_MONTH)
                ;
    }

    public int getYear(){
        return get(YEAR);
    }

    public int getMonth(){
        return get(MONTH);
    }
    public int getDayOfMonth(){
        return get(DAY_OF_MONTH);
    }
    public int getDayOfWeek(){
        return get(DAY_OF_WEEK);
    }
    public int getDayOfYear(){
        return get(DAY_OF_YEAR);
    }

    public HerbuyCalendar nextDay() {
        HerbuyCalendar calendar = new HerbuyCalendar(getYear(),getMonth(),getDayOfMonth());
        calendar.add(DAY_OF_MONTH, 1);
        return calendar;
    }

    public HerbuyCalendar previousDay() {
        HerbuyCalendar calendar = new HerbuyCalendar(getYear(),getMonth(),getDayOfMonth());
        calendar.add(DAY_OF_MONTH, -1);
        return calendar;
    }

    public String getMonthNameShort() {
        int month = this.get(Calendar.MONTH);
        return getMonthNameShort(month);
    }

    private String getMonthNameShort(int month) {
        switch (month) {
            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Aug";
            case 8:
                return "Sep";
            case 9:
                return "Oct";
            case 10:
                return "Nov";
            case 11:
                return "Dec";
            case 12:
                return "UniDec";

        }
        return "Unknown";
    }

    public String getDayName(boolean getFullname) {
        int dayOfWeek = this.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                return getFullname ? "Sunday" : "Sun";
            case 2:
                return getFullname ? "Monday" : "Mon";
            case 3:
                return getFullname ? "Tuesday" : "Tue";
            case 4:
                return getFullname ? "Wednesday" : "Wed";
            case 5:
                return getFullname ? "Thursday" : "Thu";
            case 6:
                return getFullname ? "Friday" : "Fri";
            case 7:
                return getFullname ? "Saturday" : "Sat";

        }
        return "Unknown";
    }

    public String getDayNameShort() {
        return getDayName(false);
    }

    public String getDayNameLong() {
        return getDayName(true);
    }

    public static HerbuyCalendar parse(String dateString){
        try{

            String[] dateParts = dateString.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]) - 1;
            int day = Integer.parseInt(dateParts[2]);
            return new HerbuyCalendar(year,month,day);
        }
        catch (Throwable t){
            throw new RuntimeException(t);
        }
    }

    public boolean before(String dateString){
        return this.before(parse(dateString));
    }

    public boolean after(String dateString){
        return this.after(parse(dateString));
    }

    public boolean beforeToday(){
        GregorianCalendar calendarToday = new GregorianCalendar();
        return this.before(calendarToday);
    }

    public boolean afterToday(){
        GregorianCalendar calendarToday = new GregorianCalendar();
        return this.after(calendarToday);
    }


    //================

    public HerbuyCalendar() {
    }

    public HerbuyCalendar(long timeInMillis){
        setTimeInMillis(timeInMillis);
    }

    public HerbuyCalendar(int year, int month, int day) {
        super(year, month, day);
    }

    public HerbuyCalendar(int year, int month, int day, int hour, int minute) {
        super(year, month, day, hour, minute);
    }

    public HerbuyCalendar(int year, int month, int day, int hour, int minute, int second) {
        super(year, month, day, hour, minute, second);
    }

    public HerbuyCalendar(Locale locale) {
        super(locale);
    }

    public HerbuyCalendar(TimeZone timezone) {
        super(timezone);
    }

    public HerbuyCalendar(TimeZone timezone, Locale locale) {
        super(timezone, locale);
    }

    public Integer getHourOfTheDay() {
        return get(HOUR_OF_DAY);
    }

    public String getHourOnTwelveHourClock() {
        Integer hour = get(HOUR_OF_DAY);
        if(hour < 12){
            return  hour + "AM";
        }
        else if(hour > 12){
            return (hour - 12) + "PM";
        }
        else{
            return hour + "PM";
        }
    }

    public String getDayStage() {
        Integer hour = get(HOUR_OF_DAY);
        if(hour >= 4 && hour <= 7){
            return  hour + "Early Morning";
        }
        else if(hour >= 8 && hour <= 11){
            return "Morning";
        }
        else if(hour >= 12 && hour <= 15){
            return "Afternoon";
        }
        else if(hour >= 16 && hour <= 19){
            return "Evening";
        }
        else if(hour >= 20 && hour <= 23){
            return "Night";
        }
        else if(hour >= 0 && hour <= 3){
            return "After Midnight";
        }
        else{
            return "Unknown";
        }
    }

    public Integer getMinute() {
        return get(MINUTE);
    }

    public Integer getSecond() {
        return get(SECOND);
    }

    HerbuyCalendar getLastDayOfMonth() {
        int currentYear = this.get(YEAR);
        int currentMonth = this.get(MONTH);
        int lastDay = this.getActualMaximum(DAY_OF_MONTH);
        return new HerbuyCalendar(currentYear,currentMonth,lastDay);
    }

    HerbuyCalendar getFirstDayOfMonth() {
        int currentYear = this.get(YEAR);
        int currentMonth = this.get(MONTH);
        int firstDay = this.getActualMinimum(DAY_OF_MONTH);
        return new HerbuyCalendar(currentYear,currentMonth,firstDay);
    }

    public boolean isFirstDayOfTheMonth(){
        return isSameDateAs(getFirstDayOfMonth());
    }

    public boolean isLastDayOfTheMonth(){
        return isSameDateAs(getLastDayOfMonth());
    }

    public boolean isSameDateAs(HerbuyCalendar firstDayOfMonth) {
        return get(YEAR) == firstDayOfMonth.get(YEAR)
                && get(MONTH) == firstDayOfMonth.get(MONTH)
                && get(DAY_OF_MONTH) == firstDayOfMonth.get(DAY_OF_MONTH)
                ;
    }

    public boolean isThisMonth() {
        HerbuyCalendar today = new HerbuyCalendar();
        return getYear() == today.getYear() && getMonth() == today.getMonth();
    }
    public boolean isLastMonth() {
        HerbuyCalendar lastDayOfLastMonth = new HerbuyCalendar().getFirstDayOfMonth().previousDay();
        return getYear() == lastDayOfLastMonth.getYear() && getMonth() == lastDayOfLastMonth.getMonth();
    }
    public boolean isNextMonth() {
        HerbuyCalendar firstDayOfNextMonth = new HerbuyCalendar().getLastDayOfMonth().nextDay();
        return getYear() == firstDayOfNextMonth.getYear() && getMonth() == firstDayOfNextMonth.getMonth();
    }

    public boolean isMonday() {
        return getDayOfWeek() == MONDAY;
    }

    public boolean isTuesday() {
        return getDayOfWeek() == TUESDAY;
    }

    public boolean isWednesday() {
        return getDayOfWeek() == WEDNESDAY;
    }

    public boolean isThursday() {
        return getDayOfWeek() == THURSDAY;
    }

    public boolean isFriday() {
        return getDayOfWeek() == FRIDAY;
    }

    public boolean isSaturday() {
        return getDayOfWeek() == SATURDAY;
    }

    public boolean isSunday() {
        return getDayOfWeek() == SUNDAY;
    }

    public String getTimeTwelveHour() {
        int hour = get(HOUR);
        hour = hour == 0 ? 12 : hour;

        return String.format(
                "%02d:%02d%s",
                hour,getMinute(), get(AM_PM) == AM ? " AM" : " PM"
        );

    }

    public String getTimeTwentyFourHour() {

        return String.format(
                "%02d:%02d",
                getHourOfTheDay(),getMinute()
        );

    }

    public String getTimeAgo() {
        long unixTimeMillis = getTimeInMillis();
        long curTimeMillis = System.currentTimeMillis();
        long diffInMillis = unixTimeMillis - curTimeMillis;

        //the sign of the value tells us whether it is ago [past] or from now [future]
        String agoKeyword = diffInMillis <= 0 ? "ago" : "from now";

        //now lets compute the human readable magnitude of the difference
        diffInMillis = Math.abs(diffInMillis);
        String units = "";
        Long value;

        //if less than a minute, we count in seconds
        if (diffInMillis < 60000) {
            value = diffInMillis / 1000;
            units = value == 1 ? "sec" : "secs";
        }

        //if less than an hour, we count in minutes
        else if (diffInMillis < 3600000) {
            value = diffInMillis / 60000;
            units = value == 1 ? "min" : "mins";
        }
        //if less than a day, we count in hours
        else if (diffInMillis < 86400000) {
            value = diffInMillis / 3600000;
            units = value == 1 ? "hr" : "hrs";
        }
        //if less than a week, we count in days
        else if (diffInMillis < 604800000) {

            value = diffInMillis / 86400000;
            units = value == 1 ? "day" : "days";
        }
        //if less than a month, we count in weeks
        else if (diffInMillis < 2592000000L) {

            value = diffInMillis / 604800000;
            units = value == 1 ? "wk" : "wks";
        }
        //if less than a year, we count in months
        else if (diffInMillis < 31536000000L) {

            value = diffInMillis / 2592000000L;
            units = value == 1 ? "mth" : "mths";
        } else {

            value = diffInMillis / 31536000000L;
            units = value == 1 ? "yr" : "yrs";
        }

        return String.format("%d %s %s", value, units, agoKeyword);

    }

    public HerbuyCalendar addHours(int number){
        add(HOUR,number);
        return this;
    }

}

