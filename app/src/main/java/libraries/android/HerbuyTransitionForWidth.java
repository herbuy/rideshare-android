package libraries.android;

import android.view.View;

public class HerbuyTransitionForWidth extends HerbuyTransitionForInt {

    @Override
    protected void onSetValue(View view, Integer newValue) {
        view.getLayoutParams().width = newValue;
        view.requestLayout();
    }

    @Override
    protected String getPropertyName(){
        return "widthTransition:width";
    }

    @Override
    protected Integer onGetValue(View view) {
        return view.getWidth();
    }
}
