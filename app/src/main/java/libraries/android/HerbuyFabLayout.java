package libraries.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import resources.Dp;

public class HerbuyFabLayout {
    RelativeLayout fabLayout;
    public HerbuyFabLayout(Context context, View content, int fabImageResource, View.OnClickListener onClickFab) {
        fabLayout = new RelativeLayout(context);
        fabLayout.addView(content);
        fabLayout.addView(fab(context,fabImageResource,onClickFab));
    }

    private View fab(final Context context, int fabImageResource, View.OnClickListener onClickListener) {
        FloatingActionButton fab = new FloatingActionButton(context);
        fab.setImageResource(fabImageResource);
        fab.setLayoutParams(fabLayoutParams());
        fab.setOnClickListener(onClickListener);
        return fab;
    }

    private ViewGroup.LayoutParams fabLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.setMargins(Dp.one_point_5_em(),Dp.one_point_5_em(),Dp.one_point_5_em(),Dp.one_point_5_em());
        return params;
    }

    public View getView() {
        return fabLayout;
    }
}
