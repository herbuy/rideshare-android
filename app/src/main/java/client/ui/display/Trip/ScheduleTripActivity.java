package client.ui.display.Trip;
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.skyvolt.jabber.R;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import client.ui.GotoActivity;
import client.ui.IntentExtras;
import client.ui.display.Location.GetLocationFromAddress;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarModel;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarRegNumber;
import client.ui.display.Trip.schedule_trip.p20210410.FindARideForm;
import client.ui.display.Trip.schedule_trip.p20210410.FindPassengerForm;
import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.TripUploadForm;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_car_info.CarInfo;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_time.DepartureTime;
import client.ui.display.Trip.schedule_trip.steps.CarModelCache;
import client.ui.display.Trip.schedule_trip.steps.CarRegNumCache;
import core.businessobjects.Trip;
import libraries.HerbuyCalendar;
import libraries.android.AdapterForFragmentPager;
import libraries.android.AddressListFromString;
import libraries.android.ObjectTaskPermit;
import libraries.android.TabInterceptor;
import libraries.underscore.Singleton;
import shared.BaseActivity;
import test.CurrentLocation;

public class ScheduleTripActivity extends BaseActivity{

    private Singleton<ObjectTaskPermit> locationAccessPermit = new Singleton<ObjectTaskPermit>() {
        @Override
        protected ObjectTaskPermit onCreate() {
            return new ObjectTaskPermit(getContext()).addGroupLocationAccess();
        }
    };
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationAccessPermit.instance().processPermissionsResult(requestCode,permissions,grantResults);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setTitle("+ Schedule Trip");
        String tripFormParameters = getIntent().getExtras().getString(IntentExtras.formDisplayParameters);
        //setContentView(contentView(tripFormParameters));

        setContentView(R.layout.activity_tabbed);
        ViewPager viewPager = findViewById(R.id.container);
        //viewPager.setBackgroundColor(ItemColor.primary());

        viewPager.setAdapter(
                new AdapterForFragmentPager(this.getSupportFragmentManager())

                        .addTab(
                                "Find a lift",
                                findARideForm.instance().getView()
                        )
                        .addTab(
                                "Give a lift",
                               findPassengersForm.instance().getView()
                        )
        );

        final TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        TabInterceptor.intercept(tabLayout);



        setDefaultFieldValues();

    }

    private void setDefaultFieldValues() {
        EventBusForTaskUploadTrip.travelDateChanged.notifyObservers(new HerbuyCalendar().nextDay());
        EventBusForTaskUploadTrip.seatCountChanged.notifyObservers(1);
        EventBusForTaskUploadTrip.fuelContributionChanged.notifyObservers(20000);
        setDefaultDepartureTime();
        setDefaultCarInfo();
        setDefaultOrigin();

    }

    private void setDefaultCarInfo() {
        List<CarModel> models = new CarModelCache().selectAll();


        List<CarRegNumber> regNumbers = new CarRegNumCache().selectAll();


        CarInfo carInfo = new CarInfo();
        if(models.size() > 0){
            Collections.sort(models, new Comparator<CarModel>() {
                @Override
                public int compare(CarModel first, CarModel second) {
                    return Long.compare(second.timestampLastUpdated,first.timestampLastUpdated);
                }
            });
            carInfo.carModel = models.get(0).model;
        }
        if(regNumbers.size() > 0){
            Collections.sort(regNumbers, new Comparator<CarRegNumber>() {
                @Override
                public int compare(CarRegNumber first, CarRegNumber second) {
                    return Long.compare(second.timestampLastUpdated,first.timestampLastUpdated);
                }
            });
            carInfo.carRegNumber = regNumbers.get(0).regNumber;
        }

        EventBusForTaskUploadTrip.carInfoChanged.notifyObservers(carInfo);
    }

    private void setDefaultOrigin() {
        locationAccessPermit.instance().requestThenRun(new ObjectTaskPermit.Callback() {
            @Override
            public void ifAllGranted() {
                trySettingDefaultOrigin();
            }

            @Override
            public void ifSomeDenied() {

            }
        });
    }

    private void setDefaultDepartureTime() {
        EventBusForTaskUploadTrip.departureTimeChanged.notifyObservers(
               new DepartureTime(
                       new HerbuyCalendar(System.currentTimeMillis()).getHourOfTheDay()
               )
        );
    }

    private void trySettingDefaultOrigin() {
        //get the current location
        CurrentLocation.getPeriodicUsingGPS(this, new CurrentLocation.Callback() {
            @Override
            public void locationChanged(Location location, LocationManager mgr, LocationListener listener) {
                //try to geocode

                //if origin has already been set at least once, no need to auto-set
                if(EventBusForTaskUploadTrip.originChanged.getTotalCalls() > 0){
                    mgr.removeUpdates(listener);
                    return;
                }


                //reverse geocode the address
                //geocode to find the address
                AddressListFromString addressListFromString = new AddressListFromString(
                        getContext(),
                        location.getLatitude(),
                        location.getLongitude()
                );

                if(addressListFromString.hasError() || addressListFromString.isEmpty()){
                    return;
                }


                EventBusForTaskUploadTrip.originChanged.notifyObservers(
                        GetLocationFromAddress.where(addressListFromString.getAddresses().get(0))
                );
            }

            @Override
            public void providerDisabled() {

            }
        });
    }

    private Singleton<FindARideForm> findARideForm = new Singleton<FindARideForm>() {
        @Override
        protected FindARideForm onCreate() {
            return  new FindARideForm(getContext());
        }
    };

    private Singleton<FindPassengerForm> findPassengersForm = new Singleton<FindPassengerForm>() {
        @Override
        protected FindPassengerForm onCreate() {
            return  new FindPassengerForm(getContext());
        }
    };

    private Activity getContext() {
        return this;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBusForTaskUploadTrip.clear();
    }

    private View contentView(String formDisplayParameters) {

        TripUploadForm tripDataInputForm = new TripUploadForm(this){
            @Override
            public void tripScheduled(Trip trip) {
                TripScheduledEvent.instance().notifyObservers(trip);
                GotoActivity.tripDetails(ScheduleTripActivity.this,trip);
                finish();
            }
        };
        return tripDataInputForm.getView();

        /*TripDataInputForm tripDataInputForm = new TripDataInputForm(this){
            @Override
            public void tripScheduled(Trip trip) {

                TripScheduledEvent.instance().notifyObservers(trip);
                GotoActivity.tripDetails(ScheduleTripActivity.this,trip);
                finish();
            }
        };
        return tripDataInputForm.getView();*/

    }

}
