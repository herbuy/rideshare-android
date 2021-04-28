package client.ui.display.family;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import client.data.LocalSession;
import client.data.Rest2;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.display.notification.NotificationService;
import core.businessmessages.toServer.ParamsForGetFamilies;
import core.businessobjects.Family;
import core.businessobjects.NotificationMessage;
import layers.render.Atom;
import libraries.android.HerbuyStateView;
import retrofit2.Call;

//could even pass in the family just, so that we add the logic for updating the family
//updating a family list is a single responsibility even if it could be initiated by so many events and status changes
public abstract class FamilyListUpdater extends AppListUpdater<Family> {
    protected FrameLayoutBasedHerbuyStateView view;

    public FamilyListUpdater(Context context) {
        super(context);

    }

    @Override
    protected void init() {
        view = new FrameLayoutBasedHerbuyStateView(context);
        view.getView().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        super.init();
    }

    @Override
    public void updateView(List<Family> data) {
        view.render(listOfFamilies(data));
    }

    protected abstract View listOfFamilies(List<Family> data);



    @Override
    public void renderLoading() {
        view.render(Atom.centeredText(context, "Loading..."));
    }

    @Override
    public Call<List<Family>> getCall() {
        return Rest2.api().getFamilies(paramsForGetFamilies());
    }

    private ParamsForGetFamilies paramsForGetFamilies() {
        ParamsForGetFamilies params = new ParamsForGetFamilies();
        params.setSessionId(LocalSession.instance().getId());
        params.setMemberUserId(getUserIdBeingViewed());
        params.setNotCompletedByUserId(LocalSession.instance().getUserId());
        return params;
    }


    @Override
    public void renderError(String message, final Runnable onRetry) {
        view.renderError(message, new HerbuyStateView.OnRetryLoad() {
            @Override
            public void run() {
                onRetry.run();
            }
        });
    }

    @Override
    public List<Family> getCachedData() {
        return new FamilyListCache().selectAll();
    }

    @Override
    public void clearCache() {
        new FamilyListCache().clear();
    }

    @Override
    public void addToCache(Family item) {
        new FamilyListCache().save(item.getFamilyId(), item);
    }

    @Override
    public void listenForRefreshTriggers(final Runnable runnable) {
        super.listenForRefreshTriggers(runnable);
        refreshRunnable = runnable;

    }

    @Override
    protected String[] getNotifTypesInterestedIn() {
        return new String[]{
                NotificationMessage.Type.MATCH_FOUND,
                NotificationMessage.Type.FAMILY_MESSAGE,
                NotificationMessage.Type.FAMILY_MESSAGES_SEEN
        };
    }

    private NotificationService.Callback callback;
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

    Runnable refreshRunnable;
    private void refresh() {
        if(refreshRunnable != null){
            refreshRunnable.run();
        }
    }

    @Override
    public View getView() {
        return view.getView();
    }


    public String getUserIdBeingViewed() {
        return LocalSession.instance().getUserId();
    }
}
