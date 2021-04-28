package server;

import client.data.HerbuyApi;
import cache.TripDatabase;
import cache.UserDatabase;
import core.businessobjects.CompletedTrip;
import core.businessobjects.Contact;
import core.businessobjects.FamilyMessage;
import core.businessobjects.MessageToTripActor;
import core.businessobjects.SessionDetails;
import core.businessobjects.FamilyMember;
import core.businessobjects.Family;
import core.businessobjects.Trip;
import core.businessobjects.User;
import libraries.throwException;
import server.families.TripIsCompletedByUser;
import server.families.UnseenMessages;
import server.stored_procedures.SPFamilyMember;
import server.trip.GetUnseenMessageCount;

public class Enrich {

    public static void trip(final Trip tripBeingViewed, User currentUser) {

        if (tripBeingViewed == null) {
            return;
        }

        //add actor information
        User actor = new UserDatabase().getByKey(tripBeingViewed.getActorId());
        if (actor != null) {
            tripBeingViewed.setActorName(actor.getUserName());
            tripBeingViewed.setActorProfilePic(actor.getProfilePic());
        }

        User forUser = new UserDatabase().getByKey(tripBeingViewed.getForUserId());
        if (forUser != null) {
            tripBeingViewed.setForUserName(forUser.getUserName());
            tripBeingViewed.setForUserProfilePic(forUser.getProfilePic());
        }



        //indicate family to whic trip belongs
        SPFamilyMember spFamilyMember = new SPFamilyMember(tripBeingViewed.getTripId());
        if(spFamilyMember.isFound()){
            tripBeingViewed.setFamilyId(spFamilyMember.getFamilyMember().getFamilyId());
        }

        //add info on how many marriages so far
        //get number of families to which a trip belongs so far [select distinct family from family members where trip id =

        tripBeingViewed.setTotalOtherFamilyMembers(
                StoredProcedures.getOtherFamilyMembers(tripBeingViewed.getTripId()).size()
        );

        //determining if a trip is my family member
        if(currentUser != null){
            tripBeingViewed.setUnseenMessageCount(GetUnseenMessageCount.where(tripBeingViewed.getTripId()));
        }





    }


    public static void messageToTripActor(final MessageToTripActor object) {
        if (object == null) {
            return;
        }

        new HerbuyApi.CodeBlock() {
            @Override
            public void run() {
                addActorInfo();
                addTripInformation();
            }

            protected void addTripInformation() {
                Trip trip = new TripDatabase().getByKey(object.getToTripId());
                if (trip != null) {
                    Enrich.trip(trip, null);

                    object.setTripDate(trip.getTripDate());
                    object.setTargetUserId(trip.getActorId());
                    object.setTargetUserName(trip.getActorName());
                    object.setTargetUserProfilePic(trip.getActorProfilePic());
                }
            }


            protected void addActorInfo() {
                User actor = new UserDatabase().getByKey(object.getActorId());
                if (actor != null) {
                    object.setActorName(actor.getUserName());
                    object.setActorProfilePic(actor.getProfilePic());
                }
            }
        }.run();

        //add trip info

    }

    public static void sessionDetails(SessionDetails sessionDetails) {
        if (sessionDetails != null) {
            User user = new UserDatabase().getByKey(sessionDetails.getUserId());
            if (user != null) {
                sessionDetails.setUserName(user.getUserName());
                sessionDetails.setUserProfilePic(user.getProfilePic());
            }
        }
    }

    public static void contact(Contact contact) {
        throwException.ifNull(contact, "Can not enrich a null contact");
        User contactDetails = StoredProcedures.getUserDetailsOrDie(contact.getContactId(), "Could not obtain contact details to enrich the contact");
        contact.setContactName(contactDetails.getUserName());
        contact.setContactProfilePic(contactDetails.getProfilePic());
    }

    public static void family(Family family, User currentUser) {
        try{
            Trip trip = StoredProcedures.getTripOrDie(family.getDriverTripId(),"driver trip does not exist");
            trip(trip, null);
            family.setTripDate(trip.getTripDate());

            family.setDriverId(trip.getForUserId());
            family.setDriverName(trip.getForUserName());
            family.setDriverProfilePic(trip.getForUserProfilePic());

            if(currentUser != null){
                family.setUnseenMessageCount(UnseenMessages.where(family.getFamilyId(), currentUser.getUserId()).getCount());
                family.setIsCompleted(TripIsCompletedByUser.where(family.getFamilyId(), currentUser.getUserId()));
            }

        }
        catch (Throwable t){
            System.out.println("Error enriching family: "+t.getMessage());
            return;
        }

    }

    public static void familyMessage(FamilyMessage message) {
        if(message != null){
            User sender = StoredProcedures.getUserDetailsOrDie(message.getFromUserId(),"Sender does not exist");
            message.setFromUserName(sender.getUserName());
            message.setFromUserProfilePic(sender.getProfilePic());
        }
    }

    public static void familyMember(FamilyMember member, User currentUser) {
        try{
            Trip trip = StoredProcedures.getTripOrDie(member.getTripId(),"trip does not exist");
            Enrich.trip(trip,currentUser);
            member.setUserId(trip.getForUserId());
            member.setMemberName(trip.getForUserName());
            member.setMemberProfilePic(trip.getForUserProfilePic());
        }
        catch (Throwable t){

        }
    }

    public static void completedTrip(CompletedTrip trip) {
        if(trip != null){
            try{
                User user = new UserDatabase().getByKey(trip.getUserIdCompleted());
                trip.setUserNameCompleted(user.getUserName());
                trip.setUserProfilePicCompleted(user.getProfilePic());

            }
            catch (Exception ex){

            }
        }

    }
}
