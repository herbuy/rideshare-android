package client.ui.display.notification;
import android.view.View;
import java.util.List;
import client.data.LocalSession;
import client.data.Rest2;
import client.ui.GotoActivity;
import core.businessmessages.toServer.ParamsForGetFamilies;
import core.businessobjects.Family;
import core.businessobjects.NotificationData;
import layers.render.Atom;
import retrofit2.Call;

public class FamilyMessageNotificationActivity extends NotificationActivity<Family> {

    @Override
    protected String getItemId(NotificationData data) {
        return data.getFamilyId();
    }

    @Override
    protected Call<List<Family>> getCall(String itemId) {
        return Rest2.api().getFamilies(params(itemId));
    }

    private ParamsForGetFamilies params(String familyId) {
        ParamsForGetFamilies params = new ParamsForGetFamilies();
        params.setFamilyId(familyId);
        params.setSessionId(LocalSession.instance().getId());
        return params;
    }

    @Override
    protected void gotoActivity(Family itemData) {
        GotoActivity.familyChat(this,itemData);
    }

    @Override
    protected View itemIdNotProvided() {
        return Atom.centeredText(this,"Family ID not provided");
    }

    @Override
    protected View itemDetailsNotFound() {
        return Atom.centeredText(this,"Family details not found");
    }

    @Override
    protected View fetchingItemData() {
        return Atom.centeredText(this,"Fetching family details");
    }

}