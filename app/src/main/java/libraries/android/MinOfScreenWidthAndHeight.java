package libraries.android;

import android.content.Context;

/**
 * returns the smaller of the width and height of the screen
 */
public class MinOfScreenWidthAndHeight {

    public static int get(Context context) {
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        int result = Math.min(screenWidth, height);
        return result;
    }

    public static int oneHalf(Context context) {
        return get(context) / 2;
    }

    public static int oneThird(Context context) {
        return get(context) / 3;
    }

    public static int oneQuarter(Context context) {
        return get(context) / 4;
    }

    public static int oneFifth(Context context) {
        return get(context) / 5;
    }

    public static int oneSixth(Context context) {
        return get(context) / 6;
    }

    public static int oneSeventh(Context context) {
        return get(context) / 7;
    }

    public static int oneEighth(Context context) {
        return get(context) / 8;
    }

    public static int oneNineth(Context context) {
        return get(context) / 9;
    }

    public static int oneTenth(Context context) {
        return get(context) / 10;
    }

    public static int twoThirds(Context context) {
        return get(context) * 2 / 3;
    }
}
