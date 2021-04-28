package resources;

import android.content.Context;

public class Dp {


    private static Context context;

    public static void setContext(Context context) {
        Dp.context = context;
    }

    private static float defaultTextSizeDp = 17;
    public static int scaleBy(float scaleFactor) {
        return (int)((defaultTextSizeDp * scaleFactor ));

    }

    //====================


    public static int normal(){
        return scaleBy(1f);

    }

    public static int half_em(){

        return scaleBy(0.5f);
    }




    public static int one_point_5_em() {
        return scaleBy(1.5f);
        //return (int)(normal() * 1.5f);
    }

    public static int two_em() {
        return scaleBy(2f);
        //return (int)(normal() * 2f);
    }

    public static int ui_container() {
        return two_em();
    }

    public static int four_em() {
        return scaleBy(4);
    }

    public static int eight_em() {
        return scaleBy(8);
    }
}
