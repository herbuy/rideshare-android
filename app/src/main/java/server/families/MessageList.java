package server.families;

import java.util.ArrayList;
import java.util.List;

import cache.FamilyMemberDatabase;
import cache.FamilyMessageDatabase;
import core.businessmessages.toServer.ParamsForGetFamilyMessages;
import core.businessobjects.FamilyMessage;
import core.businessobjects.FamilyMember;
import core.businessobjects.Family;
import core.businessobjects.Trip;
import core.businessobjects.User;
import libraries.IsTrue;
import libraries.data.LocalDatabase;
import server.Enrich;
import server.StoredProcedures;

public class MessageList {
    private List<FamilyMessage> messages = new ArrayList<>();

    private MessageList() {
    }

    public static MessageList where(ParamsForGetFamilyMessages params,User currentUser){
        MessageList instance = new MessageList();
        instance.init(params, currentUser);
        return instance;
    }

    private void init(final ParamsForGetFamilyMessages params, final User currentUser) {
        if(params.isSeen()){
            MarkMessagesAsSeen.where(currentUser.getUserId(), params.getFamilyId());
        }


        final Family family = StoredProcedures.getFamilyOrDie(params.getFamilyId(), "Invalid family Id");
        Enrich.family(family, currentUser);

        List<FamilyMessage> matchingMessages = new FamilyMessageDatabase().select(new LocalDatabase.Where<FamilyMessage>() {
            @Override
            public boolean isTrue(FamilyMessage object) {
                Enrich.familyMessage(object);
                return amAllowedToSeeMessage(object);
            }

            private boolean amAllowedToSeeMessage(FamilyMessage message) {
                return IsTrue.forAll(
                        message.isToFamily(params.getFamilyId()),
                        amMemberOfTheFamily(message),
                        message.isForUserId(params.getForUserId())
                );

            }

            private boolean amMemberOfTheFamily(FamilyMessage message) {
                return iHaveATripThatIsMemberOfFamily(params.getFamilyId());

            }

            private boolean iHaveATripThatIsMemberOfFamily(String familyId) {
                return new FamilyMemberDatabase().select(membersThatAreOfFamilyAndBelongToUser(familyId, currentUser.getUserId())).size() > 0;
            }

            private LocalDatabase.Where<FamilyMember> membersThatAreOfFamilyAndBelongToUser(final String familyId, final String userId) {
                return new LocalDatabase.Where<FamilyMember>() {
                    @Override
                    public boolean isTrue(FamilyMember member) {
                        Trip trip = StoredProcedures.getTripOrDie(member.getTripId(), "Invalid trip");
                        return member.isMemberOfFamily(familyId) && trip.isForUser(userId);
                    }
                };
            }


        });

        messages = matchingMessages;
    }

    public List<FamilyMessage> getMessages() {
        return messages;
    }
}
