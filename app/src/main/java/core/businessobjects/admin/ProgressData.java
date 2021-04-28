package core.businessobjects.admin;


public class ProgressData {
    private String indicatorId = "";
    private String indicatorDescription = "";
    private int overall;
    private int today; //select where date is today
    private int thisMonth; //select where month is this month
    private int yesterday;
    private int lastMonth;
    private int perDay;
    private int perMonth;
    private long timestampFirst;//in milliseconds since epoch
    private long timestampMostRecent;//in millis


    //==============================================
    //day stage sumary
    private int perUser;
    private int btnMidnightAnd3am;
    private int btn4to7am;
    private int btn8to11am;
    private int btn12to3pm;
    private int btn4to7pm;
    private int btn8to1pm;
    //week day summary
    private int onAMonday;
    private int onATuesday;
    private int onAWednesday;
    private int onAThurday;
    private int onAFriday;
    private int onASaturday;
    private int onASunday;
    private int inJanuary;
    private int inFebruary;
    private int inMarch;
    private int inApril;
    private int inMay;
    private int inJune;
    private int inJuly;
    private int inAugust;
    private int inSeptember;
    private int inOctober;
    private int inNovember;
    private int inDecember;
    private int janToMarch;
    private int aprToJun;
    private int julToSept;
    private int octToDec;


    //hourly summary

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getIndicatorDescription() {
        return indicatorDescription;
    }

    public void setIndicatorDescription(String indicatorDescription) {
        this.indicatorDescription = indicatorDescription;
    }

    public int getOverall() {
        return overall;
    }

    public void setOverall(int overall) {
        this.overall = overall;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public int getThisMonth() {
        return thisMonth;
    }

    public void setThisMonth(int thisMonth) {
        this.thisMonth = thisMonth;
    }

    public int getYesterday() {
        return yesterday;
    }

    public void setYesterday(int yesterday) {
        this.yesterday = yesterday;
    }

    public int getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(int lastMonth) {
        this.lastMonth = lastMonth;
    }

    public int getPerDay() {
        return perDay;
    }

    public void setPerDay(int perDay) {
        this.perDay = perDay;
    }

    public int getPerMonth() {
        return perMonth;
    }

    public void setPerMonth(int perMonth) {
        this.perMonth = perMonth;
    }

    public void setTimestampFirst(long timestampFirst) {
        this.timestampFirst = timestampFirst;
    }

    public long getTimestampFirst() {
        return timestampFirst;
    }

    public void setTimestampMostRecent(long timestampMostRecent) {
        this.timestampMostRecent = timestampMostRecent;
    }

    public long getTimestampMostRecent() {
        return timestampMostRecent;
    }
}
