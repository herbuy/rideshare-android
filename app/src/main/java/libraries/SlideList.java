package libraries;

import android.animation.ValueAnimator;
import android.content.Context;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.core.widget.NestedScrollView;

public class SlideList {

    private Context context;
    //we use a nested scroll so we can allow other scroll views within.
    NestedScrollView scrollView;

    public SlideList(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        TableLayout tableLayout;
        tableLayout = new TableLayout(context);
        //tableLayout.setLayoutParams(wrapContentMatchParent());
        tableLayout.addView(tableRow(context));

        scrollView = new NestedScrollView(context);

        //scrollView.setHorizontalScrollBarEnabled(false);
        //disableAbilityToScroll();
        scrollView.addView(tableLayout);
    }

    public View getView() {
        return scrollView;
    }

    public void disableAbilityToScroll() {
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }

        });
    }

    TableRow tableRow;

    private View tableRow(Context context) {
        tableRow = new TableRow(context);
        tableRow.setLayoutParams(wrapContentMatchParent());
        return tableRow;
    }

    private ViewGroup.LayoutParams wrapContentMatchParent() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void add(final View item) {

        tableRow.addView(item);

        scrollView.post(new Runnable() {
            @Override
            public void run() {

                item.getLayoutParams().width = containerWidth();
                item.getLayoutParams().height = scrollView.getHeight();
                item.setLayoutParams(item.getLayoutParams());

            }
        });


    }


    public int getCount() {
        return tableRow.getChildCount();
    }

    public int getVisibleSlideCount() {
        int overallSlideCount = getCount();
        int result = 0;
        for(int i = 0; i < overallSlideCount; i++){
            View child = tableRow.getChildAt(i);
            if(child != null && child.getVisibility() == View.VISIBLE){
                result +=1;
            }
        }
        return result;
    }

    public int getCurrentSlideIndex() {
        if(getVisibleSlideCount() < 1){
            return -1;
        }
        return currentScrollX() / containerWidth();
    }

    /**
     * returns an array with two pieces of information. First is current slide number starting from 1, next is total visible slides
     */
    public int[] getProgressInfo() {
        return new int[]{
                getCurrentSlideIndex() + 1,
                getVisibleSlideCount()
        };
    }

    public int containerWidth() {
        return scrollView.getWidth();
    }


    public void show(int index) {
        tableRow.getChildAt(index).setVisibility(View.VISIBLE);
    }

    public void animateHide(int index) {
        TransitionManager.beginDelayedTransition(tableRow);
        tableRow.getChildAt(index).setVisibility(View.GONE);
    }

    public void animateShow(int index) {
        TransitionManager.beginDelayedTransition(tableRow);
        tableRow.getChildAt(index).setVisibility(View.VISIBLE);
    }

    public void jumpToNext() {
        tableRow.setScrollX(scrollXAfterMoveToRight());
    }

    public void jumpToPrevious() {
        scrollView.setScrollX(scrollXAfterMoveToLeft());
    }

    public void animateToNext() {

        ValueAnimator animator = ValueAnimator.ofInt(currentScrollX(), scrollXAfterMoveToRight());
        animator.addUpdateListener(scrollXUpdateListener);
        animator.start();
    }

    private int currentScrollX() {
        return tableRow.getScrollX();
    }

    //============================================================
    private int scrollXAfterMoveToRight() {

        return Math.min(
                maxScrollXValue(),
                currentScrollX() + containerWidth()
        );
    }

    private int maxScrollXValue() {
        return getContentWidth() - containerWidth();
    }

    private int getContentWidth() {
        return getVisibleSlideCount() * containerWidth();
    }

    private int scrollXAfterMoveToLeft() {
        return Math.max(
                mininumScrollXValue(),
                currentScrollX() - containerWidth()
        );
    }

    private int mininumScrollXValue() {
        return 0;
    }

    public void animateToPrevious() {

        ValueAnimator animator = ValueAnimator.ofInt(currentScrollX(), scrollXAfterMoveToLeft());
        animator.addUpdateListener(scrollXUpdateListener);
        animator.start();
    }



    public void jumpToSlide(int index) {
        tableRow.setScrollX(index * containerWidth());
    }

    public void animateToSlide(int index) {
        int targetScrollX = index * containerWidth();

        ValueAnimator animator = ValueAnimator.ofInt(currentScrollX(), targetScrollX);
        animator.addUpdateListener(scrollXUpdateListener);
        animator.start();
    }

    ValueAnimator.AnimatorUpdateListener scrollXUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            int newValue = (int) valueAnimator.getAnimatedValue();
            tableRow.setScrollX(newValue);
        }
    };


    public boolean isOnFirstSlide() {
        return hasVisibleSlides() && getCurrentSlideIndex() == 0;
    }

    public boolean isOnLastSlide() {
        return hasVisibleSlides() && getCurrentSlideIndex() == getVisibleSlideCount() - 1;
    }

    public boolean hasNextSlide() {
        return hasVisibleSlides() && getCurrentSlideIndex() < getVisibleSlideCount() - 1;
    }

    public boolean hasPreviousSlide() {
        return hasVisibleSlides() && getCurrentSlideIndex() > 0;
    }

    public boolean hasVisibleSlides() {
        return getVisibleSlideCount() > 0;
    }

    public boolean hasSlide(int position) {
        return hasVisibleSlides() && position > 0 && position < getVisibleSlideCount();
    }

    public int getScrollX() {
        return scrollView.getScrollX();
    }

    public void setScrollX(int value) {
        this.scrollView.setScrollX(value);
    }
}
