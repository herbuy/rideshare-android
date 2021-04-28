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
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarRegNumber;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarModel;
import client.ui.display.Trip.schedule_trip.steps.SetDestinationInfo;
import client.ui.display.Trip.schedule_trip.steps.SetOriginInfo;
import core.businessmessages.toServer.ParamsForScheduleTrip;
import core.businessobjects.Location;
import core.validators.ValidateParamsForScheduleTrip;
import libraries.HerbuyCalendar;
import libraries.underscore.Singleton;
import libraries.SlideList;

public abstract class TripDataInputForm extends TripDataInputListener {
    private Context context;
    LinearLayout wrapper;


    public TripDataInputForm(Context context) {
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

        return viewPager.instance().getView();

    }


    private TripDataInputForm getThis() {
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
                    Arrays.asList(
                            new DestinationInputDialog(context, getThis()),
                            new OriginInputDialog(context, getThis()),
                            new DateInputDialog(context, getThis()),
                            new TimeInputDialog(context, getThis()),

                            new DrivingStatusInputDialog(context, getThis()),
                            carModelInputDialog.instance(),
                            carRegNumInputDialog.instance(),
                            seatsInputDialog.instance(),
                            fuelChargeInputDialog.instance(),
                            routeRequiredInputDialog.instance(),
                            routeInputDialog.instance(),
                            reviewDialog.instance()
                            )
            );

            return list;
        }
    };

    private Singleton<RouteRequiredInputDialog> routeRequiredInputDialog = new Singleton<RouteRequiredInputDialog>() {
        @Override
        protected RouteRequiredInputDialog onCreate() {
            return new RouteRequiredInputDialog(context,getThis());
        }
    };

    private Singleton<RouteInputDialog> routeInputDialog = new Singleton<RouteInputDialog>() {
        @Override
        protected RouteInputDialog onCreate() {
            RouteInputDialog dialog = new RouteInputDialog(context, getThis());
            dialog.getView().setVisibility(View.GONE);
            return dialog;
        }
    };

    private Singleton<CarModelInputDialog> carModelInputDialog = new Singleton<CarModelInputDialog>() {
        @Override
        protected CarModelInputDialog onCreate() {
            return new CarModelInputDialog(context, getThis());
        }
    };

    private Singleton<CarRegNumInputDialog> carRegNumInputDialog = new Singleton<CarRegNumInputDialog>() {
        @Override
        protected CarRegNumInputDialog onCreate() {
            return new CarRegNumInputDialog(context, getThis());
        }
    };

    private Singleton<SeatsAvailableInputDialog> seatsInputDialog = new Singleton<SeatsAvailableInputDialog>() {
        @Override
        protected SeatsAvailableInputDialog onCreate() {
            return new SeatsAvailableInputDialog(context, getThis());
        }
    };

    private Singleton<FuelChargeInputDialog> fuelChargeInputDialog = new Singleton<FuelChargeInputDialog>() {
        @Override
        protected FuelChargeInputDialog onCreate() {
            return new FuelChargeInputDialog(context, getThis());
        }
    };

    Singleton<SlideList> viewPager = new Singleton<SlideList>() {
        @Override
        protected SlideList onCreate() {

            final SlideList viewPager = new SlideList(context);



            for (TripInputDialog page : pages.instance()) {
                viewPager.add(page.getView());
            }
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
        viewPager.instance().animateToNext();

    }

    @Override
    public void displayPreviousPage() {
        viewPager.instance().animateToPrevious();
    }

    private boolean isLastPage(int position) {
        return position == viewPager.instance().getVisibleSlideCount() - 1;
    }

    @Override
    public boolean isOnLastPage() {
        return viewPager.instance().isOnLastSlide();
    }

    public boolean hasNextPage() {
        return viewPager.instance().hasNextSlide();
    }

    public boolean hasPreviousPage() {
        return viewPager.instance().hasPreviousSlide();
    }

    private void displayPage(int position) {
        if (viewPager.instance().hasSlide(position)) {
            viewPager.instance().animateToSlide(position);
        }
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

        if(isDriving != null && !isDriving){
            clearDriverOnlyData();
            hideDriverQuestions();
            changeDialogsToPassengerMode();

        }
        else{
            showDriverQuestions();
            changeDialogsToDriverMode();
        }
        reviewDialog.instance().setDrivingStatus(newValue);

    }

    public void changeDialogsToDriverMode() {
        seatsInputDialog.instance().changeViewToDriver();
        routeRequiredInputDialog.instance().changeToDriver();
        routeInputDialog.instance().changeViewToDriver();
    }

    public void changeDialogsToPassengerMode() {
        seatsInputDialog.instance().changeViewToPassenger();
        routeRequiredInputDialog.instance().changeToPassenger();
        routeInputDialog.instance().changeViewToPassenger();
    }

    public void clearDriverOnlyData() {
        onCarModelChanged(new CarModel(""));
        onCarRegNumChanged(new CarRegNumber(""));
        onFuelChargeChanged(0);
    }

    public void hideDriverQuestions() {
        carModelInputDialog.instance().getView().setVisibility(View.GONE);
        carRegNumInputDialog.instance().getView().setVisibility(View.GONE);
        fuelChargeInputDialog.instance().getView().setVisibility(View.GONE);
    }

    public void showDriverQuestions() {
        carModelInputDialog.instance().getView().setVisibility(View.VISIBLE);
        carRegNumInputDialog.instance().getView().setVisibility(View.VISIBLE);
        fuelChargeInputDialog.instance().getView().setVisibility(View.VISIBLE);
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
    public void setChooseRouteRequired(Boolean isChooseRouteRequired) {
        if(isChooseRouteRequired == null || !isChooseRouteRequired){
            routeInputDialog.instance().getView().setVisibility(View.GONE);
            route = new ArrayList<>();
            reviewDialog.instance().setRoute(route);
        }
        else{
            routeInputDialog.instance().getView().setVisibility(View.VISIBLE);
            reviewDialog.instance().setRoute(route);
        }


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
