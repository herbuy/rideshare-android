package client.ui.display.family;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.skyvolt.jabber.R;

import client.ui.libraries.HerbuyView;
import core.businessobjects.Family;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;

public class ArrivalConfirmationDialog implements HerbuyView {
    private final Context context;
    private final Family family;

    public ArrivalConfirmationDialog(Context context, Family family) {
        this.context = context;
        this.family = family;
    }

    @Override
    public View getView() {
        View wrapper = createView();

        wrapper.setBackgroundColor(Color.parseColor("#dddddd"));
        wrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return MakeDummy.scrollView(wrapper);
    }

    private View createView() {
        if(family == null){
            return Atom.centeredText(context, "Group trip not specified");
        }

        View title = Atom.textViewPrimaryNormal(
                context,
                String.format(
                        "You are reporting that you <b>arrived in %s from %s</b>",
                        family.getDestinationName(),family.getOriginName()

                )
        );
        title.setBackgroundResource(R.drawable.header_background);
        title.setPadding(Dp.one_point_5_em(), Dp.one_point_5_em(), Dp.one_point_5_em(), Dp.one_point_5_em());

        View details = Atom.textViewPrimaryNormal(context,"<ol><li>This will allow you <b>share with us</b> how your journey went</li><li>You will also be able to <b>rate the trip</b>, the car, and the people you travelled with for the benefit of other travellers</li></ol>");
        details.setBackgroundResource(R.drawable.header_background);
        details.setPadding(Dp.one_point_5_em(), Dp.one_point_5_em(), Dp.one_point_5_em(), Dp.one_point_5_em());

        LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                context,
                title,
                vspace(),
                details,
                confirmButton()
        );
        return wrapper;
    }

    private LinearLayout confirmButton() {
        LinearLayout wrapper = MakeDummy.linearLayoutVertical(context, new ConfirmArrivedButton(context, family).getView());
        wrapper.setBackgroundResource(R.drawable.header_background);
        wrapper.setPadding(Dp.one_point_5_em(), Dp.one_point_5_em(), Dp.one_point_5_em(), Dp.one_point_5_em());
        return wrapper;
    }

    private View vspace() {
        return MakeDummy.invisible(Atom.textView(context, "h"));
    }
}
