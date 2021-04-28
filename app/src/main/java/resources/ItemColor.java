package resources;

import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;

import com.skyvolt.jabber.R;

import libraries.android.ColorCalc;

public class ItemColor {
    static Context context;

    public static void setContext(Context context) {
        ItemColor.context = context;
    }

    public static int primary() {
        return getColor(R.color.colorPrimary);
    }


    public static int error() {
        return Color.parseColor("#FF4400");
    }

    public static int success() {
        return Color.parseColor("#E5F9ED");
    }

    private static int getColor(int resourceId) {
        return ContextCompat.getColor(context, resourceId);
    }

    public static int textPrimary() {
        return ColorCalc.multiplySaturationBy(
                0.2f,
                ColorCalc.multiplyBrightnessBy(
                        0.2f,
                        primary()
                )
        ); //return Color.parseColor("#141414");
    }

    public static int textSecondary(){
        return ColorCalc.mixColors(textPrimary(),Color.WHITE,0.3f);
    }

    public static int chatBg() {
        return ColorCalc.multiplyBrightnessBy(0.9f, Color.parseColor("#EBE4DB"));
    }

    public static int bgColor() {

        return Color.parseColor("#EFE7DE");

    }

    public static int outboundMessage() {

        return Color.parseColor("#E1FFC7");

    }

    public static int highlight() {
        return ColorCalc.mixColors(ItemColor.primary(),Color.TRANSPARENT,0.9f);
    }
}


