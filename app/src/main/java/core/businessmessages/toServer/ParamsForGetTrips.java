package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForGetTrips extends BusinessMessageToServer{
    private String tripId = ""; //useful for showing trip details
    private String isForUserId = ""; //if specified, will return only trips of that user
    private String matchingRoutesAndTravelDatesOfUserId = ""; //if user id specified, will return only trips taking the specified user's route and date e.g those taking my route. User could have multiple routes scheduled.
    private String notForUserId = ""; //if user Id specified, will exclude trips of that user e.g i may not want to view my trips

    private String excludeThoseMatchedByUserId = ""; //i may not want to see trips am already married to if am looking for potential travel mates
    private String excludeThoseProposedToByUserId = ""; // i may not want to see trips i have already proposed to, but only those i havent so that i can propose
    private String excludeSameSexAsUserId;
    private String excludeIfReachedLimitForUserId;
    private String travelDateAfterDate;
    private String whereProposerCanBeUserId;
    private String acceptingProposalsFromTripId = "";
    private String tripIdMessagesSeen = "";

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setIsForUserId(String isForUserId) {
        this.isForUserId = isForUserId;
    }

    public String getIsForUserId() {
        return isForUserId;
    }

    public void setMatchingRoutesAndTravelDatesOfUserId(String matchingRoutesAndTravelDatesOfUserId) {
        if(
                matchingRoutesAndTravelDatesOfUserId != null &&
                (
                        matchingRoutesAndTravelDatesOfUserId.trim().equalsIgnoreCase("true")
                        || matchingRoutesAndTravelDatesOfUserId.trim().equalsIgnoreCase("false")
                )
                ){
            this.matchingRoutesAndTravelDatesOfUserId = matchingRoutesAndTravelDatesOfUserId.trim();
        }

    }

    public String getMatchingRoutesAndTravelDatesOfUserId() {
        return matchingRoutesAndTravelDatesOfUserId;
    }

    public String getNotForUserId() {
        return notForUserId;
    }

    public void setNotForUserId(String notForUserId) {
        this.notForUserId = notForUserId;
    }

    public void setExcludeThoseMatchedByUserId(String excludeThoseMatchedByUserId) {
        this.excludeThoseMatchedByUserId = excludeThoseMatchedByUserId;
    }

    public String getExcludeThoseMatchedByUserId() {
        return excludeThoseMatchedByUserId;
    }

    public void setExcludeThoseProposedToByUserId(String excludeThoseProposedToByUserId) {
        this.excludeThoseProposedToByUserId = excludeThoseProposedToByUserId;
    }

    public String getExcludeThoseProposedToByUserId() {
        return excludeThoseProposedToByUserId;
    }

    public void setExcludeSameSexAsUserId(String excludeSameSexAsUserId) {
        this.excludeSameSexAsUserId = excludeSameSexAsUserId;
    }

    public String getExcludeSameSexAsUserId() {
        return excludeSameSexAsUserId;
    }

    public void setExcludeIfReachedLimitForUserId(String excludeIfReachedLimitForUserId) {
        this.excludeIfReachedLimitForUserId = excludeIfReachedLimitForUserId;
    }

    public String getExcludeIfReachedLimitForUserId() {
        return excludeIfReachedLimitForUserId;
    }

    public void setTravelDateAfterDate(String travelDateAfterDate) {
        this.travelDateAfterDate = travelDateAfterDate;
    }

    public String getTravelDateAfterDate() {
        return travelDateAfterDate;
    }

    public void setWhereProposerCanBeUserId(String whereProposerCanBeUserId) {
        this.whereProposerCanBeUserId = whereProposerCanBeUserId;
    }

    public String getWhereProposerCanBeUserId() {
        return whereProposerCanBeUserId;
    }

    public void setAcceptingProposalsFromTripId(String acceptingProposalsFromTripId) {
        this.acceptingProposalsFromTripId = acceptingProposalsFromTripId;
    }

    public String getAcceptingProposalsFromTripId() {
        return acceptingProposalsFromTripId;
    }

    public void setTripIdMessagesSeen(String tripIdMessagesSeen) {
        this.tripIdMessagesSeen = tripIdMessagesSeen;
    }

    public String getTripIdMessagesSeen() {
        return tripIdMessagesSeen;
    }
}
