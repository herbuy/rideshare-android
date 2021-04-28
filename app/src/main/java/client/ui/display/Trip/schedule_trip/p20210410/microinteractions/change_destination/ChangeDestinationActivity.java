package client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_destination;

import android.os.Bundle;
import android.view.View;
import java.util.List;
import client.ui.display.Trip.schedule_trip.p20210410.LocationInputDialog;
import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import core.businessobjects.Location;
import resources.TransitionName;
import shared.BaseActivity;

public class ChangeDestinationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView());

    }

    View view;
    private View contentView() {
        view = new LocationInputDialog(this){
            @Override
            protected String getQuestion() {
                return "City or District E.g. Fort Portal";
            }

            @Override
            protected String getPurposeAndUsage() {
                return "";
            }

            @Override
            protected String getReasonToRejectSelectOrNull(Location value) {
                return null;
            }

            @Override
            protected boolean isMultipleItemPicker() {
                return false;
            }

            @Override
            protected void onItemSelected(View sender, Location newlySelectedItem, List<Location> allSelectedItems) {
                view.setTransitionName("");
                sender.setTransitionName(TransitionName.destination);
                EventBusForTaskUploadTrip.destinationChanged.notifyObservers(newlySelectedItem);
                onBackPressed();
            }
        }.getView();

        view.setTransitionName(TransitionName.destination);


        return view;
    }
}