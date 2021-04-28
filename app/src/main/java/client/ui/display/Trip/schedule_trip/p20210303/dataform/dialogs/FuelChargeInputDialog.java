package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPicker;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPickerForApplication;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.underscore.Singleton;

public class FuelChargeInputDialog extends TripInputDialog{

    public FuelChargeInputDialog(Context context, TripDataInputListener inputListener) {
        super(context, inputListener);
    }

    Singleton<View> view = new Singleton<View>() {
        @Override
        protected View onCreate() {
            return makeWrapperWithScrollableBody("Fuel Contribution<br/><small><small>(how much will you charge per seat?)</small></small>",body());
        }
    };

    public View getView() {
        return view.instance();
    }

    private View body() {
        ItemPicker<Integer> picker = new ItemPickerForApplication<Integer>(context) {

            @Override
            protected void onItemSelected(SelectableItem<Integer> sender, Integer newlySelectedItem, List<Integer> allSelectedItems) {
                inputListener.onFuelChargeChanged(newlySelectedItem);
            }

            @Override
            protected void onItemUnselected(SelectableItem<Integer> item, Integer unselectedItem, List<Integer> allSelectedItems) {
                inputListener.onFuelChargeChanged(null);
            }

            @Override
            public String getDisplayText(Integer value) {
                return value == 0 ? "To be discussed" : String.format(Locale.ENGLISH,"%d Ugx.",value);
            }
        };
        picker.setData(getData());
        return picker.getView();
    }

    private View header() {
        return MakeDummy.linearLayoutVertical(
                context,
                Atom.textViewPrimaryBold(context,"Fuel Contribution"),
                Atom.textView(context,"How much fuel contribution do you expect from each passenger (per seat)?")
        );
    }

    private List<Integer> getData() {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i <= 60000; i+=5000){
            result.add(i);
        }
        return result;
    }

    @Override
    public boolean appliesToPassenger() {
        return false;
    }
}
