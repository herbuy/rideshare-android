package my_scripts.home_screen;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import libraries.android.MakeDummy;
import resources.Dp;

public class StatusSeekerScript {
    public static View run(Context context){
        LinearLayout container = MakeDummy.linearLayoutHorizontal(context);

        displayTotalCollected(context, container);
        displayBalance(context, container);

        container.setPadding(Dp.scaleBy(1),Dp.scaleBy(1),Dp.scaleBy(1),Dp.scaleBy(1));
        return container;

    }

    private static void displayBalance(Context context, LinearLayout container) {
        container.addView(
                MakeDummy.setWeight(
                        MakeDummy.layoutParams(
                                MakeDummy.linearLayoutVertical(
                                        context,
                                        MakeDummy.textSize(MakeDummy.textView(context, "Balance"), Dp.scaleBy(1.2f)),
                                        MakeDummy.textView(context, "Ushs. 290,000")
                                )
                                ,
                                0,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ),
                        0.5f
                )
        );
    }

    private static void displayTotalCollected(Context context, ViewGroup container) {
        container.addView(
                MakeDummy.setWeight(
                        MakeDummy.layoutParams(
                                MakeDummy.linearLayoutVertical(
                                        context,
                                        MakeDummy.textSize(MakeDummy.textView(context, "Total Collected"), Dp.scaleBy(1.2f)),
                                        MakeDummy.textView(context, "Ushs. 750,000")
                                )
                                ,
                                0,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ),
                        0.5f
                )
        );
    }
}
