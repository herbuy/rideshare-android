package client.ui.display.notifications;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import client.data.LocalSession;
import client.data.Rest2;
import client.ui.display.family.AppListUpdater;
import client.ui.display.notification.NotificationService;
import core.businessmessages.toServer.ParamsForGetDirtyEntities;
import core.businessobjects.DirtyEntities;
import core.businessobjects.NotificationMessage;
import libraries.android.CustomTabLayout;
import retrofit2.Call;

public class TotalDirtyEntitiesUpdater extends AppListUpdater<DirtyEntities> {
    private CustomTabLayout tabLayout;
    List<DirtyEntities> dirtyEntities = new ArrayList<>();

    public TotalDirtyEntitiesUpdater(Context context, CustomTabLayout tabLayout) {
        super(context);
        this.tabLayout = tabLayout;
        start();
    }

    @Override
    protected boolean autoStart() {
        return false;
    }

    @Override
    protected void init() {

    }

    @Override
    protected List<DirtyEntities> getCachedData() {
        return dirtyEntities;
    }

    @Override
    protected void clearCache() {
        dirtyEntities = new ArrayList<>();
    }

    @Override
    protected void addToCache(DirtyEntities item) {
        clearCache();
        dirtyEntities.add(item);

    }

    @Override
    protected void updateView(List<DirtyEntities> data) {
        DirtyEntities dirtyEntities = data.get(0);

        //common data: contect
        //specific data" label, count getter, tab index to select

        //make custom tab 1
        tabLayout.setCount(0, dirtyEntities.getTrips());
        tabLayout.setCount(1, dirtyEntities.getFamilies());

    }

    @Override
    protected void renderEmpty() {

    }

    @Override
    protected void renderLoading() {

    }

    @Override
    protected void renderError(String message, Runnable runnable) {

    }

    @Override
    protected Call<List<DirtyEntities>> getCall() {
        ParamsForGetDirtyEntities paramsForGetDirtyEntities = new ParamsForGetDirtyEntities();
        paramsForGetDirtyEntities.setSessionId(LocalSession.instance().getId());
        return Rest2.api().getDirtyEntities(paramsForGetDirtyEntities);
    }

    Runnable refreshRunnable;
    private void refresh() {
        if(refreshRunnable != null){
            refreshRunnable.run();
        }
    }
    @Override
    public void listenForRefreshTriggers(final Runnable runnable) {
        super.listenForRefreshTriggers(runnable);
        refreshRunnable = runnable;

    }

    @Override
    protected String[] getNotifTypesInterestedIn() {
        return new String[]{
            NotificationMessage.Type.POTENTIAL_MATCH_FOUND,
                    NotificationMessage.Type.FAMILY_MESSAGE,
                    NotificationMessage.Type.MATCH_FOUND,
                    NotificationMessage.Type.FAMILY_MESSAGES_SEEN
        };
    }

    NotificationService.Callback callback;
    @Override
    protected NotificationService.Callback getNotificationCallback() {
        if(callback == null){
            callback = new NotificationService.Callback() {
                @Override
                public void run(NotificationMessage notificationMessage) {
                    refresh();
                }
            };
        }
        return callback;
    }

    @Override
    public View getView() {
        return tabLayout.getView();
    }

}
