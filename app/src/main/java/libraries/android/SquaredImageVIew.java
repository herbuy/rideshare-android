package libraries.android;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquaredImageVIew extends ImageView {
    public SquaredImageVIew(Context context) {
        super(context);
    }

    public SquaredImageVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquaredImageVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquaredImageVIew(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width,width);
    }
}
