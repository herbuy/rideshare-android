package libraries.android;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class HideKeyboard {
    //current activity
    public static void run(Activity context) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);

    }

    //current view with keyboard focus
    public static void run(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void delayed(final View view) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                HideKeyboard.run(view);
            }
        },50);
    }
}
