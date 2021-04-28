package client.ui.display.notification;

import android.view.View;
import java.util.List;
import client.data.LocalSession;
import client.data.Rest2;
import client.ui.GotoActivity;
import core.businessmessages.toServer.ParamsForGetTrips;
import core.businessobjects.NotificationData;
import core.businessobjects.Trip;
import layers.render.Atom;
import retrofit2.Call;

public class TripNotificationActivity extends NotificationActivity<Trip> {

    @Override
    protected String getItemId(NotificationData data) {
        return data.getTripId();
    }

    @Override
    protected Call<List<Trip>> getCall(String itemId) {
        return Rest2.api().getTrips(params(itemId));
    }

    @Override
    protected void gotoActivity(Trip itemData) {
        GotoActivity.tripDetails(this,itemData);
    }

    @Override
    protected View itemIdNotProvided() {
        return Atom.centeredText(this,"Trip ID not provided");
    }

    @Override
    protected View itemDetailsNotFound() {
        return Atom.centeredText(this,"Trip details not found");
    }

    @Override
    protected View fetchingItemData() {
        return Atom.centeredText(this,"Fetching trip details");
    }


    private ParamsForGetTrips params(String tripId) {
        ParamsForGetTrips params = new ParamsForGetTrips();
        params.setTripId(tripId);
        params.setSessionId(LocalSession.instance().getId());
        return params;
    }
}