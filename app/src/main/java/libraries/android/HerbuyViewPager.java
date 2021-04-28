package libraries.android;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HerbuyViewPager extends ViewPager{
    public HerbuyViewPager(Context context) {
        super(context);
    }

    public HerbuyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private boolean pagingEnabled = true;

    public boolean isPagingEnabled() {
        return pagingEnabled;
    }

    public void setPagingEnabled(boolean pagingEnabled) {
        this.pagingEnabled = pagingEnabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isPagingEnabled() && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isPagingEnabled() && super.onInterceptTouchEvent(ev);
    }
}
