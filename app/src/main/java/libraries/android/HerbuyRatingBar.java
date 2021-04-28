package libraries.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RatingBar;

public class HerbuyRatingBar extends RatingBar{
    public HerbuyRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HerbuyRatingBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public HerbuyRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HerbuyRatingBar(Context context) {
        super(context,null,android.R.attr.ratingBarStyleIndicator);
        init();
    }



    private void init() {
        setMax(5);
        setNumStars(5);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setEnabled(true);
        setIsIndicator(false);
    }
}
