package libraries.android;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import androidx.annotation.NonNull;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;


public class MakeDummy {

    public static View addRule(View view, int rule, int anchor) {
        try {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
            layoutParams.addRule(rule,anchor);
            view.setLayoutParams(layoutParams);
        }
        catch (Exception ex) {
            relativeLayout(view);
            addRule(view, rule);
        }
        return view;
    }

    public static View addRule(View view, int rule) {
        try {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
            layoutParams.addRule(rule);
            view.setLayoutParams(layoutParams);
        }
        catch (Exception ex) {
            relativeLayout(view);
            addRule(view, rule);
        }
        return view;
    }

    @NonNull
    private static View relativeLayout(View view) {
        view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }

    public static View alignAbove(View aboveWhat, View whoAbove) {
        if (aboveWhat.getId() == -1) {
            aboveWhat.setId(Math.abs(new Random().nextInt()));
        }
        addRule(whoAbove, RelativeLayout.ABOVE, aboveWhat.getId());
        return whoAbove;
    }

    public static View alignBelow(View aboveWhat, View whoAbove) {
        if (aboveWhat.getId() == -1) {
            aboveWhat.setId(Math.abs(new Random().nextInt()));
        }
        addRule(whoAbove, RelativeLayout.BELOW, aboveWhat.getId());
        return whoAbove;
    }

    public static View alignRightOf(View aboveWhat, View whoAbove) {
        if (aboveWhat.getId() == -1) {
            aboveWhat.setId(Math.abs(new Random().nextInt()));
        }
        addRule(whoAbove, RelativeLayout.RIGHT_OF, aboveWhat.getId());
        return whoAbove;
    }

    public static View alignLeftOf(View aboveWhat, View whoAbove) {
        if (aboveWhat.getId() == -1) {
            aboveWhat.setId(Math.abs(new Random().nextInt()));
        }
        addRule(whoAbove, RelativeLayout.LEFT_OF, aboveWhat.getId());
        return whoAbove;
    }

    public static View matchParentHeight(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(layoutParams);
        return view;
    }

    public static View alignParentTop(View view) {
        return addRule(view, RelativeLayout.ALIGN_PARENT_TOP);
    }
    public static View alignParentBottom(View view) {
        return addRule(view, RelativeLayout.ALIGN_PARENT_BOTTOM);
    }
    public static View alignParentLeft(View view) {
        return addRule(view, RelativeLayout.ALIGN_PARENT_LEFT);
    }
    public static View alignParentRight(View view) {
        return addRule(view, RelativeLayout.ALIGN_PARENT_RIGHT);
    }

    public static View stretchAcrossParentBottom(View view){
        alignParentBottom(view);
        alignParentRight(view);
        alignParentLeft(view);
        return view;
    }
    public static View stretchAcrossParentTop(View view){
        alignParentTop(view);
        alignParentRight(view);
        alignParentLeft(view);
        return view;
    }
    public static View stretchAcrossParentRight(View view){
        alignParentTop(view);
        alignParentRight(view);
        alignParentBottom(view);
        return view;
    }
    public static View stretchAcrossParentLeft(View view){
        alignParentTop(view);
        alignParentLeft(view);
        alignParentBottom(view);
        return view;
    }
    public static View stretchVerticallyMiddle(View view){
        alignParentTop(view);
        alignParentBottom(view);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        return view;
    }

    public static View stretchHorizontallyMiddle(View view){
        alignParentLeft(view);
        alignParentRight(view);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        return view;
    }


    public static View alpha(View view, float alpha) {
        view.setAlpha(alpha);
        return view;
    }

    public static TextView textColor(TextView view, int color) {
        view.setTextColor(color);
        return view;
    }

    public static TextView textSize(TextView view, float size) {
        view.setTextSize(size);
        return view;
    }

    public static View backgroundResource(View view, int resourceId) {
        view.setBackgroundResource(resourceId);
        return view;
    }

    public static View padding(Context context, View child, int padding) {
        LinearLayout layout = linearLayoutVertical(context);
        layout.addView(child);
        layout.setPadding(padding, padding, padding, padding);
        return layout;
    }

    public static View padding(View child, int padding) {
        child.setPadding(padding, padding, padding, padding);
        return child;
    }

