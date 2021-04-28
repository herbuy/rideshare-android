package server.backendobjects;

import android.util.Log;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import cache.FamilyDatabase;
import cache.FamilyMemberDatabase;
import cache.ProposalDatabase;
import core.businessmessages.toServer.ParamsForSendTTProposal;
import core.businessobjects.FamilyMember;
import core.businessobjects.Family;
import core.businessobjects.Proposal;
import core.businessobjects.Trip;
import core.businessobjects.User;
import libraries.JsonEncoder;
import libraries.IsTrue;
import libraries.data.LocalDatabase;
import libraries.throwException;
import server.DateToday;
import server.Enrich;
import server.StoredProcedures;
import server.proposals.ResultsForAddMutualProposal;
import server.trip.TurnDownTripResult;

public class RespondToTrip {

    User me;
    Trip receivingParty;
    Trip sendingParty;
    ParamsForSendTTProposal requestParameters;

    private RespondToTrip(){}


    public static List<Proposal>  where(ParamsForSendTTProposal requestParameters) {
        RespondToTrip instance = new RespondToTrip();
        instance.requestParameters = requestParameters;
        instance.me = StoredProcedures.getCurrentUserOrThrowException(requestParameters.getSessionId());
        instance.receivingParty = StoredProcedures.getBackEndTripOrDie(requestParameters.getReceivingTripId(), "Recieving trip does not exist");
        instance.sendingParty = StoredProcedures.getBackEndTripOrDie(requestParameters.getSendingTripId(),"Sending trip does not exist");
        Enrich.trip(instance.sendingParty, instance.me);
        Enrich.trip(instance.receivingParty, instance.me);

        if(!instance.sendingParty.isForUser(instance.me.getUserId())){
            throw new RuntimeException("Sending trip must belong to proposer");
        }

        if(requestParameters.getResponseType().equalsIgnoreCase(ParamsForSendTTProposal.Type.INTERESTED)){
            return instance.interested();
        }
        else{
            return instance.notInterested();
        }


    }

    private List<Proposal> notInterested() {
         TurnDownTripResult result = TurnDownTripResult.where(sendingParty.getTripId(), receivingParty.getTripId());
        if(result.isSuccess()){
            return Collections.singletonList(new Proposal());
        }
        throw new RuntimeException(result.getError());
    }

    private List<Proposal> interested() {

        //temporary, to undo effects of last operation

        Log.d("Families", new FamilyDatabase().selectAll().size() + "");
        Log.d("Family Members", new FamilyMemberDatabase().selectAll().size() + "");
        Log.d("Tot. Proposals",new ProposalDatabase().selectAll().size()+"");



        String error = getValidationError();
        throwException.ifNotNullOrEmpty(error, error);

        if(receivedAProposal()){
            //throwException.saying("received proposal");
            Proposal proposal = propose();
            matchSenderAndReceiver();
            return Collections.singletonList(proposal);
        }
        else{
            //throwException.saying("neither sent no received proposal");
            Proposal proposal = propose();
            return Collections.singletonList(proposal);
        }


    }

    private boolean receivedAProposal() {
        List<Proposal> receivedProposals = getReceivedProposals();
        return receivedProposals.size() > 0;
    }

    private boolean sentAProposal() {
        List<Proposal> sentProposals = getSentProposals();
        return sentProposals.size() > 0;
    }

    private Proposal propose() {
        Proposal proposal = createNewProposal();
        new ProposalDatabase().save(proposal.getProposalId(), proposal);
        return proposal;
    }

    private String getValidationError() {
        try{
            //Log.d("SENDER", sendingParty.getActorName());
            //Log.d("RECEIVER", receivingParty.getActorName());

            //the order of applying these policies matters in providing informative messages
            applySamePartyPolicy();
            applySameSexPolicy();
            applyDuplicateMatchAttemptPolicy();
            applyMaritalLimitPolicy();
            applyDoubleProposePolicy();


            return "";
        }
        catch (Throwable t){
            return t.getMessage();
        }
    }

    private void applyDoubleProposePolicy() {
        throwException.ifTrue(sentAProposal(), "Interest already noted");
    }

    private static class DriverOfTheTwoParties {
        private String driverTripId = "";
        private boolean notFound = false;

        public DriverOfTheTwoParties(Trip sendingParty, Trip receivingParty) {
            if(sendingParty.isDriver()){
                driverTripId = sendingParty.getTripId();
            }
            else if(receivingParty.isDriver()){
                driverTripId = receivingParty.getTripId();
            }
            else{
                notFound = true;
            }
        }

        public String getDriverTripId() {
            return driverTripId;
        }

        public boolean isNotFound() {
            return notFound;
        }
    }

    private static class DriverFamily {

        private String familyId = "";
        private boolean notFound = false;
        public DriverFamily(final String driverTripId) {

            List<Family> matchingFamilies = new FamilyDatabase().select(new LocalDatabase.Where<Family>() {
                @Override
                public boolean isTrue(Family family) {
                    return family.driverIsTripId(driverTripId);
                }
            });
            if(matchingFamilies.size() > 0){
                familyId = matchingFamilies.get(0).getFamilyId();
            }
            else{
                notFound = true;
            }
        }

        public String getFamilyId() {
            return familyId;
        }

        public boolean isNotFound() {
            return notFound;
        }

        public boolean isFound() {
            return !notFound;
        }
    }

    private static class ResultForCreateNewFamilyForDriver{

        private final String familyId;

        public ResultForCreateNewFamilyForDriver(String driverTripId) {

            Family family = createFamily(driverTripId);
            new FamilyDatabase().save(family.getFamilyId(),family);
            this.familyId = family.getFamilyId();
        }

