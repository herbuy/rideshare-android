package test;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import libraries.android.HerbuyBarGraph;
import resources.Dp;
import resources.ItemColor;

public class TestHerbuyBarGraph {
    public TestHerbuyBarGraph(Context context) {
        new HerbuyBarGraph()
                .addBar("<b>Joe</b>", 45)
                .addBar("Enock", 12)
                .addBar("Andy", 35)
                .addBar("Okotch", 70)
                .addBar("Mark", 16)
                .setPadding(16)
                .setBarColor(ItemColor.primary())
                .setBarBackgroundColor(Color.TRANSPARENT)
                .setLabelColor(Color.parseColor("#242424"))
                .setLabelSize(Dp.scaleBy(0.7f))
                .setValueColor(Color.parseColor("#242424"))
                .setValueSize(Dp.scaleBy(0.7f))
                .setBarHeight(Dp.scaleBy(0.7f))
                .setVerticalSpacing(Dp.scaleBy(0.5f))
                .setOnConditionalFormat(new HerbuyBarGraph.OnConditionalFormat() {
                    @Override
                    public void format(HerbuyBarGraph sender,TextView labelView, TextView valueView, String label, Integer value) {
                        if(value < sender.getMaxValue() /2){
                            labelView.setTextColor(Color.RED);
                        }
                    }
                })
                .getView(context);
    }
}
