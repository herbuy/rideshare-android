package server.trip;

import android.util.Log;

import cache.TripMessageDatabase;
import core.businessobjects.TripMessage;
import libraries.JsonEncoder;


public class AddTripScheduledMessage {
    public static void where(String toTripId, String scheduledTripId, String transactionId) {
        TripMessage message = CreateTripScheduledMessage.where(toTripId, scheduledTripId,transactionId);
        Log.d("NOTIFYING TRIP", JsonEncoder.encode(message));
        new TripMessageDatabase().save(message.getMessageId(),message);

    }
}
