package client.ui.display.Trip;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest2;
import client.ui.GotoActivity;
import client.ui.display.Trip.schedule_trip.pickers.HourOfDayPicker;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarModel;
import client.ui.display.Trip.schedule_trip.steps.CarModelInputSystem;
import client.ui.display.Trip.schedule_trip.steps.CarNumberInputSystem;
import client.ui.display.Trip.schedule_trip.steps.DestinationStep;
import client.ui.display.Trip.schedule_trip.steps.DrivingStatusInputSystem;
import client.ui.display.Trip.schedule_trip.steps.FuelChargeInputSystem;
import client.ui.display.Trip.schedule_trip.steps.RouteInputSystem;
import client.ui.display.Trip.schedule_trip.steps.ScheduleTripSystem;
import client.ui.display.Trip.schedule_trip.steps.SeatsAvailableInputSystem;
import client.ui.display.Trip.schedule_trip.steps.SeatsRequiredStep;
import client.ui.display.Trip.schedule_trip.steps.SetDestinationInfo;
import client.ui.display.Trip.schedule_trip.steps.SetOriginInfo;
import client.ui.display.Trip.schedule_trip.steps.TravelDateInputSystem;
import client.ui.display.Trip.schedule_trip.steps.TravelInfoReviewSystem;
import client.ui.display.Trip.schedule_trip.steps.TravelOriginInputSystem;
import client.ui.display.Trip.schedule_trip.steps.TravelTimeStep;
import client.ui.display.Trip.schedule_trip.steps.TripSchedulingStep;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.libraries.Form;
import core.businessmessages.toServer.ParamsForScheduleTrip;
import core.businessobjects.Location;
import core.businessobjects.Trip;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.HerbuyCalendar;
import libraries.android.HerbuyStateView;
import retrofit2.Call;
import retrofit2.Response;

public class ScheduleTripDesign implements Form.Design {
    AppCompatActivity context;

    public ScheduleTripDesign(AppCompatActivity context) {
        this.context = context;

        init();
    }

    private void init() {
        steps = new TripSchedulingStep[]{

                destination(context),
                /*origin(context),
                passingVia(),

                travelDate(context),
                travelTime(),

                drivingStatus(),
                carModel(),
                carNumber(),
                seatsAvailable(),
                //seatsRequired(context),
                fuelCharge(),
                finalStep(context)*/
        };
    }

    @Override
    public String prize() {
        return "Travel Details";
    }

    TripSchedulingStep[] steps;
    @Override
    public TripSchedulingStep[] steps() {


        return new TripSchedulingStep[]{};
        //return steps;
    }

    DrivingStatusInputSystem drivingStatusInputSystem;
    private boolean userIsDriving = true;

    private TripSchedulingStep drivingStatus() {
        drivingStatusInputSystem = new DrivingStatusInputSystem(context) {
            @Override
            protected void drivingStatusChanged(Boolean driving) {

                if (null == driving) {
                    userIsDriving = true;
                } else {
                    userIsDriving = driving;
                }
            }
        };
        return drivingStatusInputSystem;
    }


    private boolean stepApplicableToCurrentTripType(TripSchedulingStep step) {
        return !step.isForDriversOnly() || (step.isForDriversOnly() && userIsDriving);
    }


    public SeatsRequiredStep seatsRequired(Context context) {
        return new SeatsRequiredStep(context) {
            @Override
            protected void seatCountChanged(Integer count) {
                travelInfoReviewSystem.updateSeatAvailable(count);
            }
        };
    }

    private ScheduleTripSystem dialog = new ScheduleTripSystem() {


    };

    RouteInputSystem routeInputSystem;
    List<Location> passVia = new ArrayList<>();

    public RouteInputSystem passingVia() {
        routeInputSystem = new RouteInputSystem(context) {
            @Override
            protected ScheduleTripSystem dialog() {
                return dialog;
            }

            @Override
            protected void selectedLocationsChanged(List<Location> selectedValues) {
                travelInfoReviewSystem.updatePassingVia(selectedValues);
                passVia = selectedValues == null ? new ArrayList<Location>() : selectedValues;
            }
        };
        return routeInputSystem;
    }


    FuelChargeInputSystem fuelChargeInputSystem;
    int fuelCharges;

    public FuelChargeInputSystem fuelCharge() {
        fuelChargeInputSystem = new FuelChargeInputSystem(context) {
            @Override
            protected void amountChanged(Integer newAmount) {
                travelInfoReviewSystem.updateFuelCharges(newAmount);
                fuelCharges = newAmount == null ? 0 : newAmount;
            }
        };
        return fuelChargeInputSystem;
    }

    SeatsAvailableInputSystem seatsAvailableInputSystem;
    int seatsAvailable;

    public SeatsAvailableInputSystem seatsAvailable() {
        seatsAvailableInputSystem = new SeatsAvailableInputSystem(context) {
            @Override
            protected void onSeatCountChanged(Integer count) {
                travelInfoReviewSystem.updateSeatAvailable(count);
                seatsAvailable = count == null ? 0 : count;
            }
        };
        return seatsAvailableInputSystem;
    }

    CarNumberInputSystem carNumberInputSystem;
    String carNumber = "";

