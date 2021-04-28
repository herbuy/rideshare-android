package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import client.data.LocalSession;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarModel;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarRegNumber;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import client.ui.display.Trip.schedule_trip.steps.SetDestinationInfo;
import client.ui.display.Trip.schedule_trip.steps.SetOriginInfo;
import core.businessmessages.toServer.ParamsForScheduleTrip;
import core.businessobjects.Location;
import core.validators.ValidateParamsForScheduleTrip;
import libraries.HerbuyCalendar;
import libraries.android.ViewPagerWithoutFragments;
import libraries.underscore.Singleton;

public abstract class TripDataInputForm0 extends TripDataInputListener {
    private Context context;
    LinearLayout wrapper;


    public TripDataInputForm0(Context context) {
        this.context = context;
        init();
    }

    public void init() {
        wrapper = new LinearLayout(context);
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        wrapper.setBackgroundColor(Color.WHITE);
        wrapper.addView(content());
    }

    private View content() {

        return viewPager.instance();

    }


    private TripDataInputForm0 getThis() {
        return this;
    }

    private class PageComposite extends ArrayList<TripInputDialog> {

        public void notifyMovedForward(int position) {
            for (TripInputDialog page : this) {
                page.notifyMovedForward(position);
            }
        }

        public void notifyMovedBackwards(int position) {
            for (TripInputDialog page : this) {
                page.notifyMovedBackwards(position);
            }
        }

    }

    Singleton<PageComposite> pages = new Singleton<PageComposite>() {
        @Override
        protected PageComposite onCreate() {
            PageComposite list = new PageComposite();
            list.addAll(
                    Arrays.asList(new DestinationInputDialog(context, getThis()),
                            new OriginInputDialog(context, getThis()),
                            new RouteInputDialog(context, getThis()),
                            new DateInputDialog(context, getThis()),
                            new TimeInputDialog(context, getThis()),

                            new DrivingStatusInputDialog(context, getThis()),
                            new CarModelInputDialog(context, getThis()),
                            new CarRegNumInputDialog(context, getThis()),
                            new SeatsAvailableInputDialog(context, getThis()),
                            new FuelChargeInputDialog(context, getThis()),
                            reviewDialog.instance())
            );

            return list;
        }
    };

    Singleton<ViewPagerWithoutFragments> viewPager = new Singleton<ViewPagerWithoutFragments>() {
        @Override
        protected ViewPagerWithoutFragments onCreate() {

            ViewPagerWithoutFragments viewPager = new ViewPagerWithoutFragments(context);
            for (TripInputDialog page : pages.instance()) {
                viewPager.add(page.getView());
            }
            viewPager.setOffscreenPageLimit(14);
            viewPager.addOnPageChangeListener(pageChangeListener.instance());
            return viewPager;
        }
    };

