package libraries.android;


import android.view.ActionMode;
import android.view.View;
import android.view.ActionMode.Callback;

public class StartActionMode {
    public static ActionMode run(View sender, Callback callback){
        return sender.startActionMode(callback);
    }
}
