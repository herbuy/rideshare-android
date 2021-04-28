package libraries.android;

import android.view.View;

public class HerbuyTransitionForAlpha extends HerbuyTransitionForFloat {

    @Override
    protected void onSetValue(View view, Float newValue) {
        view.setAlpha(newValue);
    }

    @Override
    protected String getPropertyName(){
        return "alpha_transition:alpha";
    }

    @Override
    protected Float onGetValue(View view) {
        return view.getAlpha();
    }
}
