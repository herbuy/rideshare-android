package my_scripts.home_screen;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import libraries.android.MakeDummy;

public class ListDisplayScript {
    public static LinearLayout run(Context context, View... items) {
        LinearLayout layout = MakeDummy.linearLayoutVertical(context);
        for(View item: items){
            layout.addView(item);
        }
        return layout;
    }
}
