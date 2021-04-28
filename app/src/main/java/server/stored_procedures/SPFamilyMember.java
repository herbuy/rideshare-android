package server.stored_procedures;

import java.util.List;

import cache.FamilyMemberDatabase;
import core.businessobjects.FamilyMember;
import libraries.data.LocalDatabase;

//retrives a family member from the database
public class SPFamilyMember {

    private boolean found = true;
    private FamilyMember familyMember;

    public SPFamilyMember(String tripId) {
        init(tripId);
    }

    private void init(final String tripId){
        List<FamilyMember> matchingMembers = new FamilyMemberDatabase().select(new LocalDatabase.Where<FamilyMember>() {
            @Override
            public boolean isTrue(FamilyMember object) {
                return object.isTripId(tripId);
            }
        });
        if(matchingMembers == null || matchingMembers.size() < 1){
            found = false;
        }
        else{
            familyMember = matchingMembers.get(0);
        }
    }

    public boolean isFound() {
        return found;
    }

    public FamilyMember getFamilyMember() {
        return familyMember;
    }
}
