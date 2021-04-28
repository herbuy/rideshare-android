package cache;

import core.businessobjects.FamilyMemberRating;

public class FamilyMemberRatingsDatabase extends AppDatabase<FamilyMemberRating> {

    @Override
    protected Class<FamilyMemberRating> getClassOfT() {
        return FamilyMemberRating.class;
    }

    @Override
    protected final String getTableName() {
        return "family_member_ratings_table";
    }
}
