package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_fuel_contribution;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPickerForApplication;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;
import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.shared.DialogActivity;
import layers.render.Atom;
import resources.Dp;
import resources.TransitionName;

public class ChangeFuelContributionActivity extends DialogActivity {
    @Override
    protected String getTransitionName() {
        return TransitionName.fuelContribution;
    }

    @Override
    protected String getHeaderText() {
        return "How much will you charge?";
    }

    @Override
    protected View getActivityContent() {
        ItemPickerForApplication<Integer> picker = new ItemPickerForApplication<Integer>(this) {
            @Override
            protected void onItemSelected(SelectableItem<Integer> sender, Integer newlySelectedValue, List<Integer> allSelectedItems) {
                EventBusForTaskUploadTrip.fuelContributionChanged.notifyObservers(newlySelectedValue);
                getView().setTransitionName("");
                sender.getView().setTransitionName(TransitionName.fuelContribution);
                onBackPressed(sender.getView());
            }

            @Override
            protected void onItemUnselected(SelectableItem<Integer> sender, Integer unselectedItem, List<Integer> allSelectedItems) {

            }

            @Override
            public String getDisplayText(Integer value) {
                return value == 0 ?
                        "To be<br/><small>discussed</small>" :
                        String.format
                                (Locale.ENGLISH,
                                        "<b>%d</b><br/><small><font color='#555555'>Ushs</font></small>",
                                        value
                                );
            }

            @Override
            protected ViewGroup getContainer(final Context context) {
                final GridLayout layout = new GridLayout(context);
                layout.setColumnCount(3);
                //layout.setBackgroundColor(Color.parseColor("#ffffcc"));
                layout.setUseDefaultMargins(true);
                layout.post(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i< layout.getChildCount(); i++){
                            View child = layout.getChildAt(i);
                            child.getLayoutParams().width = (int)(layout.getWidth() / 3.2f);
                            child.getLayoutParams().height = layout.getWidth() / 5;
                            child.setElevation(10);

                            child.requestLayout();
                            child.setPadding(child.getPaddingLeft(), Dp.normal(),child.getPaddingRight(),child.getPaddingBottom());
                            //child.setLayoutParams(params);



                        }
                    }
                });
                return layout;
            }

            @Override
            public ItemRenderer<Integer> getItemRenderer() {
                return new ItemRenderer<Integer>() {
                    @Override
                    public View render(Integer item, boolean isSelected) {
                        TextView view = Atom.textViewPrimaryNormal(getContext(),getDisplayText(item));
                        view.setGravity(Gravity.CENTER_HORIZONTAL);
                        return view;
                    }
                };
            }
        };
        picker.setData(getData());
        return picker.getView();
    }

    private Context getContext(){
        return this;
    }

    protected List<Integer> getData() {
        return Arrays.asList(
                0,
                5000,
                10000,
                15000,
                20000,
                25000,
                30000,
                35000,
                40000,
                45000,
                50000,
                55000,
                60000
        );
    }
}