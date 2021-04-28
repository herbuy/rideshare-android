package libraries.android;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

public class LabelledEditText3 extends LinearLayout {

    private TextView label;
    private EditText editText;


    public void setText(String text) {
        editText.setText(text);
    }


    private void init() {
        label = new TextView(getContext());
        label.setLayoutParams(matchParentWrapContent());
        label.setY(8);


        editText = new EditText(getContext());
        editText.setLayoutParams(matchParentWrapContent());
        //editText.setY(-8);
        editText.setPadding(0,0,0,16);

        this.addView(label);
        this.addView(editText);
        this.setOrientation(VERTICAL);

        this.setBackgroundColor(Color.parseColor("#ffffcc"));
        this.setPadding(32,8,32,8);

    }

    private ViewGroup.LayoutParams matchParentWrapContent() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public TextView getLabel() {
        return label;
    }

    public EditText getEditText() {
        return editText;
    }

    //==================

    public LabelledEditText3(Context context) {
        super(context);
        init();
    }

    public LabelledEditText3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LabelledEditText3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LabelledEditText3(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public LabelledEditText3 setHint(String hint) {
        getLabel().setText(hint);
        getEditText().setHint(hint);
        return this;
    }
}
