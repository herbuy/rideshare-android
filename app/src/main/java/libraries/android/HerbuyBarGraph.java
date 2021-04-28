package libraries.android;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class HerbuyBarGraph {
    final Map<String, Integer> dataset = new HashMap<>();
    private int paddingLeft = 8;
    private int paddingTop = 8;
    private int paddingRight = 8;
    private int paddingBottom = 8;
    private int barColor = Color.GRAY;
    private int barBackgroundColor = Color.TRANSPARENT;
    private Integer labelColor;
    private Integer labelSize;
    private Integer valueColor;
    private Integer valueSize;
    private Integer barHeight;
    Integer verticalSpacing = 16;


    public HerbuyBarGraph addBar(String key, Integer value) {
        dataset.put(key, value);
        return this;
    }


    public View getView(Context context) {

        if (dataset.size() < 1) {
            TextView textView = new TextView(context);
            textView.setText("No data to display");
            textView.setPadding(32, 32, 32, 32);
            return textView;
        }

        int maxValue = getMaxValue(dataset);


        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setColumnStretchable(2, true);
        tableLayout.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingLeft());


        for (final String key : dataset.keySet()) {

            TextView label = label(key, context);
            TextView value = value(dataset.get(key), context);

            TableRow row = newRow(context);
            tableLayout.addView(row);
            row.addView(label);
            row.addView(value);
            row.addView(right(context, dataset.get(key), maxValue));

            row.setGravity(Gravity.CENTER_VERTICAL);
            if(onConditionalFormat != null){
                onConditionalFormat.format(this,label, value, key, dataset.get(key));
            }



        }

        return tableLayout;
    }

    public interface OnConditionalFormat{
        void format(HerbuyBarGraph sender,TextView labelView, TextView valueView, String label, Integer value);
    }
    private OnConditionalFormat onConditionalFormat;

    public HerbuyBarGraph setOnConditionalFormat(OnConditionalFormat onConditionalFormat) {
        this.onConditionalFormat = onConditionalFormat;
        return this;
    }

    public Integer getMaxValue(){
        return getMaxValue(dataset);
    }

    private Integer getMaxValue(Map<String, Integer> dataset) {
        Integer[] sortedArray = new Integer[dataset.size()];
        Arrays.sort(dataset.values().toArray(sortedArray), new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                if (lhs > rhs) {
                    return -1;
                } else if (lhs < rhs) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return sortedArray[0];
    }

    private TextView label(String text, Context context) {
        TextView label = new TextView(context);
        label.setText(Html.fromHtml(text));
        label.setPadding(0, 0, 16, 0);
        if(labelColor != null){
            label.setTextColor(labelColor);
        }
        if(labelSize != null){
            label.setTextSize(labelSize);
        }
        return label;

    }

    private TextView value(Integer value, Context context) {
        TextView label = new TextView(context);
        label.setText(String.valueOf(value));
        label.setPadding(0, 0, 16, 0);

        if(valueColor != null){
            label.setTextColor(valueColor);
        }
        if(valueSize != null){
            label.setTextSize(valueSize);
        }
        return label;
    }

    private View right(Context context, final Integer value, final Integer maxValue) {
        final TextView bar = newBar(context);
        final LinearLayout barContainer = newBarContainer(context);
        final FrameLayout finalContainer = new FrameLayout(context);
        finalContainer.addView(barContainer);
        barContainer.addView(bar);

        finalContainer.post(new Runnable() {
            @Override
            public void run() {
                if(maxValue > 0){
                    bar.getLayoutParams().width = (finalContainer.getWidth() * value / maxValue);

                    if (barHeight != null) {
                        bar.getLayoutParams().height = barHeight;
                    }

                    bar.requestLayout();
                }


            }
        });

        return finalContainer;

    }

    private LinearLayout newBarContainer(Context context) {
        final LinearLayout barContainer = new LinearLayout(context);
        barContainer.setBackgroundColor(barBackgroundColor);
        barContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return barContainer;
    }

    private TextView newBar(Context context) {
        final TextView bar = new TextView(context);
        bar.setText("");
        bar.setTextColor(Color.TRANSPARENT);
        bar.setBackgroundColor(barColor);
        bar.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return bar;
    }


    private TableRow newRow(Context context) {
        TableRow row = new TableRow(context);
        row.setPadding(0, verticalSpacing /2, 0, verticalSpacing /2);
        return row;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public HerbuyBarGraph setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public HerbuyBarGraph setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public HerbuyBarGraph setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        return this;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public HerbuyBarGraph setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        return this;
    }

    public HerbuyBarGraph setPadding(int padding) {
        paddingLeft = padding;
        paddingRight = padding;
        paddingTop = padding;
        paddingBottom = padding;
        return this;
    }

    public HerbuyBarGraph setBarColor(int barColor) {
        this.barColor = barColor;
        return this;
    }

    public int getBarColor() {
        return barColor;
    }

    public HerbuyBarGraph setBarBackgroundColor(int barBackgroundColor) {
        this.barBackgroundColor = barBackgroundColor;
        return this;
    }

    public int getBarBackgroundColor() {
        return barBackgroundColor;
    }

    public HerbuyBarGraph setLabelColor(int labelColor) {
        this.labelColor = labelColor;
        return this;
    }

    public int getLabelColor() {
        return labelColor;
    }

    public HerbuyBarGraph setLabelSize(int labelSize) {
        this.labelSize = labelSize;
        return this;
    }

    public int getLabelSize() {
        return labelSize;
    }

    public Integer getValueColor() {
        return valueColor;
    }

    public HerbuyBarGraph setValueColor(Integer valueColor) {
        this.valueColor = valueColor;
        return this;
    }

    public Integer getValueSize() {
        return valueSize;
    }

    public HerbuyBarGraph setValueSize(Integer valueSize) {
        this.valueSize = valueSize;
        return this;
    }

    public HerbuyBarGraph setBarHeight(int barHeight) {
        this.barHeight = barHeight;
        return this;
    }

    public int getBarHeight() {
        return barHeight;
    }

    public Integer getVerticalSpacing() {
        return verticalSpacing;
    }

    public HerbuyBarGraph setVerticalSpacing(Integer verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
        return this;
    }
}
