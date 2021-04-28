package libraries.android;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HerbuyConservativeView {

    public static View create(final Context context, int backgroundColor, int padding, final View... children){
        final LinearLayout container = new LinearLayout(context);
        container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.setBackgroundColor(backgroundColor);
        container.setGravity(Gravity.CENTER);
        container.setPadding(padding,padding,padding,padding);

        container.post(new Runnable() {
            @Override
            public void run() {

                int layoutSize = Math.min(container.getWidth(), container.getHeight());
                LinearLayout innerContainer = new LinearLayout(context);
                innerContainer.setOrientation(LinearLayout.VERTICAL);
                container.addView(innerContainer);
                innerContainer.setLayoutParams(new LinearLayout.LayoutParams(
                        layoutSize, layoutSize
                ));

                innerContainer.setBackgroundColor(Color.WHITE);
                innerContainer.setPadding(16,16,16,16);
                for(View child: children){
                    innerContainer.addView(child);
                }



            }
        });

        return container;
    }
}