    public CarNumberInputSystem carNumber() {
        carNumberInputSystem = new CarNumberInputSystem(context) {
            @Override
            protected void onCarRegistrationNumberChanged(String value) {
                travelInfoReviewSystem.updateCarRegistrationNumber(value);
                carNumber = value == null || value.trim().equalsIgnoreCase("") ? "" : value.trim();
            }
        };
        return carNumberInputSystem;
    }

    CarModelInputSystem carModelInputSystem;
    String carModel = "";

    public CarModelInputSystem carModel() {
        carModelInputSystem = new CarModelInputSystem(context) {
            @Override
            protected void carModelChanged(CarModel item) {
                travelInfoReviewSystem.updateCarModel(item);
                carModel = item == null ? "" : item.model;
            }
        };
        return carModelInputSystem;
    }

    TravelTimeStep travelTimeStep;
    String travelTime = "";

    public TravelTimeStep travelTime() {
        travelTimeStep = new TravelTimeStep(context) {
            @Override
            protected void travelTimeChanged(HourOfDayPicker.Hour hour) {
                travelInfoReviewSystem.updateTravelTime(hour);
                travelTime = hour == null ? "" : hour.toTime();
            }
        };
        return travelTimeStep;
    }


    TravelDateInputSystem travelDateInputSystem;
    String travelDate = "";

    public TravelDateInputSystem travelDate(Context context) {
        travelDateInputSystem = new TravelDateInputSystem(context) {
            @Override
            protected void onTripDateChanged(HerbuyCalendar newDate) {
                travelInfoReviewSystem.updateTravelDate(newDate);
                travelDate = newDate == null ? "" : String.format(
                        Locale.ENGLISH,
                        "%d-%d-%d",
                        newDate.getYear(),
                        newDate.getMonth() + 1,
                        newDate.getDayOfMonth()
                );
            }
        };
        return travelDateInputSystem;
    }

    DestinationStep destinationStep;
    Location dest = new Location();

    public TripSchedulingStep destination(Context context) {
        destinationStep = new DestinationStep(context) {
            @Override
            protected ScheduleTripSystem dialog() {
                return dialog;
            }

            @Override
            protected void onLocationSelected(Location item) {
                travelInfoReviewSystem.updateDestination(item);
                dest = item;
            }

            @Override
            protected void onLocationUnSelected(Location item) {
                travelInfoReviewSystem.updateDestination(null);
                dest = new Location();
            }
        };

        return destinationStep;
    }

    TravelOriginInputSystem travelOriginInputSystem;
    Location origin = new Location();

    public TravelOriginInputSystem origin(Context context) {
        travelOriginInputSystem = new TravelOriginInputSystem(context) {
            @Override
            protected ScheduleTripSystem dialog() {
                return dialog;
            }

            @Override
            protected void onLocationSelected(Location item) {
                travelInfoReviewSystem.updateOrigin(item);
                origin = item;
            }

            @Override
            protected void onLocationUnSelected(Location item) {
                travelInfoReviewSystem.updateOrigin(null);
                origin = new Location();
            }
        };
        return travelOriginInputSystem;
    }

    TravelInfoReviewSystem travelInfoReviewSystem;

    public TravelInfoReviewSystem finalStep(final AppCompatActivity context) {
        travelInfoReviewSystem = new TravelInfoReviewSystem(context) {
            @Override
            protected void onUploadTrip(final FrameLayoutBasedHerbuyStateView sender) {

                sender.render(MakeDummy.wrapContent(Atom.textView(context, "Uploading...")));
                Rest2.api().scheduleTrip(getData()).enqueue(new AppCallback<List<Trip>>() {
                    @Override
                    protected void onSuccess(Call<List<Trip>> call, final Response<List<Trip>> response) {
                        sender.render(MakeDummy.wrapContent(Atom.textView(context, "SUCCESS!!")));
                        Trip lastTripScheduled = response.body().get(0);
                        LocalSession.instance().setLastTripScheduled(lastTripScheduled);
                        context.finish();
                        GotoActivity.tripDetails(context, lastTripScheduled);
                        TripScheduledEvent.instance().notifyObservers(lastTripScheduled);
                    }

                    @Override
                    protected void onError(Call<List<Trip>> call, Throwable t) {
                        sender.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                            @Override
                            public void run() {
                                onUploadTrip(sender);
                            }
                        });
                    }
                });
            }
        };
        return travelInfoReviewSystem;
    }


    private ParamsForScheduleTrip getData() {
        ParamsForScheduleTrip paramsForScheduleTrip = new ParamsForScheduleTrip();
        paramsForScheduleTrip.setSessionId(LocalSession.instance().getId());
        paramsForScheduleTrip.setForUserId(LocalSession.instance().getUserId());

        SetDestinationInfo.where(paramsForScheduleTrip, dest);
        SetOriginInfo.where(paramsForScheduleTrip, origin);

        paramsForScheduleTrip.setVehicleModel(carModel);
        paramsForScheduleTrip.setVehicleRegNumber(carNumber);
        paramsForScheduleTrip.setMaxConcurrentTTMarriages(seatsAvailable);

        paramsForScheduleTrip.setFuelCharge(fuelCharges);

        paramsForScheduleTrip.setTripDate(travelDate);

        paramsForScheduleTrip.setTripTime(travelTime);

        paramsForScheduleTrip.getPassingViaLocations().addAll(passVia);

        return paramsForScheduleTrip;
    }

}
