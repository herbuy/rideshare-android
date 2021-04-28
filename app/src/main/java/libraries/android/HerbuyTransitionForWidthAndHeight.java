package libraries.android;

import android.content.Context;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.View;

public class HerbuyTransitionForWidthAndHeight extends TransitionSet {

    public HerbuyTransitionForWidthAndHeight() {
        init();
    }

    public HerbuyTransitionForWidthAndHeight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        addTransition(new HerbuyTransitionForWidth());
        addTransition(new HerbuyTransitionForHeight());

    }
    public static void setNewValues(int width, int height, View view){
        view.getLayoutParams().width = width;
        view.getLayoutParams().height = height;
        view.requestLayout();

    }
}
