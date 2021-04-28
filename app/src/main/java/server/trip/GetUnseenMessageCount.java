package server.trip;

import java.util.List;

import cache.TripMessageDatabase;
import core.businessobjects.TripMessage;
import libraries.data.LocalDatabase;

public class GetUnseenMessageCount {
    public static int where(final String tripId) {
        List<TripMessage> unseenMessages = new TripMessageDatabase().select(new LocalDatabase.Where<TripMessage>() {
            @Override
            public boolean isTrue(TripMessage message) {
                return message.isToTripId(tripId) && message.isNotSeen();
            }
        });

        return unseenMessages != null ? unseenMessages.size() : 0;
    }
}
