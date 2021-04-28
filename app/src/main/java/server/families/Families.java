package server.families;

import android.util.Log;

import java.util.List;

import cache.FamilyDatabase;
import core.businessmessages.toServer.ParamsForGetFamilies;
import core.businessobjects.Family;
import core.businessobjects.User;
import libraries.IsTrue;
import libraries.data.LocalDatabase;
import server.Enrich;
import server.stored_procedures.SPFamilyIncludesUser;
import server.users.BackEndUser;

public class Families {
    private ParamsForGetFamilies params;

    private Families() {
    }
    public static List<Family> where(final ParamsForGetFamilies params){
        Families families = new Families();
        families.init(params);
        return families.getFamilies();
    }

    private void init(ParamsForGetFamilies params) {
        this.params = params;
    }
    private List<Family> getFamilies(){
        final User currentUser = BackEndUser.fromSessionId(params.getSessionId());

        List<Family> families = new FamilyDatabase().select(new LocalDatabase.Where<Family>() {
            @Override
            public boolean isTrue(Family family) {
                try {
                    Enrich.family(family, currentUser);
                    return IsTrue.forAll(
                            IsTrue.forAny(
                                    params.getMemberUserId() == null
                                            || params.getMemberUserId().trim().equalsIgnoreCase("")
                                            || new SPFamilyIncludesUser(family.getFamilyId(), params.getMemberUserId()).isTrue()
                            ),
                            IsTrue.forAny(
                                    IsTrue.thatNullOrEmpty(params.getCompletedByUserId()),
                                    TripIsCompletedByUser.where(family.getFamilyId(), params.getCompletedByUserId())

                            ),
                            IsTrue.forAny(
                                    IsTrue.thatNullOrEmpty(params.getNotCompletedByUserId()),
                                    !TripIsCompletedByUser.where(family.getFamilyId(), params.getNotCompletedByUserId())

                            )
                    );

                } catch (Exception ex) {
                    Log.d("GetFamiliesAPI",ex.getMessage());
                    return false;
                }

            }
        });
        return families;
    }
}