    Singleton<ViewPager.SimpleOnPageChangeListener> pageChangeListener = new Singleton<ViewPager.SimpleOnPageChangeListener>() {
        @Override
        protected ViewPager.SimpleOnPageChangeListener onCreate() {
            return new ViewPager.SimpleOnPageChangeListener() {
                int previousPage = -1;

                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    int movedForward = position - previousPage;
                    previousPage = position;


                    if (movedForward > 0) {
                        pages.instance().notifyMovedForward(position);
                    } else if (movedForward < 0) {
                        pages.instance().notifyMovedBackwards(position);
                    } else {
                        stayedOnSamePage(position);
                    }

                    //modify title to see
                }
            };
        }


    };

    private void stayedOnSamePage(int position) {

    }

    //Set<Integer> driverOnlyPages = new HashSet<>(Arrays.asList(6,7,8,9));


    private boolean isPassenger() {
        return isDriving == null || !isDriving;
    }

    @Override
    public boolean pageApplicableToCurrentUser(int position) {
        return
                isDriving == null
                        || isDriving
                        || isPassenger()
                        && pages.instance().get(position).appliesToPassenger();

        /*
        if(isDriving == null){
            return true;
        }
        if(isDriving){
            return true;
        }

        //at this point, they are not driving
        if(!driverOnlyPages.contains(position)){
            return true;
        }

        return false;*/
    }

    @Override
    public void displayNextPage() {
        displayPage(viewPager.instance().getCurrentItem() + 1);
    }

    @Override
    public void displayPreviousPage() {
        displayPage(viewPager.instance().getCurrentItem() - 1);
    }

    private boolean isLastPage(int position) {
        return position == viewPager.instance().getAdapter().getCount() - 1;
    }

    @Override
    public boolean isOnLastPage() {
        return viewPager.instance().getAdapter().getCount() > 0
                && viewPager.instance().getCurrentItem() ==
                (viewPager.instance().getAdapter().getCount() - 1);
    }

    public boolean hasNextPage() {
        return hasPage(viewPager.instance().getCurrentItem() + 1);
    }

    public boolean hasPreviousPage() {
        return hasPage(viewPager.instance().getCurrentItem() - 1);
    }

    private void displayPage(int position) {
        if (hasPage(position)) {
            viewPager.instance().setCurrentItem(position);
        }
    }

    private boolean hasPage(int page) {
        return viewPager.instance().getAdapter().getCount() > 0
                && page >= 0
                && page < viewPager.instance().getAdapter().getCount();
    }

    Singleton<TripDataReviewDialog> reviewDialog = new Singleton<TripDataReviewDialog>() {
        @Override
        protected TripDataReviewDialog onCreate() {
            return new TripDataReviewDialog(context, getThis());
        }
    };


    private Boolean isDriving;

    @Override
    public void drivingStatusChanged(Boolean newValue) {
        isDriving = newValue;
        reviewDialog.instance().setDrivingStatus(newValue);
    }

    @Override
    public String getErrorInOriginOrNull(Location value) {

        if (dest == null || value == null) {
            return "";
        }

        if (value.matchesUptoSubAdmin(dest)) {
            return "Origin can not be same or similar to destination";
        }

        return "";

    }

    //can reduce size of this class by taking this validation code into another class
    //which just contains business logic or app logic
    @Override
    public String getErrorInDestinationOrNull(Location value) {
        if (orig == null || value == null) {
            return "";
        }

        if (value.matchesUptoSubAdmin(orig)) {
            return "Destination can not be same or similar to origin";
        }

        return "";

    }

    public View getView() {
        return wrapper;
    }

    private Location dest;

    @Override
    public void onDestinationChanged(Location newValue) {
        dest = newValue;
        reviewDialog.instance().setDestination(newValue);
    }

    private Location orig;

    @Override
    public void onOriginChanged(Location newValue) {
        orig = newValue;
        reviewDialog.instance().setOrigin(newValue);

    }

    List<Location> route = new ArrayList<>();

    @Override
    public void onRouteChanged(List<Location> newValue) {
        if (route != null) {
            route = newValue;
        } else {
            route = new ArrayList<>();
        }
        reviewDialog.instance().setRoute(route);

    }

    @Override
    public String getErrorRouteItem(Location value) {
        if (value == null) {
            return null;
        }
        if (value.matchesUptoSubAdmin(orig)) {
            return "Can not be same as your origin";
        }
        if (value.matchesUptoSubAdmin(dest)) {
            return "Can not be same as your destination";
        }

        return null;
    }

    private HerbuyCalendar travelDate;

    @Override
    public void onDateChanged(HerbuyCalendar newValue) {
        travelDate = newValue;
        reviewDialog.instance().setTravelDate(newValue);
    }

    TimeInputDialog.Hour travelTime;

    @Override
    public void onTimeChanged(TimeInputDialog.Hour newValue) {
        travelTime = newValue;
        reviewDialog.instance().setTravelTime(newValue);
    }

    private CarModel carModel;

    @Override
    public void onCarModelChanged(CarModel newValue) {
        carModel = newValue;
        reviewDialog.instance().setCarModel(newValue);
    }

    private CarRegNumber carRegNumber;

    @Override
    public void onCarRegNumChanged(CarRegNumber newValue) {
        carRegNumber = newValue;
        reviewDialog.instance().setCarRegNumber(newValue);
    }

    Integer fuelCharges;

    @Override
    public void onFuelChargeChanged(Integer newValue) {
        fuelCharges = newValue;
        reviewDialog.instance().setFuelCharges(newValue);
    }

    Integer seatsAvailable;

    @Override
    public void onSeatsAvailableChanged(Integer newValue) {
        seatsAvailable = newValue;
        reviewDialog.instance().setSeatsAvailable(newValue);
    }

    @Override
    public ParamsForScheduleTrip getParamsForScheduleTrip() {
        ParamsForScheduleTrip paramsForScheduleTrip = new ParamsForScheduleTrip();
        paramsForScheduleTrip.setSessionId(LocalSession.instance().getId());
        paramsForScheduleTrip.setForUserId(LocalSession.instance().getUserId());

        SetDestinationInfo.where(paramsForScheduleTrip, dest);
        SetOriginInfo.where(paramsForScheduleTrip, orig);

        paramsForScheduleTrip.setVehicleModel(carModel == null ? "" : carModel.model);
        paramsForScheduleTrip.setVehicleRegNumber(carRegNumber == null ? "" : carRegNumber.regNumber);
        paramsForScheduleTrip.setMaxConcurrentTTMarriages(seatsAvailable == null ? 1 : seatsAvailable);

        paramsForScheduleTrip.setFuelCharge(fuelCharges == null ? 0 : fuelCharges);

        paramsForScheduleTrip.setTripDate(travelDate == null ? "" : makeDateString(travelDate));

        paramsForScheduleTrip.setTripTime(travelTime == null ? "" : travelTime.toTime());

        paramsForScheduleTrip.getPassingViaLocations().addAll(route);

        return paramsForScheduleTrip;
    }

    @Override
    public boolean readyToUpload() {
        ParamsForScheduleTrip params = getParamsForScheduleTrip();
        if(params == null){
            return false;
        }
        try{
            ValidateParamsForScheduleTrip.where(params);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    private String makeDateString(HerbuyCalendar travelDate) {
        return String.format(
                Locale.ENGLISH,
                "%d-%d-%d",
                travelDate.getYear(),
                travelDate.getMonth() + 1,
                travelDate.getDayOfMonth()
        );
    }
}
