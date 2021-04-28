package server.families;

import java.util.UUID;

import cache.FamilyMessageDatabase;
import core.businessmessages.toServer.ParamsForSendFamilyMessage;
import core.businessobjects.FamilyMessage;
import libraries.JsonEncoder;
import libraries.SecondsSinceEpoch;

public class BackEndFamilyMessage extends FamilyMessage{
    private BackEndFamilyMessage() {
    }
    public static BackEndFamilyMessage fromParams(ParamsForSendFamilyMessage params, String forUserId, String transactionId){
        FamilyMessage message = new FamilyMessage();
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageType(params.getMessageType());
        message.setMessageText(params.getMessageText());
        message.setFromUserId(params.getFromUserId());
        message.setToFamilyId(params.getToFamilyId());
        message.setForUserId(forUserId);
        message.setStatus(FamilyMessage.Status.SENT); //server messages have a status of sent by default
        message.setCorrelationId(params.getCorrelationId());
        message.setTimestampCreatedMillis(SecondsSinceEpoch.get());
        message.setTransactionId(transactionId);

        return fromMessage(message);
    }

    public static BackEndFamilyMessage fromMessageId(String messageId){
        FamilyMessage message = new FamilyMessageDatabase().getByKey(messageId);
        if(message != null){
            return fromMessage(message);
        }
        return new BackEndFamilyMessage();
    }

    private static BackEndFamilyMessage fromMessage(FamilyMessage message) {
        return JsonEncoder.decode(JsonEncoder.encode(message),BackEndFamilyMessage.class);
    }
}
