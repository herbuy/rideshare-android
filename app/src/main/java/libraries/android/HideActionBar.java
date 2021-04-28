package libraries.android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class HideActionBar {
    //coding the result
    public static final int RESULT_NULL_ACTIVITY = 1;
    public static final int RESULT_NULL_ACTION_BAR = 2;
    public static final int RESULT_SUCCESS = 3;

    public static int run(AppCompatActivity activity){
        if(activity == null){
            return RESULT_NULL_ACTIVITY;
        }

        ActionBar actionBar = activity.getSupportActionBar();
        if(actionBar == null){
            return RESULT_NULL_ACTION_BAR;
        }

        actionBar.hide();
        return RESULT_SUCCESS;

    }
}
