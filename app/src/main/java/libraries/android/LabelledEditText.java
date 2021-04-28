package libraries.android;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LabelledEditText {
    Context context;
    LinearLayout linearLayout;
    public final TextView label;
    public final EditText editText;
    private TextChangedListener textChangedListener;


    public LabelledEditText(Context context, String hint) {
        this.context = context;
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        label = new TextView(context);
        label.setTextColor(Color.parseColor("#242424"));
        label.setTypeface(null, Typeface.BOLD);
        label.setVisibility(View.GONE);
        linearLayout.addView(label);

        editText = new EditText(context);
        linearLayout.addView(editText);
        editText.setY(-16);

        setHint(hint);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                TransitionManager.beginDelayedTransition(linearLayout);
                if (text.trim().equalsIgnoreCase("")) {
                    label.setVisibility(View.GONE);
                } else {
                    label.setVisibility(View.VISIBLE);
                }

                if (textChangedListener != null) {
                    textChangedListener.afterTextChanged(s);
                }
            }
        });


    }

    public View getView() {
        return linearLayout;
    }

    public void setText(String text) {
        editText.setText(text);
    }

    public void setHint(String hint) {
        label.setText(hint);
        editText.setHint(hint);
    }

    public void setTextChangedListener(TextChangedListener textChangedListener) {
        this.textChangedListener = textChangedListener;
    }

    public void setInputType(int inputType) {
        editText.setInputType(inputType);
    }
}
