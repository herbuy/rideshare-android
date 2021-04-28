package server.families;

import cache.FamilyMemberDatabase;
import core.businessobjects.FamilyMember;
import libraries.data.LocalDatabase;

//given two trips, returns proposals sent from one trip to another
public class FamilyIdOfTrip {
    private boolean notFound = false;
    private String familyId;

    private FamilyIdOfTrip(){

    }

    public static FamilyIdOfTrip where(String tripId){
        FamilyIdOfTrip instance = new FamilyIdOfTrip();
        instance.init(tripId);
        return instance;

    }

    public String getFamilyId() {
        return familyId;
    }

    private void init(final String tripId) {
         FamilyMember familyMember = new FamilyMemberDatabase().selectFirst(new LocalDatabase.Where<FamilyMember>() {
             @Override
             public boolean isTrue(FamilyMember currentMember) {
                 return currentMember.isTripId(tripId);
             }
         });
        if(familyMember == null){
            notFound = true;
        }
        else{
            familyId = familyMember.getFamilyId();
        }

    }

    public boolean isNotFound() {
        return notFound;
    }
    public boolean isFound(){
        return !notFound;
    }

    public boolean isSameAsFamilyIdOfTrip(String tripId) {
        FamilyIdOfTrip familyIdOfTrip2 = FamilyIdOfTrip.where(tripId);
        return this.isNotFound()
                && familyIdOfTrip2.isFound()
                && this.isFamilyId(familyIdOfTrip2.getFamilyId())
                ;

    }

    public boolean isFamilyId(String familyId) {
        return familyId != null
                && !familyId.trim().equalsIgnoreCase("")
                && this.familyId != null
                && !this.familyId.trim().equalsIgnoreCase("")
                && this.familyId.trim().equalsIgnoreCase(familyId.trim())
                ;
    }
}
