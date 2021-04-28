package server.families;

import cache.FamilyMessageDatabase;
import core.businessobjects.FamilyMessage;
import libraries.data.LocalDatabase;

public class MarkMessagesAsSeen {
    private MarkMessagesAsSeen() {
    }

    public static MarkMessagesAsSeen where(String viewerUserId, String familyId) {
        MarkMessagesAsSeen instance = new MarkMessagesAsSeen();
        instance.init(viewerUserId,familyId);
        return instance;
    }

    private void init(final String viewerUserId, final String familyId) {
        new FamilyMessageDatabase().forEach(new LocalDatabase.ForEach<FamilyMessage>() {
            @Override
            public void run(FamilyMessage message) {
                if(message.isNotFromUser(viewerUserId) && message.isToFamily(familyId)){
                    message.setStatus(FamilyMessage.Status.SEEN);
                    new FamilyMessageDatabase().save(message.getMessageId(),message);
                }
            }
        });
    }
}
