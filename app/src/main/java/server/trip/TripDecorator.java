package server.trip;

import android.util.Log;;

import cache.TripDatabase;
import core.businessobjects.Trip;
import libraries.JsonEncoder;
import libraries.IsTrue;
import libraries.HerbuyCalendar;
import server.StoredProcedures;
import server.families.FamilyIdOfTrip;
import server.families.FamilyMatesOfTrip;
import server.proposals.ProposalsFromTripToTrip;

public class TripDecorator extends Trip {
    private boolean found = false;
    private TripDecorator() {
    }
    public static TripDecorator fromTripId(String tripId){
        Trip trip = new TripDatabase().getByKey(tripId);
        return fromTrip(trip);
    }

    public static TripDecorator fromTrip(Trip trip) {
        if(trip != null){
            TripDecorator instance = JsonEncoder.decode(JsonEncoder.encode(trip), TripDecorator.class);
            instance.found = true;
            return instance;
        }
        return new TripDecorator();
    }

    public boolean isFound() {
        return found;
    }

    public boolean isAcceptingProposalsFromTrip(String tripId) {

        try{
            Trip trip = StoredProcedures.getTripOrDie(tripId, "Trip does not exist");
            return isAcceptingProposalsFromTrip(trip);
        }
        catch (Throwable t){
            Log.e(this.getClass().getSimpleName(),t.getMessage() );
            return false;
        }
    }
    public boolean isAcceptingProposalsFromTrip(Trip trip){
        TripDecorator tripDecorator = JsonEncoder.decode(JsonEncoder.encode(trip),TripDecorator.class);
        return isAcceptingProposalsFromTrip(tripDecorator);

    }

    public boolean isAcceptingProposalsFromTrip(TripDecorator proposingTrip){

        //todo: using is driver or is passenger and on same date will not work coz even though passenger may see driver and propose, driver will not be able to see passenger's proposal. So better to just leave date unconsidered so both can view
        return IsTrue.forAll(
                isNotForSameUser(proposingTrip),
                isFromSameOrigin(proposingTrip),
                isToSameDestination(proposingTrip),
                //a driver trip can be on a different date, but a passenger trip must be on same date
                //(isDriver() || isPassenger() && isOnSameDate(proposingTrip)),
                //isOnSameDate(proposingTrip),
                isNotSameSex(proposingTrip),
                isNotYetReceivedProposalFromTripId(proposingTrip.getTripId()),

                isNotTurnedDownTripId(proposingTrip.getTripId()),
                isNotTurnedDownByTripId(proposingTrip.getTripId()),


                isNotBlockedByUser(proposingTrip.getForUserId()),
                isNotBlockedUser(proposingTrip.getForUserId()),

                isNotFull(),
                proposingTrip.isNotFull()

                //isUpComing(),
                //proposingTrip.isUpComing()

        );
    }

    private boolean isUpComing() {

        try{
            HerbuyCalendar herbuyCalendar = HerbuyCalendar.parse(getTripDate());
            return !herbuyCalendar.beforeToday();
        }
        catch (Throwable t){
            Log.e(TripDecorator.class.getSimpleName(),t.getMessage());
            return false;
        }
    }

    private boolean isFull() {
        return FamilyMatesOfTrip.where(getTripId()).count() >= getMaxConcurrentTTMarriages();
    }
    public boolean isNotFull(){
        return !isFull();
    }

    private boolean isFromSameFamily(final String tripId) {
        return FamilyIdOfTrip.where(tripId).isSameAsFamilyIdOfTrip(this.getTripId());
    }

    public boolean isBlockedByUser(String byUserId) {
        return BlockedUsers.where(this.getForUserId(),byUserId).isFound();
    }

    public boolean isNotBlockedByUser(String byUserId) {
        return !isBlockedByUser(byUserId);
    }

    public boolean isBlockedUser(String userId) {
        return BlockedUsers.where(userId,this.getForUserId()).isFound();
    }

    public boolean isNotBlockedUser(String userId) {
        return !isBlockedUser(userId);
    }

    public boolean isNotTurnedDownTripId(String tripIdTurnedDown) {
        return !isTurnedDownTripId(tripIdTurnedDown);
    }
    public boolean isNotTurnedDownByTripId(String tripIdTurnedDown) {
        return !isTurnedDownByTripId(tripIdTurnedDown);
    }

    public boolean isTurnedDownTripId(String tripIdTurnedDown) {
        TurnedDownTrips turnedDownTrips =  TurnedDownTrips.where(tripIdTurnedDown, this.getTripId());
        return turnedDownTrips.isFound();
    }

    public boolean isTurnedDownByTripId(String rejectingTripId) {
        TurnedDownTrips turnedDownTrips =  TurnedDownTrips.where(this.getTripId(),rejectingTripId);
        return turnedDownTrips.isFound();
    }

    //we only need the trip id, not need to pass in entire trip coz then we increased instances of dependency on trip class
    public boolean isAlreadyReceivedProposalFromTripId(String fromTripId) {
        ProposalsFromTripToTrip proposals = new ProposalsFromTripToTrip(
                fromTripId,getTripId()
        );
        return proposals.isFound();
    }
    public boolean isNotYetReceivedProposalFromTripId(String fromTripId){
        return !isAlreadyReceivedProposalFromTripId(fromTripId);
    }
}
