package libraries.android;

import android.content.Context;

public class HerbuyDevice {
    public static void setContext(Context context) {
        HerbuyDevice.context = context;
    }

    private static Context context;

    private static String LANDSCAPE = "LANDSCAPE";
    private static String PORTRAIT = "PORTRAIT";

    public static String getOrientation(){
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        return width > height ? LANDSCAPE : PORTRAIT;

    }
    public static boolean isLandscape(){
        return getOrientation().equalsIgnoreCase(LANDSCAPE);
    }
    public static boolean isPortrait(){
        return getOrientation().equalsIgnoreCase(PORTRAIT);
    }
    public static int getSmallerOfWidthOrHeight(){
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        return Math.min(width,height);
    }
}
