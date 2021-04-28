package libraries.android;

import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class DisableInterceptingTouchEvents {
    public static void where(FrameLayout frameLayout){
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }
}
