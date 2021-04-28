package client.ui;

//information that accompanies an intent to shed more light on the intent
public class IntentExtras {
    public static String FEEDBACK;
    public static String chatParameters = "chatParameters";
    public static String tripId = "tripId";
    public static String location = "location";
    public static String tripDetails = "tripDetails";
    public static String user = "user";
    public static String selectedLocation = "selectedLocation";
    public static String userLoginDetails = "userLoginDetails";
    public static String family = "family";
    public static final String formDisplayParameters = "formDisplayParameters";
    public static String title = "";
    public static String data = "";

    public static class ChatParameters{
        public String targetUserId = "";
        public String targetUserName = "";
        public String targetUserProfilePic = "";

        public String getTargetUserId() {
            return targetUserId;
        }

        public void setTargetUserId(String targetUserId) {
            this.targetUserId = targetUserId;
        }

        public String getTargetUserName() {
            return targetUserName;
        }

        public void setTargetUserName(String targetUserName) {
            this.targetUserName = targetUserName;
        }

        public String getTargetUserProfilePic() {
            return targetUserProfilePic;
        }

        public void setTargetUserProfilePic(String targetUserProfilePic) {
            this.targetUserProfilePic = targetUserProfilePic;
        }
    }
}
