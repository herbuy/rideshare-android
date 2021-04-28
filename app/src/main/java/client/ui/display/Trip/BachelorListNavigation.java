package client.ui.display.Trip;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import client.ui.libraries.HerbuyView;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.ColorCalc;
import resources.Dp;
import resources.ItemColor;

public abstract class BachelorListNavigation implements HerbuyView {
    private final Context context;
    private LinearLayout view;

    public BachelorListNavigation(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        ImageButton btnNext = new ImageButton(context);
        btnNext.setImageResource(R.drawable.bmp_media_next);
        btnNext.setBackgroundColor(ColorCalc.setAlpha(0.1f, ItemColor.primary()));
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowNext();
            }
        });


        ImageButton btnPrev = new ImageButton(context);
        btnPrev.setImageResource(R.drawable.bmp_media_prev);
        btnPrev.setBackgroundColor(ColorCalc.setAlpha(0.1f, ItemColor.primary()));
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPrevious();

            }
        });

        TextView spacer = Atom.textView(context, ".");
        spacer.setVisibility(View.INVISIBLE);

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemParams.weight = 1;

        btnNext.setLayoutParams(itemParams);
        btnPrev.setLayoutParams(itemParams);
        spacer.setLayoutParams(itemParams);

        LinearLayout wrapper = MakeDummy.linearLayoutHorizontal(context, btnPrev, spacer, btnNext);
        wrapper.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());
        wrapper.setBackgroundResource(R.drawable.footer_background);


        this.view = wrapper;
    }

    protected abstract void onShowPrevious();

    protected abstract void onShowNext();

    @Override
    public View getView() {
        return view;
    }

}
