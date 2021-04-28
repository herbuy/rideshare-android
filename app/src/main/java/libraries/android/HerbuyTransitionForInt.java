package libraries.android;

import android.animation.ValueAnimator;

public abstract class HerbuyTransitionForInt extends HerbuyTransition<Integer>{


    @Override
    protected final ValueAnimator createValueAnimator(Integer startValue, Integer endValue) {
        return ValueAnimator.ofInt(startValue,endValue);
    }
}
