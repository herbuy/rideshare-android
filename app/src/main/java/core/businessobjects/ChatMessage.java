package core.businessobjects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ChatMessage {
    private String transactionId = ""; //identifies messages that were sent in the same batch/transaction
    private String messageId = "";
    private String messageType = "";
    private String text = "";

    private String fromUserId = "";
    private String fromUserName = "";
    private String fromUserProfilePic = "";

    private String toUserId = "";
    private String toUserName = "";
    private String toUserProfilePic = "";

    private String forUserId = "";

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromUserProfilePic() {
        return fromUserProfilePic;
    }

    public void setFromUserProfilePic(String fromUserProfilePic) {
        this.fromUserProfilePic = fromUserProfilePic;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getToUserProfilePic() {
        return toUserProfilePic;
    }

    public void setToUserProfilePic(String toUserProfilePic) {
        this.toUserProfilePic = toUserProfilePic;
    }

    public String getForUserId() {
        return forUserId;
    }

    public void setForUserId(String forUserId) {
        this.forUserId = forUserId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isForUser(String forUserId) {
        return
                this.forUserId != null
                        && forUserId != null
                        && !this.forUserId.trim().equalsIgnoreCase("")
                        && !forUserId.trim().equalsIgnoreCase("")
                        && this.forUserId.trim().equalsIgnoreCase(forUserId.trim());
    }

    public boolean isFromUser(String fromUserId) {
        return
                this.fromUserId != null
                        && fromUserId != null
                        && !this.fromUserId.trim().equalsIgnoreCase("")
                        && !fromUserId.trim().equalsIgnoreCase("")
                        && this.fromUserId.trim().equalsIgnoreCase(fromUserId.trim());
    }

    public boolean isToUser(String toUserId) {
        return
                this.toUserId != null
                        && toUserId != null
                        && !this.toUserId.trim().equalsIgnoreCase("")
                        && !toUserId.trim().equalsIgnoreCase("")
                        && this.toUserId.trim().equalsIgnoreCase(toUserId.trim());
    }

    /*
    //sender
    private String senderId;
    private String senderName;
    private String senderProfilePic;

    //target
    private String targetId;
    private String targetName;
    private String targetProfilePic;

    //subject
    private String messageId;
    private String messageType; //for starters, we can support text, contact, image, tt request reference
    private String text;

    //viewer
    private String viewerId;

    //status
    private String deliveryStatus; //pending, delivered, seen, failed;

    //dates
    private String dateSent; //recorded at the time the server receives it, but can be displayed in the client time
    private String timeSent;

    private String dateDelivered; //comes from delivery notifications from the client device
    private String timeDelivered;

    //correlation information
    private String correlationId; //the message to which it was replying, if any
    private String correlationContentType;
    private String correlationContent;

    //permissions:
    private String canReply;
    private String canDelete;*/


    public static class Type {
        public static final String TEXT_PLAIN = "TEXT_PLAIN";
        public static final String TRAVELLING = "TRAVELLING"; //sent when a trip is scheduled for a customer or business]

        private static final Set<String> getSet(){
            return new HashSet<>(Arrays.asList(new String[]{
                    TEXT_PLAIN,
                    TRAVELLING
            }));
        }

        public static boolean contains(String messageType) {
            return getSet().contains(messageType);
        }
    }
}
