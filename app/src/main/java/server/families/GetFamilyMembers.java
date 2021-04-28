package server.families;

import java.util.List;

import cache.FamilyMemberDatabase;
import core.businessobjects.FamilyMember;
import core.businessobjects.User;
import libraries.data.LocalDatabase;
import server.Enrich;

public class GetFamilyMembers {
    public static List<FamilyMember> where(final String familyId, final User currentUser) {
        List<FamilyMember> results = new FamilyMemberDatabase().select(new LocalDatabase.Where<FamilyMember>() {
            @Override
            public boolean isTrue(FamilyMember member) {
                Enrich.familyMember(member,currentUser);
                return member.isMemberOfFamily(familyId);
            }
        });
        return results;
    }
}
