package server.stored_procedures;

import java.util.List;

import cache.FamilyMemberDatabase;
import cache.TripDatabase;
import core.businessobjects.FamilyMember;
import core.businessobjects.Trip;
import libraries.data.LocalDatabase;

public class SPFamilyIncludesUser {
    private boolean aTrue = false;

    public SPFamilyIncludesUser(String familyId, String memberUserId) {
        init(familyId,memberUserId);

    }

    private void init(final String familyId, final String userId) {
        //a user is a member of a family if a trip in the members table is theirs
        List<FamilyMember> familyMembersPostedByUser = new FamilyMemberDatabase().select(new LocalDatabase.Where<FamilyMember>() {
            @Override
            public boolean isTrue(FamilyMember familyMember) {
                return familyMember.isMemberOfFamily(familyId) && tripIsForUserId(familyMember.getTripId(), userId);
            }
        });
        if(familyMembersPostedByUser != null && familyMembersPostedByUser.size() > 0){
            aTrue = true;
        }
    }
    private boolean tripIsForUserId(final String tripId, final String userId){
        List<Trip> matchingTrips = new TripDatabase().select(new LocalDatabase.Where<Trip>() {
            @Override
            public boolean isTrue(Trip trip) {
                return trip.isForUser(userId) && trip.isTripId(tripId);
            }
        });
        return matchingTrips != null && matchingTrips.size() > 0;
    }

    public boolean isTrue() {
        return aTrue;
    }
}
