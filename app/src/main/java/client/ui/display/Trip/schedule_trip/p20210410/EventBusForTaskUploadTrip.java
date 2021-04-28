package client.ui.display.Trip.schedule_trip.p20210410;

import android.content.Context;
import android.view.View;

import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_car_info.CarInfo;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_car_info.ChangeCarDetailsActivity;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_date.ChangeTravelDateActivity;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_fuel_contribution.ChangeFuelContributionActivity;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_origin.ChangeOriginActivity;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_destination.ChangeDestinationActivity;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_seat_count.ChangeSeatCountActivity;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_time.ChangeDepartureTimeActivity;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_time.DepartureTime;
import client.ui.display.Trip.schedule_trip.p20210410.shared.TextInputDialog;
import core.businessobjects.Location;
import libraries.HerbuyCalendar;
import libraries.ObserverList;
import libraries.android.GetParentActivity;
import libraries.android.Sdk;
import resources.TransitionName;

/** receives and dispatches events related to the task of uploading a trip */
public class EventBusForTaskUploadTrip {

    /** called when you want to start the loop for changing the destination */
    public static void startLoopForChangeDestination(View sender) {
        sender.setTransitionName(TransitionName.destination);
        Sdk.StartActivity.singleSharedElement(
                GetParentActivity.fromView(sender),
                ChangeDestinationActivity.class,
                sender
        );
        //context.startActivity(new Intent(context, ChangeDestinationActivity.class));
    }

    public static void startLoopForChangeOrigin(View sender) {
        sender.setTransitionName(TransitionName.origin);
        Sdk.StartActivity.singleSharedElement(
                GetParentActivity.fromView(sender),
                ChangeOriginActivity.class,
                sender
        );
    }

    public static final ObserverList<Location> destinationChanged = new ObserverList<>();
    public static final ObserverList<Location> originChanged = new ObserverList<>();
    public static final ObserverList<HerbuyCalendar> travelDateChanged = new ObserverList<>();
    public static final ObserverList<DepartureTime> departureTimeChanged = new ObserverList<>();
    public static final ObserverList<Integer> seatCountChanged = new ObserverList<>();
    public static final ObserverList<Integer> fuelContributionChanged = new ObserverList<>();
    public static final ObserverList<String> carModelChanged = new ObserverList<>();
    public static final ObserverList<String> carRegNumberChanged = new ObserverList<>();
    public static final ObserverList<CarInfo> carInfoChanged = new ObserverList<>();

    public static void clear() {

        destinationChanged.clear();
        originChanged.clear();
        travelDateChanged.clear();
        departureTimeChanged.clear();
        seatCountChanged.clear();
        fuelContributionChanged.clear();
        carModelChanged.clear();
        carRegNumberChanged.clear();
        carInfoChanged.clear();

    }

    public static void startLoopForChangeTravelDate(View sender) {
        sender.setTransitionName(TransitionName.date);
        Sdk.StartActivity.singleSharedElement(
                GetParentActivity.fromView(sender),
                ChangeTravelDateActivity.class,
                sender
        );
    }

    public static void startLoopForChangeDepartureTime(View sender) {
        sender.setTransitionName(TransitionName.time);
        Sdk.StartActivity.singleSharedElement(
                GetParentActivity.fromView(sender),
                ChangeDepartureTimeActivity.class,
                sender
        );
    }

    public static void startLoopForChangeCarInfo(View sender) {
        sender.setTransitionName(TransitionName.carInfo);
        Sdk.StartActivity.singleSharedElement(
                GetParentActivity.fromView(sender),
                ChangeCarDetailsActivity.class,
                sender
        );
    }

    public static void startLoopForChangeSeatCount(View sender) {

        sender.setTransitionName(TransitionName.seatCount);
        Sdk.StartActivity.singleSharedElement(
                GetParentActivity.fromView(sender),
                ChangeSeatCountActivity.class,
                sender
        );
    }

    public static void startLoopForChangeFuelContribution(View sender) {

        sender.setTransitionName(TransitionName.fuelContribution);
        Sdk.StartActivity.singleSharedElement(
                GetParentActivity.fromView(sender),
                ChangeFuelContributionActivity.class,
                sender
        );
    }

    public static void startLoopForChangeCarModel(final Context context) {
        new TextInputDialog(context) {
            @Override
            protected void onClickOk(String newValue) {
                carModelChanged.notifyObservers(newValue);
            }

            @Override
            protected String getTitleText() {
                return "Your car model";
            }

            @Override
            protected String getHintText() {
                return "E.g. Subaru Forester";
            }
        };

    }

    public static void startLoopForChangeCarRegNumber(final Context context) {
        new TextInputDialog(context) {
            @Override
            protected void onClickOk(String newValue) {
                carRegNumberChanged.notifyObservers(newValue);
            }

            @Override
            protected String getTitleText() {
                return "Car Registration Number";
            }

            @Override
            protected String getHintText() {
                return "E.g. UAX 405 T";
            }
        };

    }

}
