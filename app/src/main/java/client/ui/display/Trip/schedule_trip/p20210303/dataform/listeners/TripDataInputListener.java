package client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners;

import core.businessmessages.toServer.ParamsForScheduleTrip;
import core.businessobjects.Trip;

public abstract class TripDataInputListener implements
        DestinationInputListener,
        OriginInputListener,
        RouteInputListener,
        DateInputListener,
        TimeInputListener,
        DrivingStatusInputListener,
        CarModelInputListener,
        CarRegNumInputListener,
        SeatsAvailableInputListener,
        FuelChargeInputListener,
        NavigationListener
{
    public abstract ParamsForScheduleTrip getParamsForScheduleTrip();

    public abstract void tripScheduled(Trip trip);

    public abstract boolean readyToUpload();

    public abstract void setChooseRouteRequired(Boolean isChooseRouteRequired);
}
