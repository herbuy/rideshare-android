package client.ui.display.Trip.schedule_trip.p20210410;


import libraries.ObserverList;

/** receives and dispatches events related to the task of uploading a trip */
public class EventBusForTaskBrowsePotentialMatches {


    public static final ObserverList<Void> browsingEnded = new ObserverList<>();

    public static void clear() {

        browsingEnded.clear();

    }

}
