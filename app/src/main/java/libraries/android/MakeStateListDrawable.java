package libraries.android;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;

public class MakeStateListDrawable {

    public static StateListDrawable where(int defaultColor,int colorWhenSelected, int alphaPercent) {
        StateListDrawable res = new StateListDrawable();
        res.setExitFadeDuration(400);
        if(alphaPercent < 100){
            res.setAlpha(alphaPercent);
        }


        res.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(colorWhenSelected));
        res.addState(new int[]{}, new ColorDrawable(defaultColor));
        return res;
    }
    public static StateListDrawable where(int defaultColor,int colorWhenSelected){
        return where(defaultColor,colorWhenSelected,100);
    }
}
