package server.trip;

import cache.TripDatabase;
import cache.TripMessageDatabase;
import core.businessobjects.Trip;
import core.businessobjects.TripMessage;
import libraries.data.LocalDatabase;

public class MarkTripMessagesAsSeen {
    public static void where(final String tripId, final String userIdMarking) {
        if(tripId != null && !tripId.trim().equalsIgnoreCase("")){

            new TripMessageDatabase().forEach(new LocalDatabase.ForEach<TripMessage>() {
                @Override
                public void run(TripMessage message) {

                    if(message.isToTripId(tripId.trim())){
                        //get more info about the trip
                        Trip trip = new TripDatabase().getByKey(tripId.trim());
                        if(trip != null && trip.isForUser(userIdMarking)){
                            message.setSeen(true);
                            new TripMessageDatabase().save(message.getMessageId(),message);
                        }

                    }

                }
            });
        }

    }
}
