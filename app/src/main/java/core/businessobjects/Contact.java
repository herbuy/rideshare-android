package core.businessobjects;

public class Contact {
    private String uniqueId = ""; //a unique id to identify this record, for purposes like updating
    private String userId = ""; //the user who owns this contact [later, we shall rename to ForUserId
    private String contactId = "";
    private String contactName = "";
    private String contactProfilePic = "";
    private String contactDisplayName = ""; //the name the user uses to identify the user [different from wha the user calls himself]
    private long timestampLastModifiedInSecs; //used, among other things, to show contacts with most recent messages first

    //these parameters are used when displaying contacts to show a bit of the last message
    private String lastMessageType = "";
    private String lastMessageText = "";
    private String lastMessageSenderId = "";

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactProfilePic() {
        return contactProfilePic;
    }

    public void setContactProfilePic(String contactProfilePic) {
        this.contactProfilePic = contactProfilePic;
    }

    public String getContactDisplayName() {
        return contactDisplayName;
    }

    public void setContactDisplayName(String contactDisplayName) {
        this.contactDisplayName = contactDisplayName;
    }

    public long getTimestampLastModifiedInSecs() {
        return timestampLastModifiedInSecs;
    }

    public void setTimestampLastModifiedInSecs(long timestampLastModifiedInSecs) {
        this.timestampLastModifiedInSecs = timestampLastModifiedInSecs;
    }

    public String getLastMessageType() {
        return lastMessageType;
    }

    public void setLastMessageType(String lastMessageType) {
        if(!ChatMessage.Type.contains(lastMessageType)){
            throw new RuntimeException("Invalid message type");
        }
        this.lastMessageType = lastMessageType;
    }

    public String getLastMessageText() {
        return lastMessageText;
    }

    public void setLastMessageText(String lastMessageText) {
        this.lastMessageText = lastMessageText;
    }

    public String getLastMessageSenderId() {
        return lastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }

    public boolean isUser(String userId) {
        return
                this.contactId != null
                        && userId != null
                        && !this.contactId.trim().equalsIgnoreCase("")
                        && !userId.trim().equalsIgnoreCase("")
                        && this.contactId.trim().equalsIgnoreCase(userId.trim());
    }

    public boolean isForUser(String userId) {
        return
                this.userId != null
                        && userId != null
                        && !this.userId.trim().equalsIgnoreCase("")
                        && !userId.trim().equalsIgnoreCase("")
                        && this.userId.trim().equalsIgnoreCase(userId.trim());
    }






}
