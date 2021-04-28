package test;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PagerForViews {
    class MyHorizontalScrollView extends HorizontalScrollView{
        public MyHorizontalScrollView(Context context) {
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

        protected void alreadyScrolling(MyHorizontalScrollView myHorizontalScrollView, int x, int y) {

        }

        protected void onScrollStart(MyHorizontalScrollView sender, int x, int y) {

        }

        private Runnable checkTimestampLastScrollNotification(final int x, final int y) {
            return new Runnable() {
                @Override
                public void run() {
                    if(millisSinceLastScrollNotification() > 40){

                        onScrollStopped(MyHorizontalScrollView.this,x, y);
                        resetTimestampOfLastScrollNotification();
                    }
                }
            };
        }

        protected void onScrollStopped(MyHorizontalScrollView myHorizontalScrollView, int x, int y) {

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

    public PagerForViews(Context context) {
        this.context = context;
    }

    private Context context;

    int currentIndex = 0;
    public View getView() {


        final MyHorizontalScrollView scrollView = new MyHorizontalScrollView(context){
            int startX;
            @Override
            protected void onScrollStart(MyHorizontalScrollView sender, int x, int y) {
                super.onScrollStart(sender, x, y);
                startX = x;
            }

            private OnTouchListener defaultOnTouchListener;
            @Override
            protected void alreadyScrolling(MyHorizontalScrollView sender, int x, int y) {
                super.alreadyScrolling(sender, x, y);

                sender.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                int dx = x - startX;
                Log.e("DX",String.valueOf(dx));
                //MessageBox.show("scrolling",context);
                if(dx > 0){
                    currentIndex += 1;
                    sender.setNotifyScrollChangeEnabled(false);
                    scrollToCurrentPage(sender);
                }
                else if (dx < 0){
                    currentIndex -= 1;
                    sender.setNotifyScrollChangeEnabled(false);
                    scrollToCurrentPage(sender);
                }

            }

            boolean alreadyScrolling = false;
            synchronized private void scrollToCurrentPage(final MyHorizontalScrollView sender) {
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
            protected void onScrollStopped(MyHorizontalScrollView sender, int x, int y) {

            }
        };
        scrollView.addView(tableLayout());
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                resizeChildren(scrollView);
            }
        });

        return scrollView;


    }



    List<View> children = new ArrayList<>();
    private void resizeChildren(HorizontalScrollView scrollView) {
        for(View child: children){
            if(child.getLayoutParams() != null){
                child.getLayoutParams().width = scrollView.getWidth();
                child.getLayoutParams().height = scrollView.getHeight();
                child.setLayoutParams(child.getLayoutParams());

            }
        }
    }

    private View tableLayout() {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.addView(tableRow());
        return tableLayout;
    }

    private View tableRow() {
        TableRow tableRow = new TableRow(context);
        for(int i = 0; i < 5; i++){
            tableRow.addView(page(i));
        }
        return tableRow;
    }

    private View page(int index) {
        TextView page = new TextView(context);
        page.setText("ITEM "+index);
        page.setPadding(24,24,24,24);
        int[] possibleColors = new int[]{Color.GRAY,Color.GREEN,Color.BLUE,Color.RED};
        int selectedIndex = Math.abs(new Random().nextInt()) % possibleColors.length;
        page.setBackgroundColor(possibleColors[selectedIndex]);
        children.add(page);
        return page;
    }

}
