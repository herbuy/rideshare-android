package client.ui.display.notification;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import client.ui.IntentExtras;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import core.businessobjects.NotificationData;
import layers.render.Atom;
import libraries.android.CancelNotification;
import libraries.JsonEncoder;
import libraries.android.HerbuyStateView;
import retrofit2.Call;
import retrofit2.Response;
import shared.BaseActivity;

public abstract class NotificationActivity<ListItemType> extends BaseActivity {
    FrameLayoutBasedHerbuyStateView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new FrameLayoutBasedHerbuyStateView(this);
        setContentView(view.getView());

        NotificationData data = JsonEncoder.decode(getIntent().getStringExtra(IntentExtras.data), NotificationData.class);
        if(data == null){
            view.render(noData());
            return;
        }

        String itemId = getItemId(data);
        if(itemId == null || itemId.trim().equalsIgnoreCase("")){
            view.render(itemIdNotProvided());
            return;
        }

        String notificationId = data.get("notification_id");
        if(notificationId == null || notificationId.trim().equalsIgnoreCase("")){
            view.render(notificationIdMissing());
            return;
        }


        downloadItemDetailsInBackground(itemId, notificationId);

    }

    protected abstract String getItemId(NotificationData data);
    private View notificationIdMissing() {
        return Atom.centeredText(this,"Notification ID missing");
    }

    private void downloadItemDetailsInBackground(final String itemId, final String notificationId) {
        view.render(fetchingItemData());
        Call<List<ListItemType>> call = getCall(itemId);
        call.enqueue(new AppCallback<List<ListItemType>>() {
            @Override
            protected void onSuccess(Call<List<ListItemType>> call, Response<List<ListItemType>> response) {
                handleSuccess(response, notificationId);
            }

            @Override
            protected void onError(Call<List<ListItemType>> call, Throwable t) {
                handleError(t, itemId, notificationId);
            }
        });
    }

    protected abstract Call<List<ListItemType>> getCall(String itemId);

    private void handleError(Throwable t, final String itemId, final String notificationId) {
        view.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
            @Override
            public void run() {
                downloadItemDetailsInBackground(itemId, notificationId);
            }
        });
    }

    private void handleSuccess(Response<List<ListItemType>> response, String notificationId) {

        new NotificationCache().deleteByKey(notificationId);
        CancelNotification.where((Context)this,notificationId);

        if(response.body() == null || response.body().size() < 1){
            view.render(itemDetailsNotFound());
        }
        else{
            finish();
            gotoActivity(response.body().get(0));
        }
    }

    protected abstract void gotoActivity(ListItemType itemData);

    protected abstract View itemDetailsNotFound();

    protected abstract View itemIdNotProvided();

    protected abstract View fetchingItemData();

    private View noData(){
        return Atom.centeredText(this,"Notification data not provided");
    }

}