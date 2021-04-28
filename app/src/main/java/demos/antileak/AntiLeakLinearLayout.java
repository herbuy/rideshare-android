package demos.antileak;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public abstract class AntiLeakLinearLayout extends LinearLayout {
    public AntiLeakLinearLayout(Context context) {
        super(context);
    }

    public AntiLeakLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AntiLeakLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AntiLeakLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
