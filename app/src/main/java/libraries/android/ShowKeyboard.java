package libraries.android;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class ShowKeyboard {
    public static void where(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    public static void where(final View editText) {
        editText.post(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) editText.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }
    public static void where(AlertDialog dialog){
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
