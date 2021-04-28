package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPicker;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPickerForApplication;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;
import layers.render.Atom;
import libraries.android.DisableInterceptingTouchEvents;
import libraries.android.MakeDummy;
import libraries.android.MinOfScreenWidthAndHeight;
import libraries.underscore.Singleton;
import resources.ItemColor;

public class TimeInputDialog extends TripInputDialog {
    public TimeInputDialog(Context context, TripDataInputListener inputListener) {
        super(context, inputListener);
    }

    Singleton<View> finalView = new Singleton<View>() {
        @Override
        protected View onCreate() {
            return makeWrapperWithScrollableBody("Your Departure time",body());
        }
    };

    private View body() {

        ItemPicker<Hour> picker = new ItemPickerForApplication<Hour>(context) {

            GridLayout gridLayout;
            @Override
            protected ViewGroup getContainer(Context context) {
                gridLayout = new GridLayout(context);
                gridLayout.setColumnCount(4);
                gridLayout.setPadding(8,8,8,8);

                return gridLayout;
            }

            @Override
            public ItemRenderer<Hour> getItemRenderer() {
                return new ItemRenderer<Hour>() {
                    @Override
                    public View render(Hour item, boolean isSelected) {

                        int size = MinOfScreenWidthAndHeight.oneFifth(context);
                        final TextView view = Atom.textView(context,item.getFriendlyHour());
                        view.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                        view.setLayoutParams(new ViewGroup.LayoutParams(size,size));
                        if(isSelected){
                            view.setBackgroundColor(ItemColor.primary() );
                            view.setTextColor(Color.WHITE);
                            view.setTypeface(null, Typeface.BOLD);
                        }
                        return view;
                    }
                };
            }

            @Override
            protected void onItemSelected(SelectableItem<Hour> sender, Hour newlySelectedItem, List<Hour> allSelectedItems) {
                inputListener.onTimeChanged(newlySelectedItem);
            }

            @Override
            protected void onItemUnselected(SelectableItem<Hour> item, Hour unselectedItem, List<Hour> allSelectedItems) {
                inputListener.onTimeChanged(null);
            }

            @Override
            public String getDisplayText(Hour value) {
                return value.getFriendlyHour();
            }
        };

        picker.setData(getData());

        ScrollView scrollView = MakeDummy.scrollView(picker.getView());
        DisableInterceptingTouchEvents.where(scrollView);
        return scrollView;
    }


    public View getView() {
        return finalView.instance();
    }

    protected List<Hour> getData() {
        return Arrays.asList(
                new Hour(6),
                new Hour(7),
                new Hour(8),
                new Hour(9),
                new Hour(10),
                new Hour(11),
                new Hour(12),
                new Hour(13),
                new Hour(14),
                new Hour(15),
                new Hour(16),
                new Hour(17),
                new Hour(18),
                new Hour(19),
                new Hour(20),
                new Hour(21),
                new Hour(22),
                new Hour(23),
                new Hour(0),
                new Hour(1),
                new Hour(2),
                new Hour(3),
                new Hour(4),
                new Hour(5)
        );
    }

    public static class Hour {
        private int hour;

        public Hour(int hour) {
            this.hour = hour;
        }

        public String getFriendlyHour(){
            if(hour == 0){
                return "12am";
            }
            else if(hour < 12){
                return hour + "am";
            }
            else if(hour == 12){
                return "12pm";
            }
            else{
                return (hour - 12) + "pm";
            }

        }

        public String toTime() {
            return String.format("%d:00", hour);
        }

        public int get() {
            return hour;
        }
    }
}
