package server.users;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;
import cache.FamilyMemberDatabase;
import cache.TripDatabase;
import cache.UserDatabase;
import core.businessmessages.toServer.ParamsForSendFamilyMessage;
import core.businessobjects.FamilyMessage;
import core.businessobjects.FamilyMember;
import core.businessobjects.Trip;
import core.businessobjects.User;
import libraries.JsonEncoder;
import libraries.data.LocalDatabase;
import server.families.SendMessage;
import server.sessions.BackEndSession;

public class BackEndUser extends User {
    private boolean isFound = false;

    private BackEndUser(){
    }

    public static BackEndUser fromSessionId(String sessionId){
        BackEndSession session = BackEndSession.where(sessionId);
        if(session.isFound()){
            return fromUserId(session.getUserId());
        }
        else{
            throw new RuntimeException("You need to login");
        }
    }

    //instantiates a back end user from a user id
    public static BackEndUser fromUserId(String userId) {
        User user = new UserDatabase().getByKey(userId);
        if(user != null){
            BackEndUser backEndUser = JsonEncoder.decode(JsonEncoder.encode(user),BackEndUser.class);
            backEndUser.isFound = true;
            return backEndUser;
        }
        else{
            return new BackEndUser();
        }
    }

    public boolean isFound() {
        return isFound;
    }

    public boolean isMemberOfFamily(final String toFamilyId) {
        //get family members where trip id for for user(this)
        //if at least 1, then true, else false
        FamilyMember result = new FamilyMemberDatabase().selectFirst(new LocalDatabase.Where<FamilyMember>() {
            @Override
            public boolean isTrue(FamilyMember member) {
                return member.isMemberOfFamily(toFamilyId) && userTripIdSet().contains(member.getTripId());
            }
        });

        return result != null;
    }

    //trips posted by this user
    @NonNull
    private Set<String> userTripIdSet() {
        final Set<String> tripIdSet = new HashSet<>();
        new TripDatabase().forEach(new LocalDatabase.ForEach<Trip>() {
            @Override
            public void run(Trip trip) {
                if (trip.isForUser(BackEndUser.this.getUserId())) {
                    tripIdSet.add(trip.getTripId());
                }
            }
        });
        return tripIdSet;
    }

    public FamilyMessage sendMessage(final ParamsForSendFamilyMessage params) {
        return SendMessage.where(params).getSentMessage();

    }

}
