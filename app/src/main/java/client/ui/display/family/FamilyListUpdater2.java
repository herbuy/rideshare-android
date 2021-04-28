package client.ui.display.family;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.SquaredImageVIew;
import libraries.android.MinOfScreenWidthAndHeight;
import resources.Dp;

//could even pass in the family just, so that we add the logic for updating the family
//updating a family list is a single responsibility even if it could be initiated by so many events and status changes
public abstract class FamilyListUpdater2 extends FamilyListUpdater {
    public FamilyListUpdater2(Context context) {
        super(context);
    }

    @Override
    public void renderEmpty() {
        ImageView image = new SquaredImageVIew(context);
        image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        image.setImageResource(R.drawable.img_trip_tab_empty);
        image.setAlpha(0.5f);
        image.setLayoutParams(new ViewGroup.LayoutParams(
                MinOfScreenWidthAndHeight.oneThird(context),
                MinOfScreenWidthAndHeight.oneThird(context)
        ));

        TextView text = Atom.textView(context, "<b>No Content</b><br/> Travellers you matched with will appear here.");
        MakeDummy.wrapContent(text);
        text.setLayoutParams(new ViewGroup.LayoutParams(
                MinOfScreenWidthAndHeight.twoThirds(context),
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        text.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                context,
                MakeDummy.lineSeparator(context,MinOfScreenWidthAndHeight.oneSixth(context)),
                text,
                MakeDummy.lineSeparator(context, Dp.normal()),
                image,
                MakeDummy.lineSeparator(context,MinOfScreenWidthAndHeight.oneSixth(context))
        );

        wrapper.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        wrapper.setBackgroundResource(R.drawable.header_background);

        FrameLayout finalWrapper = new FrameLayout(context);
        finalWrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        finalWrapper.addView(wrapper);
        finalWrapper.setPadding(Dp.normal(),Dp.normal(),Dp.normal(),Dp.normal());

        view.render(finalWrapper);
    }


}
