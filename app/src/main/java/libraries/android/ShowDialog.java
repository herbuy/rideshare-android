package libraries.android;

import android.app.Dialog;
import android.view.View;

public class ShowDialog {
    public static Dialog run(View content){
        Dialog dialog = new Dialog(content.getContext());
        dialog.setContentView(content);
        dialog.show();
        return dialog;
    }
}
