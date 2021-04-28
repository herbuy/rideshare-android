package server.families;

import java.util.ArrayList;
import java.util.List;

import cache.FamilyMemberDatabase;
import core.businessobjects.FamilyMember;
import libraries.data.LocalDatabase;

public class FamilyMatesOfTrip {
    private boolean isFound = false;
    private List<FamilyMember> mates = new ArrayList<>();

    private FamilyMatesOfTrip(){
    }

    public static FamilyMatesOfTrip where(String tripId){
        FamilyMatesOfTrip instance = new FamilyMatesOfTrip();
        instance.init(tripId);
        return instance;
    }

    private void init(final String tripId) {
        //get family of this trip
        final FamilyIdOfTrip familyIdOfTrip = FamilyIdOfTrip.where(tripId);

        if(familyIdOfTrip.isFound()){
            List<FamilyMember> results = new FamilyMemberDatabase().select(new LocalDatabase.Where<FamilyMember>() {
                @Override
                public boolean isTrue(FamilyMember familyMember) {
                    return !familyMember.isTripId(tripId) && familyMember.isMemberOfFamily(familyIdOfTrip.getFamilyId());
                }
            });
            if(results != null && results.size() > 0){
                isFound = true;
                mates = results;
            }

        }
    }

    public boolean isFound() {
        return isFound;
    }

    public List<FamilyMember> getMates() {
        return mates;
    }

    public int count() {
        return mates.size();
    }
}
