package libraries.android;

import android.animation.ValueAnimator;

public abstract class HerbuyTransitionForFloat extends HerbuyTransition<Float>{

    @Override
    protected ValueAnimator createValueAnimator(Float startValue, Float endValue) {
        return ValueAnimator.ofFloat(startValue,endValue);
    }
}
