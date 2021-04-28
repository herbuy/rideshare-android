package client.ui;


import android.graphics.Color;

import libraries.android.HerbuyBarGraph;
import resources.Dp;
import resources.ItemColor;

public class AppBarGraph extends HerbuyBarGraph {
    public AppBarGraph() {
        this.setBarColor(ItemColor.primary())
                .setBarBackgroundColor(Color.TRANSPARENT)
                .setLabelColor(Color.parseColor("#242424"))
                .setLabelSize(Dp.scaleBy(0.7f))
                .setValueColor(Color.parseColor("#242424"))
                .setValueSize(Dp.scaleBy(0.7f))
                .setBarHeight(Dp.scaleBy(0.7f))
                .setVerticalSpacing(Dp.scaleBy(0.5f));
    }
}
