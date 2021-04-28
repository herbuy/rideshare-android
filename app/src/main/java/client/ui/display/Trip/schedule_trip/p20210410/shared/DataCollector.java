package client.ui.display.Trip.schedule_trip.p20210410.shared;

import java.util.Locale;

import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_car_info.CarInfo;
import client.ui.display.Trip.schedule_trip.p20210410.microinteractions.change_time.DepartureTime;
import client.ui.display.Trip.schedule_trip.steps.SetDestinationInfo;
import client.ui.display.Trip.schedule_trip.steps.SetOriginInfo;
import core.businessmessages.toServer.ParamsForScheduleTrip;
import core.businessobjects.Location;
import libraries.HerbuyCalendar;
import libraries.ObserverList;

public class DataCollector {
    private ParamsForScheduleTrip params = new ParamsForScheduleTrip();

    public DataCollector() {

        EventBusForTaskUploadTrip.destinationChanged.add(new ObserverList.Observer<Location>() {
            @Override
            public void notifyEvent(Location eventArgs) {
                SetDestinationInfo.where(params,eventArgs);
            }
        });

        EventBusForTaskUploadTrip.originChanged.add(new ObserverList.Observer<Location>() {
            @Override
            public void notifyEvent(Location eventArgs) {
                SetOriginInfo.where(params,eventArgs);
            }
        });

        EventBusForTaskUploadTrip.travelDateChanged.add(new ObserverList.Observer<HerbuyCalendar>() {
            @Override
            public void notifyEvent(HerbuyCalendar travelDate) {
                params.setTripDate(travelDate == null ? "" : makeDateString(travelDate));
            }
        });
        EventBusForTaskUploadTrip.departureTimeChanged.add(new ObserverList.Observer<DepartureTime>() {
            @Override
            public void notifyEvent(DepartureTime travelTime) {
                params.setTripTime(travelTime == null ? "" : travelTime.toTime());
            }
        });

        EventBusForTaskUploadTrip.carInfoChanged.add(new ObserverList.Observer<CarInfo>() {
            @Override
            public void notifyEvent(CarInfo eventArgs) {
                if(eventArgs == null){
                    eventArgs = new CarInfo();
                }
                params.setVehicleModel(eventArgs.carModel == null ? "" : eventArgs.carModel);
                params.setVehicleRegNumber(eventArgs.carRegNumber == null ? "" : eventArgs.carRegNumber);


            }
        });

        EventBusForTaskUploadTrip.seatCountChanged.add(new ObserverList.Observer<Integer>() {
            @Override
            public void notifyEvent(Integer eventArgs) {
                params.setMaxConcurrentTTMarriages(eventArgs == null ? 1 : eventArgs);

            }
        });
        EventBusForTaskUploadTrip.fuelContributionChanged.add(new ObserverList.Observer<Integer>() {
            @Override
            public void notifyEvent(Integer fuelCharges) {
                params.setFuelCharge(fuelCharges == null ? 0 : fuelCharges);
            }
        });

        //paramsForScheduleTrip.getPassingViaLocations().addAll(route);
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

    public ParamsForScheduleTrip getParams() {
        return params;
    }
}