    public static View paddingTop(View view, int padding) {
        view.setPadding(view.getPaddingLeft(), padding, view.getPaddingRight(), view.getPaddingBottom());
        return view;
    }

    public static View paddingBottom(View view, int padding) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), padding);
        return view;
    }

    public static View paddingLeft(View view, int padding) {
        view.setPadding(padding, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        return view;
    }

    public static View paddingRight(View view, int padding) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), padding, view.getPaddingBottom());
        return view;
    }

    public static Button button(Context context, CharSequence text) {
        Button btn = new Button(context);
        btn.setText(text);
        return btn;
    }

    public static Button button(Context context, String text, View.OnClickListener onClickListener) {
        Button btn = button(context, text);
        btn.setOnClickListener(onClickListener);
        return btn;
    }

    public static View lineSeparator(Context context, int height, int color) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        textView.setBackgroundColor(color);
        return textView;
    }
    public static View lineSeparator(Context context, int height){
        return lineSeparator(context,height,Color.TRANSPARENT);

    }
    public static View verticalSeparator(Context context, int width, int color) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(width, width));
        textView.setBackgroundColor(color);
        return textView;
    }
    public static View verticalSeparator(Context context, int width){
        return verticalSeparator(context,width, Color.TRANSPARENT);
    }

    public static LinearLayout linearLayoutVertical(Context context, final View... children) {

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        for(View child: children){
            layout.addView(child);
        }

        return layout;
    }

    public static LinearLayout linearLayoutHorizontal(Context context, View... children) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        for(View child: children){
            layout.addView(child);
        }
        return layout;
    }

    public static TextView textView(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setText(Html.fromHtml(text));
        return textView;
    }



    public static View backgroundColor(View view, int color) {
        view.setBackgroundColor(color);
        return view;
    }

    public static LinearLayout centerHorizontal(LinearLayout child) {
        child.setGravity(Gravity.CENTER_HORIZONTAL);
        return child;
    }

    public static TextView centerHorizontal(TextView child) {
        child.setGravity(Gravity.CENTER_HORIZONTAL);
        return child;
    }

    public static LinearLayout centerHorizontal(ImageView child) {

        return centerHorizontal(linearLayoutVertical(child.getContext(), child));
    }


    public static ImageView imageView(Context context, int width, int height, int resourceId) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(resourceId);
        return imageView;
    }
    public static ImageView imageView(Context context, int width, int height, int resourceId, float alpha){
        ImageView imageView = imageView(context,width,height,resourceId);
        imageView.setAlpha(alpha);
        return imageView;
    }

    public static Handler handler() {
        return new Handler();
    }

    public static ViewGroup setContent(ViewGroup container, View content) {
        container.removeAllViews();
        container.addView(content);
        return container ;
    }

    public static View layoutParams(View view, int width, int height) {
        if(view.getLayoutParams() == null){
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width,height);
            view.setLayoutParams(layoutParams);
        }
        else{
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = width;
            params.height = height;
            view.requestLayout();
        }

        return view;
    }

    public static View setWeight(View view, float weight) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                view.getLayoutParams().width,
                view.getLayoutParams().height
        );

        try{
            layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();

        }
        catch (Exception ex){

        }
        layoutParams.weight = weight;
        view.setLayoutParams(layoutParams);
        return view;
    }

    public static View wrapContent(View view) {

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(layoutParams == null){
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
        }
        else{
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            view.setLayoutParams(layoutParams);
        }
        return view;
    }

    public static TextView typefaceNormal(TextView view) {
        view.setTypeface(null, Typeface.NORMAL);
        return view;
    }

    public static ScrollView scrollView(View view) {
        ScrollView scrollView = new ScrollView(view.getContext());
        scrollView.addView(view);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return scrollView;
    }

    public static View invisible(View view) {
        view.setVisibility(View.INVISIBLE);
        return view;
    }

    public static View gone(View view) {
        view.setVisibility(View.GONE);
        return view;
    }

    public static TableLayout table(Context context, TableRow... tableRows) {
        TableLayout tableLayout = new TableLayout(context);
        for(TableRow row: tableRows){
            tableLayout.addView(row);
        }
        return tableLayout;
    }

    public static TableRow row(Context context, View... views) {
        TableRow tableRow = new TableRow(context);
        for(View view: views){
            tableRow.addView(view);
        }
        return tableRow;
    }
}
