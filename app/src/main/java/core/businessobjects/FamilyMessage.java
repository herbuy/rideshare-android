package core.businessobjects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FamilyMessage {

    private String messageId = "";
    private String fromUserId = ""; //the person who sent the message. Must be a member of the family or group [even if group of two]
    private String toFamilyId = ""; //the set of family members who are meant to receive a copy of the message
    private String forUserId = ""; //the person authorized to view this particular instance of the message
    private String messageType = "";
    private String messageText = "";
    private boolean systemMessage = false;
    private String correlationId = "";
    private String status = "";
    private String transactionId = ""; //identifies a group of messages sent in one activity, e.g for deleting

    private String fromUserName = "";
    private String fromUserProfilePic = "";
    private long timestampCreatedMillis;



    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setToFamilyId(String toFamilyId) {
        this.toFamilyId = toFamilyId;
    }

    public String getToFamilyId() {
        return toFamilyId;
    }

    public void setMessageType(String messageType) {
        if(Type.getSupportedTypes().contains(messageType)){
            this.messageType = messageType;
        }

    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public boolean isFromUser(String userId) {
        return userId != null
                && !userId.trim().equalsIgnoreCase("")
                && this.fromUserId != null
                && !this.fromUserId.trim().equalsIgnoreCase("")
                && this.fromUserId.trim().equalsIgnoreCase(userId.trim());
    }

    public boolean isNotFromUser(String userId){
        return !isFromUser(userId);
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getForUserId() {
        return forUserId;
    }

    public void setForUserId(String forUserId) {
        this.forUserId = forUserId;
    }

    public boolean isSending() {
        return status != null && status.trim().equalsIgnoreCase(Status.SENDING);
    }
    public boolean isSent() {
        return status != null && status.trim().equalsIgnoreCase(Status.SENT);
    }
    public boolean isFailed() {
        return status != null && status.trim().equalsIgnoreCase(Status.FAILED);
    }
    public boolean isDelivered() {
        return status != null && status.trim().equalsIgnoreCase(Status.DELIVERED);
    }
    public boolean isSeen() {
        return status != null && status.trim().equalsIgnoreCase(Status.SEEN);
    }
    public boolean isNotSeen(){
        return !isSeen();
    }

    public void setStatusToFailed() {
        status = Status.FAILED;
    }
    public void setStatusToSent() {
        status = Status.SENT;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public boolean isSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(boolean systemMessage) {
        this.systemMessage = systemMessage;
    }

    public void setFromUserProfilePic(String fromUserProfilePic) {
        this.fromUserProfilePic = fromUserProfilePic;
    }

    public String getFromUserProfilePic() {
        return fromUserProfilePic;
    }

    public void setTimestampCreatedMillis(long timestampCreatedMillis) {
        this.timestampCreatedMillis = timestampCreatedMillis;
    }

    public long getTimestampCreatedMillis() {
        return timestampCreatedMillis;
    }

    public boolean isToFamily(String familyId) {
        return familyId != null
                && !familyId.trim().equalsIgnoreCase("")
                && toFamilyId != null
                && !toFamilyId.trim().equalsIgnoreCase("")
                && toFamilyId.trim().equalsIgnoreCase(familyId.trim())
                ;
    }

    public boolean isForUserId(String forUserId) {
        return forUserId != null
                && !forUserId.trim().equalsIgnoreCase("")
                && this.forUserId != null
                && !this.forUserId.trim().equalsIgnoreCase("")
                && this.forUserId.trim().equalsIgnoreCase(forUserId.trim())
                ;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public static class Type{
        public static final String TEXT_PLAIN = "TEXT_PLAIN";

        private static Set<String> getSupportedTypes(){
            return new HashSet<>(Arrays.asList(new String[]{
                    TEXT_PLAIN
            }));
        }

        public static boolean contains(String messageType) {
            return getSupportedTypes().contains(messageType);
        }
    }

    public class Status {
        public static final String SENDING = "SENDING";
        public static final String SENT = "SENT";
        public static final String FAILED = "FAILED";
        public static final String DELIVERED = "DELIVERED";
        public static final String SEEN = "SEEN";
    }
}
