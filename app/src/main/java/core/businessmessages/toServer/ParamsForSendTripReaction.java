package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForSendTripReaction extends BusinessMessageToServer {
    private String tripId;
    private String reactionType;

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }
}
