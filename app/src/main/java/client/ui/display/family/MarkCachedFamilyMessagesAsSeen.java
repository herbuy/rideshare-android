package client.ui.display.family;

import core.businessobjects.FamilyMessage;

public class MarkCachedFamilyMessagesAsSeen {
    synchronized public static void where(String familyId) {
        if (familyExists(familyId)) {
            MessageCacheForFamily messageCacheForFamily = familyGetByKey(familyId);
            for (FamilyMessage message : messageCacheForFamily.getList()) {
                message.setStatus(FamilyMessage.Status.SEEN);
            }
            new FamilyMessageCaches().save(messageCacheForFamily.getFamilyId(),messageCacheForFamily);

        }

    }

    private static boolean familyExists(String familyId) {
        return new FamilyMessageCaches().hasKey(familyId);
    }
    private static MessageCacheForFamily familyGetByKey(String toFamilyId) {
        return new FamilyMessageCaches().getByKey(toFamilyId);
    }
}
