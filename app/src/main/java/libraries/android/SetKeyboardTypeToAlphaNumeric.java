package libraries.android;

import android.text.InputType;
import android.widget.EditText;

public class SetKeyboardTypeToAlphaNumeric {
    public static void where(EditText editText){
        editText.setInputType(
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        );
    }
}
