package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;


public class ParamsForSendTTProposal extends BusinessMessageToServer {
    private String receivingTripId = "";
    private String responseType = Type.INTERESTED;
    private String sendingTripId;

    public String getReceivingTripId() {
        return receivingTripId;
    }

    public void setReceivingTripId(String receivingTripId) {
        this.receivingTripId = receivingTripId;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public void setResponseTypeToInterested(){
        setResponseType(Type.INTERESTED);
    }

    public void setResponseTypeToNotInterested(){
        setResponseType(Type.NOT_INTERESTED);
    }

    public void setSendingTripId(String sendingTripId) {
        this.sendingTripId = sendingTripId;
    }

    public String getSendingTripId() {
        return sendingTripId;
    }

    public static class Type{
        public static final String INTERESTED = "INTERESTED";
        public static final String NOT_INTERESTED = "NOT_INTERESTED";

    }
}