        private Family createFamily(String driverTripId) {
            Family driverFamily = new Family();
            driverFamily.setFamilyId(UUID.randomUUID().toString());
            driverFamily.setDriverTripId(driverTripId);
            return driverFamily;
        }

        public String getFamilyId() {
            return familyId;
        }
    }

    private static class ResultForMemberExists{

        private boolean isTrue = false;

        public ResultForMemberExists(final String tripId, final String familyId) {
            List<FamilyMember> matchingMembers = new FamilyMemberDatabase().select(new LocalDatabase.Where<FamilyMember>() {
                @Override
                public boolean isTrue(FamilyMember familyMember) {
                    return familyMember.isTripId(tripId) && familyMember.isMemberOfFamily(familyId);
                }
            });
            if(matchingMembers != null && matchingMembers.size() > 0){
                isTrue = true;
            }

        }

        public boolean isTrue() {
            return isTrue;
        }

        public boolean isNotTrue() {
            return !isTrue;
        }
    }

    private static class ResultForAddMemberToFamilyIfNotExist {

        public ResultForAddMemberToFamilyIfNotExist(String driverFamilyId, Trip... familyMembers) {
            for(Trip trip: familyMembers){
                ResultForMemberExists resultForMemberExists = new ResultForMemberExists(trip.getTripId(),driverFamilyId);
                if(resultForMemberExists.isNotTrue()){
                    addMemberToFamily(trip.getTripId(), driverFamilyId);
                }

            }

        }
        private void addMemberToFamily(final String tripId, final String familyId) {
            throwException.ifTrue(IsTrue.thatNullOrEmptyForAny(tripId,familyId),"cant add family member if tripId and family empty");

            FamilyMember familyMember = new FamilyMember();
            familyMember.setMemberId(UUID.randomUUID().toString());
            familyMember.setTripId(tripId);
            familyMember.setFamilyId(familyId);
            new FamilyMemberDatabase().save(familyMember.getMemberId(), familyMember);

        }
    }

    protected void matchSenderAndReceiver() {

        //-----------
        //these classes are used internally [can call driver determinant.determineDriver e.g ResultForDetermineDriver
        DriverOfTheTwoParties driver = new DriverOfTheTwoParties(sendingParty,receivingParty);
        if(driver.isNotFound()){
            throwException.saying("One of the parties must be a driver");
        }
        else{
            String driverFamilyId = "";
            DriverFamily driverFamily = new DriverFamily(driver.getDriverTripId());
            if(driverFamily.isFound()){
                driverFamilyId = driverFamily.getFamilyId();
            }
            else if(driverFamily.isNotFound()){
                ResultForCreateNewFamilyForDriver resultForCreateNewFamilyForDriver = new ResultForCreateNewFamilyForDriver(driver.getDriverTripId());
                driverFamilyId = resultForCreateNewFamilyForDriver.getFamilyId();
            }
            ResultForAddMemberToFamilyIfNotExist resultForAddMemberToFamilyIfNotExist = new ResultForAddMemberToFamilyIfNotExist(driverFamilyId,sendingParty,receivingParty);
            new ResultsForAddMutualProposal(receivingParty.getTripId(),sendingParty.getTripId());
            //NotifyFamilyCreated.where(driverFamilyId);

        }

    }

    protected void applySamePartyPolicy() {
        throwException.ifTrue(sendingParty.isTripId(receivingParty.getTripId()), "Sending and receiving party can not be the same");
    }

    protected void applySameSexPolicy() {


        throwException.ifTrue(
                sendingParty.isDriver() && receivingParty.isDriver(),
                "You can not propose to a fellow driver, choose someone without a vehicle"
        );

        throwException.ifTrue(
                sendingParty.isPassenger() && receivingParty.isPassenger(),
                "You can not propose to a fellow passenger, choose someone driving"
        );
    }

    protected void applyMaritalLimitPolicy() {

        throwException.ifTrue(
                sendingParty.isReachedMaritalLimit(),
                sendingParty.isDriver() ? "Your car is full": "You are already taken" //"You are already taken or full"
        );

        throwException.ifTrue(
                receivingParty.isReachedMaritalLimit(),
                receivingParty.isDriver() ? "This car is full": "Already taken" // "Receiving party is already taken or full"
        );
    }

    protected void applyDuplicateMatchAttemptPolicy() {

        throwException.ifTrue(
                sendingParty.isMarriedTo(receivingParty),
                "you are already matched with " + receivingParty.getActorName()
        );

    }


    protected List<Proposal> getSentProposals() {
        return new ProposalDatabase().select(new LocalDatabase.Where<Proposal>() {
            @Override
            public boolean isTrue(Proposal currentRequest) {

                return currentRequest.isFromTrip(sendingParty.getTripId()) && currentRequest.isToTrip(receivingParty.getTripId());
            }
        });
    }

    protected List<Proposal> getReceivedProposals() {
        return new ProposalDatabase().select(new LocalDatabase.Where<Proposal>() {
            @Override
            public boolean isTrue(Proposal currentRequest) {

                return currentRequest.isFromTrip(receivingParty.getTripId()) && currentRequest.isToTrip(sendingParty.getTripId());
            }
        });
    }

    private Proposal createNewProposal() {
        Proposal proposal = new Proposal();
        String requestId = UUID.randomUUID().toString();
        proposal.setProposalId(requestId);
        proposal.setProposalDate(DateToday.get()); //we want to be able to passe the concrete date implementation but we use the abstract date system
        proposal.setActorId(me.getUserId());
        proposal.setToTripId(receivingParty.getTripId());
        proposal.setFromTripId(
                sendingParty.getTripId()
        );
        Log.d("SAVED",JsonEncoder.encode(proposal));
        return proposal;
    }

}
