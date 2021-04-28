package my_scripts.home_screen;

import android.content.Context;
import android.graphics.Color;

import android.view.View;
import android.widget.Button;

import com.skyvolt.jabber.R;

import libraries.android.MakeDummy;

public class ButtonDisplayScript {
    public static Button run(Context context, String text, View.OnClickListener onClickListener){
        Button button = MakeDummy.button(context, text);
        button.setBackgroundResource(R.drawable.button);
        button.setTextColor(Color.WHITE);
        button.setOnClickListener(onClickListener);
        return button;
    }

}
