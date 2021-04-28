package libraries.android;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;

public class AnimateBackgroundColorThenBack {
    public static void where(final int startColor, final int endColor, final View targetView, final Runnable onEnd){

        animateBackgroundColor(startColor, endColor, targetView, new Runnable() {
            @Override
            public void run() {
                animateBackgroundColor(endColor,startColor,targetView,onEnd);
            }
        });
    }

    private static void animateBackgroundColor(int startColor, int endColor, final View targetView, final Runnable onEnd) {
        ValueAnimator animator = ValueAnimator.ofInt(
                startColor,
                endColor
        );
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int newColor = (int)valueAnimator.getAnimatedValue();
                targetView.setBackgroundColor(newColor);

            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(onEnd != null){
                    onEnd.run();
                }


            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

}
