package libraries.android;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import client.ui.display.Trip.TripListActivity;

public class CustomTabLayout {
    CustomTab[] customTabs;
    TabLayout tabLayout;

    public CustomTabLayout(final TabLayout tabLayout, float textSize, int textColor, int counterTextColor) {
        this.tabLayout = tabLayout;

        customTabs = new CustomTab[tabLayout.getTabCount()];
        for (int currentIndex = 0; currentIndex < tabLayout.getTabCount(); currentIndex++) {
            final int tabIndex = currentIndex;
            customTabs[tabIndex] = new CustomTab(tabLayout.getContext());
            customTabs[tabIndex].setText(tabLayout.getTabAt(tabIndex).getText().toString());
            customTabs[tabIndex].setTextSize(textSize);
            customTabs[tabIndex].setTextColor(textColor);
            customTabs[tabIndex].setCount(0);
            customTabs[tabIndex].setCounterTextColor(counterTextColor);


            customTabs[tabIndex].getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tabLayout.getTabAt(tabIndex).select();
                }
            });

            tabLayout.getTabAt(tabIndex).setCustomView(customTabs[tabIndex].getView());
        }

    }

    public void setCount(int tabIndex, int value) {
        try{
            customTabs[tabIndex].setCount(value);
        }
        catch (Exception ex){
            Log.e(getClass().getName(),ex.getMessage());
        }

    }

    private class CustomTab {
        private Context context;
        private TextView titleView;
        private TextView counterView;
        private LinearLayout container;

        public CustomTab(Context context) {
            this.context = context;
            container = new LinearLayout(context);
            container.setOrientation(LinearLayout.HORIZONTAL);
            container.setGravity(Gravity.CENTER_HORIZONTAL);
            container.addView(titleView());
            container.addView(counterView());
        }

        private View counterView() {
            counterView = new TextView(context);
            counterView.setBackground(circle());
            counterView.setGravity(Gravity.CENTER_HORIZONTAL);
            counterView.setElevation(10);
            return counterView;
        }

        ShapeDrawable oval;
        private Drawable circle() {
            oval = new ShapeDrawable (new OvalShape());
            oval.setIntrinsicHeight (24);
            oval.setIntrinsicWidth (24);
            oval.getPaint ().setColor (Color.WHITE);
            return oval;
        }

        public void setCounterBackgroundColor(int color){
            oval.getPaint ().setColor (color);
        }

        private View titleView() {
            titleView = new TextView(context);
            titleView.setGravity(Gravity.CENTER_HORIZONTAL);
            titleView.setTypeface(null, Typeface.BOLD);
            titleView.setPadding(0,0,8,0);
            return titleView;
        }

        public View getView() {
            return container;
        }

        public void setCount(int value) {
            TransitionManager.beginDelayedTransition(container);
            counterView.setText(String.valueOf(value));
            if(value < 1){
                counterView.setVisibility(View.GONE);
            }
            else{
                counterView.setVisibility(View.VISIBLE);
            }
        }

        public void setCounterTextColor(int counterTextColor){
            counterView.setTextColor(counterTextColor);
        }

        public void setText(String title) {
            titleView.setText(title);
        }

        public void setTextSize(float textSize) {
            titleView.setTextSize(textSize);
            counterView.setTextSize(textSize);
        }

        public void setTextColor(int color) {
            titleView.setTextColor(color);
        }
    }

    public View getView(){
        return tabLayout;
    }

}
