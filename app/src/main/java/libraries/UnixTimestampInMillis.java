
package libraries;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class UnixTimestampInMillis {
    private UnixTimestampInMillis() {
    }


    public static String toDateTime(long unixTimestampInMillis){
        return toDateTime(unixTimestampInMillis, "yyyy-MM-dd HH:mm:ss");
    }

    public static String toDate(long unixTimestampInMillis){
        return toDateTime(unixTimestampInMillis, "yyyy-MM-dd");
    }
    public static String toYear(long unixTimestampInMillis){
        return toDateTime(unixTimestampInMillis, "yyyy");
    }
    public static String toMonth(long unixTimestampInMillis){
        return toDateTime(unixTimestampInMillis, "MM");
    }
    public static String toDay(long unixTimestampInMillis){
        return toDateTime(unixTimestampInMillis, "dd");
    }
    public static String toTime(long unixTimestampInMillis){
        return toDateTime(unixTimestampInMillis, "HH:mm:ss");
    }
    public static String toHour(long unixTimestampInMillis){
        return toDateTime(unixTimestampInMillis, "HH");
    }
    public static String toMinute(long unixTimestampInMillis){
        return toDateTime(unixTimestampInMillis, "mm");
    }
    public static String toSecond(long unixTimestampInMillis){
        return toDateTime(unixTimestampInMillis, "ss");
    }
    public static String toTimeTwelveHour(long unixTimestampInMillis){

        int hour = Integer.valueOf(toHour(unixTimestampInMillis));
        String minuteString = toMinute(unixTimestampInMillis);
        int minute = Integer.valueOf(minuteString);
        String suffix = " AM";
        String delimiter = ":";
        if(hour >= 12){
            suffix = " PM";
            if(hour > 12){
                hour = hour - 12;
            }
        }
        if(minute == 0){
            minuteString = "";
            delimiter = "";
        }
        if(hour == 0){
            hour = 12;
        }

        return String.format("%d%s%s%s", hour, delimiter, minuteString, suffix);
    }

    public static String toDateTime(long unixTimestampInMillis, String formatString) {
        Date date = new Date(unixTimestampInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatString, Locale.UK);
        return dateFormat.format(date);
    }
    public static long fromDateTime(String dateString, String dateFormat){
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat,Locale.UK);
            return simpleDateFormat.parse(dateString).getTime();
        }
        catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    public static long fromHourAndMinute(String hourAndMinute){
        return fromDateTime(hourAndMinute,"HH:mm","HH:m","H:mm","H:m");
    }



    public static long fromDateTime(String dateString, String... dateFormatsToTry){
        String error = "";
        for(String dateFormat: dateFormatsToTry){
            try{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat,Locale.UK);
                return simpleDateFormat.parse(dateString).getTime();
            }
            catch(Exception ex){
                error = ex.getMessage();
                continue;
            }
        }
        throw new RuntimeException(error);

    }


    //later, we shall have a dictionary to look up all 124 possible formats to try
    private static long fromDateTime(String dateString){
        String[] supportedYearPatterns = new String[]{"yyyy"};
        String[] supportedMonthPatterns = new String[]{"MM","M",""};
        String[] supportedDayPatterns = new String[]{"dd","d",""};
        String[] supportedHourPatterns = new String[]{"HH","H",""};
        String[] supportedMinutePatterns = new String[]{"mm","m",""};
        String[] supportedSecondPatterns = new String[]{"ss","s",""};
        String[] supportedDateDelimiters = new String[]{"-","/"};

        Set<String> patterns = new HashSet<>();
        for(String year : supportedYearPatterns){
            for(String month: supportedMonthPatterns){
                if(month.equalsIgnoreCase("")){
                    continue;
                }
                for(String dateDelimiter: supportedDateDelimiters){
                    patterns.add(year+dateDelimiter+month);
                    for(String day: supportedDayPatterns){
                        if(day.equalsIgnoreCase("")){
                            continue;
                        }
                        patterns.add(year+dateDelimiter+month+dateDelimiter+day);
                        for(String hour: supportedHourPatterns){
                            if(hour.equalsIgnoreCase("")){
                                continue;
                            }
                            patterns.add(year+dateDelimiter+month+dateDelimiter+day+" "+hour);
                            for(String minute: supportedMinutePatterns){
                                if(minute.equalsIgnoreCase("")){
                                    continue;
                                }
                                patterns.add(year+dateDelimiter+month+dateDelimiter+day+" "+hour+":"+minute);
                                for(String second: supportedSecondPatterns){
                                    if(second.equalsIgnoreCase("")){
                                        continue;
                                    }
                                    patterns.add(year+dateDelimiter+month+dateDelimiter+day+" "+hour+":"+minute+":"+second);
                                }
                            }

                        }
                    }
                }



            }
        }

        try{
            return fromDateTime(dateString,"yyyy-MM-dd HH:mm:ss");
        }
        catch(Exception ex){
            return fromDateTime(dateString,"yyyy-MM-d");
        }

    }

    public static String toTimeAgo(long unixTimestampInMillis) {
        long currentTimeStamp = System.currentTimeMillis();
        String agoKeyword = "ago";
        long absoluteDifference = Math.abs(unixTimestampInMillis - currentTimeStamp);


        float years = absoluteDifference / 1000 / 60 / 60 / 24 / 365;
        float months = absoluteDifference / 1000 / 60 / 60 / 24 / 30;
        float weeks = absoluteDifference / 1000 / 60 / 60 / 24 / 7;
        float days = absoluteDifference / 1000 / 60 / 60 / 24;
        float hours = absoluteDifference / 1000 / 60 / 60;
        float minutes = absoluteDifference / 1000 / 60;
        float seconds = absoluteDifference / 1000;

        String finalValue = "";
        if(years >= 1){
            finalValue = years + " years";
        }
        else if(months >=1){
            finalValue = months + " months";
        }
        else if(weeks >=1){
            finalValue = weeks + " weeks";
        }
        else if(days >=1){
            finalValue = days + " days";
        }
        else if(hours >=1){
            finalValue = hours + " hours";
        }
        else if(minutes >=1){
            finalValue = minutes + " minutes";
        }
        else{
            finalValue = seconds + " seconds";
        }

        if(unixTimestampInMillis == currentTimeStamp){
            return "Right now";
        }
        if(unixTimestampInMillis < currentTimeStamp){
            agoKeyword = " ago";
        }
        else{
            agoKeyword = " from now"; //e.g 5 minutes from now
        }
        return finalValue + agoKeyword;
    }

    public static long fromDate(String dateString) {
        return fromDateTime(dateString,"yyyy-MM-dd","yyyy-M-d","yyyy-M-dd","yyyy-MM-d");
    }

    public static long fromTime(String timeString) {
        try{
            return fromDateTime(timeString,"HH:mm","H:m","HH:m","H:mm");
        }
        catch(Exception exception){
            try{
                return fromDateTime(timeString,"HH:mm:ss","HH:mm:s","HH:m:ss","H:mm:ss","HH:m:s","H:mm:s","H:m:ss","H:m:s");
            }
            catch (Exception ex2){
                return fromDateTime(timeString,"HH","H");
            }
        }

    }
}

