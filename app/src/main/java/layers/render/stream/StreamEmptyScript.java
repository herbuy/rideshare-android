package layers.render.stream;

import android.content.Context;
import android.view.View;

import libraries.android.MakeDummy;

public class StreamEmptyScript {
    public static View run(Context context) {
        return MakeDummy.textView(context, "No Items In Stream");
    }
}
