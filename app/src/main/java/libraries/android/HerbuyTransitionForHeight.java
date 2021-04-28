package libraries.android;

import android.view.View;

public class HerbuyTransitionForHeight extends HerbuyTransitionForInt {

    @Override
    protected void onSetValue(View view, Integer newValue) {
        view.getLayoutParams().height = newValue;
        view.requestLayout();
    }

    @Override
    protected String getPropertyName(){
        return "heightTransition:height";
    }

    @Override
    protected Integer onGetValue(View view) {
        return view.getHeight();
    }
}
