package server.families;

import cache.FamilyMemberDatabase;
import core.businessobjects.FamilyMember;
import libraries.data.LocalDatabase;
import server.Enrich;

public class GetFamilyMemberIdOrDie {
    public static String whereUserId(final String userId, String errorMessage) {
        FamilyMember familyMember = new FamilyMemberDatabase().selectFirst(new LocalDatabase.Where<FamilyMember>() {
            @Override
            public boolean isTrue(FamilyMember object) {
                Enrich.familyMember(object,null);
                return object.isUserId(userId);
            }
        });
        if(familyMember == null){
            throw new RuntimeException(errorMessage);
        }
        return familyMember.getMemberId();
    }
}
