package libraries.android;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.ScaleAnimation;

public class ViewUtils {
    public static int getRequiredScrollY(View item, View itemList, View scrollView){
        float itemDistanceFromScrollViewTop = itemList.getY() + item.getY();
        if(itemDistanceFromScrollViewTop < 0){
            return (int)itemDistanceFromScrollViewTop;
        }
        if(itemDistanceFromScrollViewTop + item.getHeight() > scrollView.getHeight()){
            return (int)(itemDistanceFromScrollViewTop + item.getHeight() - scrollView.getHeight());
        }
        return 0;

    }

    public static boolean noNeedToScroll(View item, View itemList, int scrollViewWidth, int scrollViewHeight){

        return
                //item left visible in list
                item.getX() >= 0
                        //
                        && (itemList.getX() + itemList.getWidth()) <= scrollViewWidth
                        && itemList.getY() >= 0
                        && (itemList.getY() + itemList.getHeight()) <= scrollViewHeight;
    }

    public static boolean needToScroll(View item, View itemList, int scrollViewWidth, int scrollViewHeight){
        return !noNeedToScroll(item,itemList,scrollViewWidth,scrollViewHeight);

    }


    public static void showListUsingHeightAnimation(final View container){

        container.setVisibility(View.VISIBLE);
        container.post(new Runnable() {
            @Override
            public void run() {
                final int initialHeight = container.getHeight();
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, initialHeight);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        final int newHeight = (int) ((float) animation.getAnimatedValue());
                        container.getLayoutParams().height = newHeight;
                        container.requestLayout();
                    }
                });
                valueAnimator.setDuration(400);
                valueAnimator.start();

            }
        });

    }

    public static void hideUsingHeightAnimation(final View container) {


        container.post(new Runnable() {
            @Override
            public void run() {
                final int initialHeight = container.getHeight();
                ValueAnimator valueAnimator = ValueAnimator.ofInt(initialHeight, 0);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        container.getLayoutParams().height = (int) animation.getAnimatedValue();
                        container.requestLayout();
                    }
                });
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        container.setVisibility(View.GONE);
                        container.getLayoutParams().height = initialHeight;

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                valueAnimator.setDuration(400);
                valueAnimator.start();

            }
        });



    }

    public static void scrollToItem(final View scrollView, View itemView, int duration, int delay){

        /*
        ValueAnimator scrollXAnimator = ValueAnimator.ofFloat(scrollView.getScrollX(), itemView.getX());
        scrollXAnimator.setDuration(duration);
        scrollXAnimator.setStartDelay(delay);

        scrollXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float newValue = (float) animation.getAnimatedValue();
                scrollView.setScrollX((int) newValue);
            }
        });*/

        ValueAnimator scrollYAnimator = ValueAnimator.ofFloat(scrollView.getScrollY(), itemView.getScrollY());
        scrollYAnimator.setDuration(duration);
        scrollYAnimator.setStartDelay(delay);

        scrollYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float newValue = (float) animation.getAnimatedValue();
                Log.d("==== NEW VALUE",String.valueOf(newValue));
                scrollView.setScrollY((int) newValue);
            }
        });

        //scrollXAnimator.start();
        scrollYAnimator.start();

        /*
        final int totalSteps = 12;//fps
        final int length = 400;//millis
        final Handler handler = new Handler();

        if (totalSteps < 1) {
            return;
        }

        int periodBetweenSteps = length / totalSteps;
        final Timer timer = new Timer();

        if (beforeStart != null) {
            beforeStart.run();
        }

        timer.scheduleAtFixedRate(new TimerTask() {

            int currentStep = 0;

            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!exceedsTotalSteps()) {

                            final float fraction = (float) currentStep / (float) totalSteps;
                            //apply



                            //Log.d("STEP NUMBER: ", currentStep+"");
                            currentStep += 1;

                            if (exceedsTotalSteps()) {
                                endAnimation();
                            }

                        }
                    }
                });
            }

            private boolean exceedsTotalSteps() {
                return currentStep > totalSteps;
            }

            private void endAnimation() {
                timer.cancel();

                if (onFinish != null) {
                    onFinish.onFinish(HerbuyAnimation.this);

                }
            }
        }, delay, periodBetweenSteps);*/

    }

    public static void showItemUsingScaleXY(View item, int startDelay){
        ScaleAnimation animation = new ScaleAnimation(0, item.getScaleX(), 0, item.getScaleY(), item.getWidth() / 2, item.getHeight() / 2);
        animation.setDuration(200);
        animation.setStartOffset(startDelay);
        item.startAnimation(animation);
    }

}
