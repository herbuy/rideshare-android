package core.businessobjects;

import java.util.HashMap;

import java.util.Map;

public class NotificationMessage {

    private String notificationType = "";
    public final Map<String,String> data = new HashMap<>();
    private String senderName = "";
    private String senderMessage = "";
    private int senderNumericId;
    private String senderId = "";
    private String senderProfilePic = "";

    //applicable to messages like family message seen.
    //they are not displayed in the notification drawer
    //but all messages in the cache for that family
    //should be updated to mark them as seen
    //and if in foreground, all messages in that family shd also be marked as seen
    //shd be marked as seen in add
    private boolean invisibleInDrawer = false;

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Map<String, String> getData() {
        return data;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public void setSenderMessage(String senderMessage) {
        this.senderMessage = senderMessage;
    }


    public Integer getSenderNumericId() {
        return senderNumericId;
    }

    public void setSenderNumericId(int senderNumericId) {
        this.senderNumericId = senderNumericId;
    }

    public String getSenderProfilePic() {
        return senderProfilePic;
    }

    public void setSenderProfilePic(String senderProfilePic) {
        this.senderProfilePic = senderProfilePic;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public boolean isType(String messageType) {
        if(messageType == null){
            return false;
        }
        if(messageType.trim().equalsIgnoreCase("")){
            return false;
        }
        if(this.notificationType == null){
            return false;
        }
        if(this.notificationType.trim().equalsIgnoreCase("")){
            return false;
        }
        if(! this.notificationType.trim().equalsIgnoreCase(messageType.trim())){
            return false;
        }
        return true;
    }

    public static class Type {

        public static final String POTENTIAL_MATCH_FOUND = "POTENTIAL_MATCH_FOUND";
        public static final String MATCH_FOUND = "MATCH_FOUND";
        public static final String FAMILY_MESSAGE = "FAMILY_MESSAGE";
        public static String FAMILY_MESSAGES_SEEN = "FAMILY_MESSAGES_SEEN";
        public static String PROFILE_PHOTO_CHANGED = "PROFILE_PHOTO_CHANGED";
    }

    public boolean isInvisibleInDrawer() {
        return invisibleInDrawer;
    }

    public void setIsInvisibleInDrawer(boolean isInvisibleInDrawer) {
        this.invisibleInDrawer = isInvisibleInDrawer;
    }




}
