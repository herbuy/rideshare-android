package cache;

import core.businessobjects.FamilyMember;

public class FamilyMemberDatabase extends AppDatabase<FamilyMember> {

    @Override
    protected Class<FamilyMember> getClassOfT() {
        return FamilyMember.class;
    }

    @Override
    protected final String getTableName() {
        return "tt_family_members_table";
    }
}
