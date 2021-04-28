package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPicker;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPickerForApplication;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;
import libraries.android.MakeDummy;
import libraries.underscore.Singleton;

public class RouteRequiredInputDialog extends TripInputDialog {

    public RouteRequiredInputDialog(Context context, TripDataInputListener inputListener) {
        super(context, inputListener);
    }


    public View getView() {
        return wrapper.instance();
    }

    TitleSetCallback.TitleChangerLink changerLink;
    Singleton<View> wrapper = new Singleton<View>() {
        @Override
        protected View onCreate() {
            return makeWrapper(passengerTitle(), bodyWrapper.instance(), new TitleSetCallback() {
                @Override
                public void receiveLink(TitleChangerLink changer) {
                    changerLink = changer;
                }
            });
        }
    };


    private String textForNo = textForNoIfPassenger();
    private Singleton<ViewGroup> bodyWrapper = new Singleton<ViewGroup>() {
        @Override
        protected ViewGroup onCreate() {
            LinearLayout linearLayout = MakeDummy.linearLayoutVertical(context,body());
            return linearLayout;
        }
    };
    private View body() {
        ItemPicker<Boolean> picker = new ItemPickerForApplication<Boolean>(context) {

            @Override
            protected void onItemSelected(SelectableItem<Boolean> sender, Boolean newlySelectedItem, List<Boolean> allSelectedItems) {
                inputListener.setChooseRouteRequired(newlySelectedItem);
            }

            @Override
            protected void onItemUnselected(SelectableItem<Boolean> item, Boolean unselectedItem, List<Boolean> allSelectedItems) {
                inputListener.setChooseRouteRequired(null);
            }

            @Override
            public String getDisplayText(Boolean isDriving) {
                return isDriving ? "Yes, for places I choose" : textForNo;
            }
        };
        picker.setData(Arrays.asList(true,false));

        return picker.getView();
    }

    public String textForNoIfPassenger() {
        return "No, do not show";
    }
    public String textForNoIfDriver() {
        return "No, won't pick up";
    }

    private String passengerTitle() {
        return "Also show you drivers stopping along the way?";
    }

    private String driverTitle() {
        return "Want to pick up passengers along the way?";
    }


    public void changeToPassenger() {
        changerLink.changeTitle(passengerTitle());
        textForNo = textForNoIfPassenger();
        refreshList();
    }
    public void changeToDriver() {
        changerLink.changeTitle(driverTitle());
        textForNo = textForNoIfDriver();
        refreshList();
    }

    private void refreshList() {
        bodyWrapper.instance().removeAllViews();
        bodyWrapper.instance().addView(body());
    }
}
