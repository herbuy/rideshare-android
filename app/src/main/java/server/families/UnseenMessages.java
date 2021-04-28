package server.families;

import java.util.ArrayList;
import java.util.List;

import cache.FamilyMessageDatabase;
import core.businessobjects.FamilyMessage;
import libraries.data.LocalDatabase;

public class UnseenMessages {
    private List<FamilyMessage> messages = new ArrayList<>();

    private UnseenMessages() {
    }
    public static UnseenMessages where(String familyId, String forUserId){
        UnseenMessages instance = new UnseenMessages();
        instance.init(familyId,forUserId);
        return instance;
    }

    private void init(final String familyId, final String forUserId) {

        List<FamilyMessage> results = new FamilyMessageDatabase().select(new LocalDatabase.Where<FamilyMessage>() {
            @Override
            public boolean isTrue(FamilyMessage message) {
                return message.isToFamily(familyId)
                        && message.isForUserId(forUserId)
                        && message.isNotFromUser(forUserId)
                        && message.isNotSeen()
                        ;
            }
        });
        if(results != null){
            this.messages = results;

        }
    }

    public List<FamilyMessage> getMessages() {
        return messages;
    }

    public int getCount(){
        return messages.size();
    }
}
