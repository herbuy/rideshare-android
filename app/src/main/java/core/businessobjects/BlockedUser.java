package core.businessobjects;

public class BlockedUser {
    private String recordId = "";
    private String userIdBlocked = "";
    private String userIdThatBlocked = "";

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUserIdBlocked() {
        return userIdBlocked;
    }

    public void setUserIdBlocked(String userIdBlocked) {
        this.userIdBlocked = userIdBlocked;
    }

    public String getUserIdThatBlocked() {
        return userIdThatBlocked;
    }

    public void setUserIdThatBlocked(String userIdThatBlocked) {
        this.userIdThatBlocked = userIdThatBlocked;
    }

    public boolean isUserId(String blockedUserId) {
        return blockedUserId != null
                && !blockedUserId.trim().equalsIgnoreCase("")
                && this.userIdBlocked != null
                && !this.userIdBlocked.trim().equalsIgnoreCase("")
                && this.userIdBlocked.trim().equalsIgnoreCase(blockedUserId.trim())
                ;
    }

    public boolean isBlockedByUserId(String blockingUserId) {
        return blockingUserId != null
                && !blockingUserId.trim().equalsIgnoreCase("")
                && this.userIdThatBlocked != null
                && !this.userIdThatBlocked.trim().equalsIgnoreCase("")
                && this.userIdThatBlocked.trim().equalsIgnoreCase(blockingUserId.trim())
                ;
    }
}
