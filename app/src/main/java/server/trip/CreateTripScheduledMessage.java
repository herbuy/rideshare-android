package server.trip;

import java.util.UUID;

import core.businessobjects.TripMessage;

public class CreateTripScheduledMessage {

    public static TripMessage where(String toTripId, String scheduledTripId, String transactionId) {
        TripMessage tripMessage = new TripMessage();
        tripMessage.setMessageId(UUID.randomUUID().toString());
        tripMessage.setMessageType(TripMessage.Type.TRIP_SCHEDULED);
        tripMessage.setMessageText("");
        tripMessage.setSystemMessage(true);
        tripMessage.setToTripId(toTripId);
        tripMessage.setFromTripId(scheduledTripId);
        tripMessage.setTransactionId(transactionId);
        return tripMessage;
    }
}
