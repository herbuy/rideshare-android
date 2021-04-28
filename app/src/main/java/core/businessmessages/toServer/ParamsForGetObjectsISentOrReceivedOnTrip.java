package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForGetObjectsISentOrReceivedOnTrip extends BusinessMessageToServer {
    private String tripIdEquals;
    private String actorIdEquals;
    private String targetUserIdEquals;

    public String getTripIdEquals() {
        return tripIdEquals;
    }

    public void setTripIdEquals(String tripIdEquals) {
        this.tripIdEquals = tripIdEquals;
    }

    public String getActorIdEquals() {
        return actorIdEquals;
    }

    public void setActorIdEquals(String actorIdEquals) {
        this.actorIdEquals = actorIdEquals;
    }

    public String getTargetUserIdEquals() {
        return targetUserIdEquals;
    }

    public void setTargetUserIdEquals(String targetUserIdEquals) {
        this.targetUserIdEquals = targetUserIdEquals;
    }
}
