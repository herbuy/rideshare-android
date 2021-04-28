package test;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import libraries.android.MessageBox;

public abstract class Pager {

    Scroller scrollView;

    public Pager(Context context) {
        this.context = context;
        initScroller();
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                loadNewViews();
            }
        });
    }

    private boolean scrollToPreviousEnabled = false;
    private boolean scrollToNextEnabled = false;

    private void initScroller() {
        scrollView = new Scroller(context){
            int startX;
            @Override
            protected void onScrollStart(Scroller sender, int x, int y) {
                super.onScrollStart(sender, x, y);
                startX = x;
            }

            private OnTouchListener defaultOnTouchListener;
            @Override
            protected void alreadyScrolling(Scroller sender, int x, int y) {
                super.alreadyScrolling(sender, x, y);

                sender.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                int dx = x - startX;
                if(wantsToScrollToNext(dx)){
                    if(scrollToNextEnabled){
                        currentIndex += 1;
                        sender.setNotifyScrollChangeEnabled(false);
                        beforeScrollToNext();
                        loadNewViews();
                        scrollToCurrentPage(sender);
                    }
                    else{
                        scrollToNextFailed();
                    }
                }
                else if (wantsToScrollToPrevious(dx)){
                    if(scrollToPreviousEnabled){
                        currentIndex -= 1;
                        sender.setNotifyScrollChangeEnabled(false);
                        beforeScrollToPrevious();
                        loadNewViews();
                        scrollToCurrentPage(sender);
                    }
                    else{
                        scrollToPreviousFailed();
                    }
                }

            }

            private boolean wantsToScrollToPrevious(int dx) {
                return dx < 0;
            }

            private boolean wantsToScrollToNext(int dx) {
                return dx > 0;
            }

            boolean alreadyScrolling = false;
            synchronized private void scrollToCurrentPage(final Scroller sender) {
                if(alreadyScrolling){
                    return;
                }
                alreadyScrolling = true;
                sender.setNotifyScrollChangeEnabled(false);

                ValueAnimator valueAnimator = ValueAnimator.ofInt(
                        sender.getScrollX(),
                        currentIndex * sender.getWidth()
                );
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int newValue = (int)valueAnimator.getAnimatedValue();
                        sender.setScrollX(newValue);
                    }

                });
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        alreadyScrolling = false;
                        sender.setNotifyScrollChangeEnabled(true);
                        sender.setOnTouchListener(null);


                    }


                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                valueAnimator.start();
                /*setSmoothScrollingEnabled(true);
                sender.setScrollX(currentIndex * sender.getWidth());*/
            }

            @Override
            protected void onScrollStopped(Scroller sender, int x, int y) {



            }
        };
        scrollView.addView(tableLayout());
    }

    protected abstract void beforeScrollToPrevious();

    protected abstract void beforeScrollToNext();

    protected void scrollToPreviousFailed() {

    }

    protected void scrollToNextFailed() {

    }

    public void loadNewViews() {
        View newPrevious = onGetPreviousItem();
        scrollToPreviousEnabled = newPrevious != null && newPrevious.getVisibility() == View.VISIBLE;

        View newCurrentItem = onGetCurrentItem();

        View newNextItem = onGetNextItem();
        scrollToNextEnabled = newNextItem != null && newNextItem.getVisibility() == View.VISIBLE;

        //we dont want to throw error if null value passed
        newPrevious = newPrevious == null ? dummyView() : newPrevious;
        newNextItem = newNextItem == null ? dummyView() : newNextItem;
        newCurrentItem = newCurrentItem == null ? dummyView() : newCurrentItem;

        Pager.this.addView(newPrevious,newCurrentItem,newNextItem);
        scrollView.setScrollX(scrollView.getWidth());
    }

    private View dummyView() {
        TextView view = new TextView(context);
        view.setVisibility(View.GONE);
        return view;
    }


    protected abstract View onGetCurrentItem() ;
    protected abstract View onGetNextItem();
    protected abstract View onGetPreviousItem();

    private Context context;

    int currentIndex = 0;
    public View getView() {
        return scrollView;


    }


    private View tableLayout() {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.addView(tableRow());
        return tableLayout;
    }

    TableRow tableRow;
    private View tableRow() {
        tableRow = new TableRow(context);
        return tableRow;
    }

    private Pager addView(View... viewList){
        tableRow.removeAllViews();
        for(final View child: viewList){

            tableRow.addView(child);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    if(child.getLayoutParams() != null){
                        child.getLayoutParams().width = scrollView.getWidth();
                        child.getLayoutParams().height = scrollView.getHeight();
                        child.setLayoutParams(child.getLayoutParams());

                    }
                }
            });

        }
        return this;
    }

    /** the scroller is used to scroll horizontally */
    class Scroller extends HorizontalScrollView{
        public Scroller(Context context) {
            super(context);
        }

        long timestampOfLastScrollNotification = -1;
        boolean scrolling = false;

        private boolean notifyScrollChangeEnabled = true;

        synchronized public void setNotifyScrollChangeEnabled(boolean notifyScrollChangeEnabled) {
            this.notifyScrollChangeEnabled = notifyScrollChangeEnabled;
        }

        @Override
        synchronized protected void onScrollChanged(int newX, int newY, int oldX, int oldY) {
            super.onScrollChanged(newX, newY, oldX, oldY);
            if(!notifyScrollChangeEnabled){
                return;
            }
            if(scrolling){
                updateTimestampOfLastScrollNotification();
                postDelayed(checkTimestampLastScrollNotification(newX, newY),100);
                alreadyScrolling(this,newX, newY);
            }
            else{
                scrolling = true;
                onScrollStart(this,newX, newY);
            }

        }

        protected void alreadyScrolling(Scroller scroller, int x, int y) {

        }

        protected void onScrollStart(Scroller sender, int x, int y) {

        }

        private Runnable checkTimestampLastScrollNotification(final int x, final int y) {
            return new Runnable() {
                @Override
                public void run() {
                    if(millisSinceLastScrollNotification() > 40){

                        onScrollStopped(Scroller.this,x, y);
                        resetTimestampOfLastScrollNotification();
                    }
                }
            };
        }

        protected void onScrollStopped(Scroller scroller, int x, int y) {

        }

        public void resetTimestampOfLastScrollNotification() {
            timestampOfLastScrollNotification = -1;
            scrolling = false;
        }

        private long millisSinceLastScrollNotification() {
            return System.currentTimeMillis() - timestampOfLastScrollNotification;
        }

        private void updateTimestampOfLastScrollNotification() {
            timestampOfLastScrollNotification = System.currentTimeMillis();
        }
    }

}
