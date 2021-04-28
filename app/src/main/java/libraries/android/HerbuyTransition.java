package libraries.android;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

public abstract class HerbuyTransition<T extends Number> extends Transition {
    @Override
    public final void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public final void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put(getPropertyName(), onGetValue(transitionValues.view));
    }



    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {

        T startValue = null;
        T endValue = null;
        if(startValues != null){
            startValue = (T) startValues.values.get(getPropertyName());
        }
        if(endValues != null){
            endValue = (T) endValues.values.get(getPropertyName());
        }


        if (!startValue.equals(endValue) && endValues != null) {
            onSetValue(endValues.view, startValue);
            ValueAnimator animator = createValueAnimator(startValue, endValue);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    onSetValue(endValues.view, (T) animation.getAnimatedValue());
                }
            });
            return animator;

        }
        return null;

    }

    protected abstract ValueAnimator createValueAnimator(T startValue, T endValue);
    protected abstract void onSetValue(View view, T newValue);
    protected abstract String getPropertyName();
    protected abstract T onGetValue(View view);
}
