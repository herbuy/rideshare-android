package server.families;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import cache.FamilyMemberRatingsDatabase;
import core.businessobjects.FamilyMemberRating;

public class RateFamilyMember {

    public static List<FamilyMemberRating> where(String familyId, String memberIdRated, String userIdRating, float ratingTimeKeeping, float ratingFriendliness, float ratingSafeDriving, float ratingOtherFactors){
        FamilyMemberRating rating = createRatingData(familyId, memberIdRated, userIdRating, ratingTimeKeeping, ratingFriendliness, ratingSafeDriving, ratingOtherFactors);
        new FamilyMemberRatingsDatabase().save(rating.getRecordId(),rating);
        return Collections.singletonList(rating);


    }

    private static FamilyMemberRating createRatingData(String familyId, String memberIdRated, String userIdRating, float ratingTimeKeeping, float ratingFriendliness, float ratingSafeDriving, float ratingOtherFactors) {
        FamilyMemberRating familyMemberRating = new FamilyMemberRating();
        familyMemberRating.setRecordId(UUID.randomUUID().toString());
        familyMemberRating.setFamilyId(familyId);
        familyMemberRating.setMemberIdRated(memberIdRated);
        familyMemberRating.setMemberIdThatRated(GetFamilyMemberIdOrDie.whereUserId(userIdRating, "Must be a member of the trip"));
        familyMemberRating.setRatingTimeKeeping(ratingTimeKeeping);
        familyMemberRating.setRatingFriendliness(ratingFriendliness);
        familyMemberRating.setRatingSafeDriving(ratingSafeDriving);
        familyMemberRating.setRatingOtherFactors(ratingOtherFactors);

        familyMemberRating.setTimestampRatedInMillis(System.currentTimeMillis());
        return familyMemberRating;

    }
}
