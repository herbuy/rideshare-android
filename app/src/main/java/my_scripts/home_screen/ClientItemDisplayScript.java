package my_scripts.home_screen;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import core.businessobjects.Client;
import libraries.android.MakeDummy;
import resources.Dp;

public class ClientItemDisplayScript {
    public static View run(Context context, Client itemDetails) {
        View item = MakeDummy.linearLayoutVertical(
                context,

                nameDisplayScript(context, itemDetails),
                amountDisplayScript(context, itemDetails),
                telephoneDisplayScript(context, itemDetails)
                );

        item.setPadding(Dp.scaleBy(1),Dp.scaleBy(1),Dp.scaleBy(1),Dp.scaleBy(1));
        return item;
    }

    private static View telephoneDisplayScript(Context context, Client itemDetails) {
        TextView name = MakeDummy.textView(context,itemDetails.getName());
        name.setTextSize(Dp.scaleBy(0.8f));
        return name;
    }

    private static View amountDisplayScript(Context context, Client itemDetails) {
        return MakeDummy.textView(context,itemDetails.getAmount());
    }

    private static View nameDisplayScript(Context context, Client itemDetails) {
        TextView name = MakeDummy.textView(context,itemDetails.getName());
        name.setTextSize(Dp.scaleBy(1.2f));
        return name;
    }


}
