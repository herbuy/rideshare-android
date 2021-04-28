package libraries.android;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class MakeACircle {

    public static ShapeDrawable where (int width, int height, int color) {
        ShapeDrawable oval = new ShapeDrawable (new OvalShape());
        oval.setIntrinsicHeight (height);
        oval.setIntrinsicWidth (width);
        oval.getPaint ().setColor (color);
        return oval;
    }
}
