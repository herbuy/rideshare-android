package libraries.android;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

public class GetParentActivity {
    public static Activity fromView(View view){
        return fromContext(view.getContext());
    }

    public static Activity fromContext(Context context) {

        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
}
