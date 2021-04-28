package core.businessobjects;

import java.util.Arrays;
import java.util.List;

public class TripReaction extends MessageToTripActor {
    private String reactionId;
    private String reactionType;
    private String content; //i realized we can cater for comments or custom reactions here my allowing to set content.

    public String getReactionId() {
        return reactionId;
    }

    public void setReactionId(String reactionId) {
        this.reactionId = reactionId;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }

    public static class Type{
        public static final String LIKE = "LIKE";
        public static final String SAFE_JOURNEY = "SAFE_JOURNEY";
        public static final String WELCOME = "WELCOME";

        //we might also consider these reactions [subject to consideration
        public static final String COMMENT = "WELCOME";
        public static final String INQUIRY = "WELCOME";
        public static final String REVIEW = "WELCOME";
        public static final String TESTIMONIAL = "WELCOME";
        public static final String STATUS_UPDATE = "WELCOME";
        //can have concept of subtype of reaction


        public static List<String> getList(){
            return Arrays.asList(
                    LIKE, SAFE_JOURNEY,WELCOME
            ) ;

        }

    }
}
