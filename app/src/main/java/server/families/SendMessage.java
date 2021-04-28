package server.families;

import java.util.UUID;

import cache.FamilyMemberDatabase;
import cache.FamilyMessageDatabase;
import core.businessmessages.toServer.ParamsForSendFamilyMessage;
import core.businessobjects.FamilyMessage;
import core.businessobjects.FamilyMember;
import libraries.data.LocalDatabase;
import libraries.throwException;
import server.trip.TripDecorator;
import server.users.BackEndUser;

public class SendMessage {
    private SendMessage() {
    }
    public static SendMessage where(ParamsForSendFamilyMessage params){
        SendMessage sendMessage = new SendMessage();
        sendMessage.init(params);
        return sendMessage;

    }

    private void init(final ParamsForSendFamilyMessage params) {
        BackEndUser sender = BackEndUser.fromUserId(params.getFromUserId());
        throwException.ifNot(sender.isMemberOfFamily(params.getToFamilyId()), "Only family members can send messages to this family");

        final String transactionId = UUID.randomUUID().toString();
        new FamilyMemberDatabase().forEach(new LocalDatabase.ForEach<FamilyMember>() {
            @Override
            public void run(FamilyMember member) {
                if (member.isMemberOfFamily(params.getToFamilyId())) {
                    sendMessageToMember(member, params, transactionId);
                }
            }
        });
    }

    public FamilyMessage sentMessage = new FamilyMessage();

    private void sendMessageToMember(FamilyMember member, ParamsForSendFamilyMessage params, String transactionId) {
        String forUserId = TripDecorator.fromTripId(member.getTripId()).getForUserId();
        BackEndFamilyMessage message = BackEndFamilyMessage.fromParams(params,forUserId, transactionId);
        new FamilyMessageDatabase().save(message.getMessageId(),(FamilyMessage)message);
        if(message.isForUserId(message.getFromUserId())){
            sentMessage = message;
        }
    }

    public FamilyMessage getSentMessage() {
        return sentMessage;
    }
}
