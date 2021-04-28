package client.ui.display.family;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import client.data.LocalSession;
import client.data.Rest2;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.display.notification.NotificationService;
import core.businessmessages.toServer.ParamsForGetFamilyMessages;
import core.businessmessages.toServer.ParamsForSendFamilyMessage;
import core.businessobjects.Family;
import core.businessobjects.FamilyMessage;
import core.businessobjects.NotificationMessage;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.android.ViewUtils;
import libraries.android.HerbuyStateView;
import libraries.android.HerbuyViewGroup;
import retrofit2.Call;

public abstract class FamilyMessageListUpdater extends AppListUpdater<FamilyMessage> {

    public FamilyMessageListUpdater(Context context, Family familyOrMarriage) {
        super(context);
        this.familyOrMarriage = familyOrMarriage;
        start();
    }

    @Override
    protected boolean autoStart() {
        return false;
    }

    //we track the family message views so that we can tell them to update when messages are seen for this family
    List<FamilyMessageView> familyMessageViews = new ArrayList<>();
    @Override
    protected void init() {
        view = new FrameLayoutBasedHerbuyStateView(context);

        listViewer = new HerbuyViewGroup<FamilyMessage>(context) {
            @Override
            protected ViewGroup createAbsListView(Context context) {
                return MakeDummy.linearLayoutVertical(context);
            }

            @Override
            protected View createItemView(Context context, FamilyMessage item) {
                FamilyMessageView familyMessageView = new FamilyMessageView(context, item,familyMessageListener);
                familyMessageViews.add(familyMessageView);
                return familyMessageView.getView();
            }
        };
        listViewer.setComparator(sortByTimestampAscending());

        NotificationService.interceptors.add(notificationInterceptor);

    }

    private FamilyMessageView.EventListener familyMessageListener = new FamilyMessageView.EventListener() {
        @Override
        public void onMessageSent(FamilyMessageView sender, FamilyMessage message) {
            scrollToBottom();
            //MessageBox.show("Message sent",context);
        }
    };

    //this approach seems to be working, but is inefficient for two main reaons
    //1. it runs all callbacks/interceptors  [unnecessary work] 2. FamilyId that shd refresh could be different from this one
    NotificationService.Interceptor notificationInterceptor = new NotificationService.Interceptor() {
        @Override
        public void intercept(NotificationMessage notificationMessage) {
            if(notificationMessage.isType(NotificationMessage.Type.FAMILY_MESSAGES_SEEN)){
                MessageBox.show("message seen",context);
            }
            else{
                refresh();
            }

            //MessageBox.show("I have received",context);
        }
    };

    @Override
    protected List<FamilyMessage> getCachedData() {
        MessageCacheForFamily messageCacheForFamily = new FamilyMessageCaches().getByKey(familyOrMarriage.getFamilyId());
        if (messageCacheForFamily == null || messageCacheForFamily.getList() == null) {
            return new ArrayList<>();
        }

        return messageCacheForFamily.getList();
    }

    @Override
    protected void clearCache() {
        new FamilyMessageCaches().deleteByKey(familyOrMarriage.getFamilyId());

    }

    @Override
    protected void addToCache(FamilyMessage item) {

        familyCreateCacheIfNotExists(item.getToFamilyId());

        MessageCacheForFamily collection = familyGetByKey(item.getToFamilyId());
        collection.getList().add(item);
        new FamilyMessageCaches().save(item.getToFamilyId(), collection);


    }




    private void familyCreateCacheIfNotExists(String familyId) {
        if (!familyExists(familyId)) {

            MessageCacheForFamily messageCacheForFamily = new MessageCacheForFamily();
            messageCacheForFamily.setFamilyId(familyId);
            new FamilyMessageCaches().save(messageCacheForFamily.getFamilyId(), messageCacheForFamily);
        }
    }


    private MessageCacheForFamily familyGetByKey(String toFamilyId) {
        return new FamilyMessageCaches().getByKey(toFamilyId);
    }

    private boolean familyExists(String familyId) {
        return new FamilyMessageCaches().hasKey(familyId);
    }

    protected void doUpdateView(List<FamilyMessage> data) {
        Log.d(className(), "total family messages found: " + data.size());
        Log.d(className(), "refreshing list viewer");

        //if was at end of list, enable scroll to bottom to see the new message
        //but if was not at end of list, psssible was perusing through list, just indicate new message added at bottom


        listViewer.setContent(data);
        view.render(listViewer.getView());
        view.getView().post(new Runnable() {
            @Override
            public void run() {
                scrollToBottom();
            }
        });
    }

    private void scrollToBottom() {

        if (listViewer.hasChildren()) {

            int requiredScrollY = ViewUtils.getRequiredScrollY(listViewer.lastChild(),listViewer.getContainer(),listViewer.getScrollView());

            //MessageBox.show("scroll needed",context);
            ValueAnimator valueAnimator = ValueAnimator.ofInt(
                    listViewer.getScrollView().getScrollY(),
                    requiredScrollY
                    //(int)listViewer.lastChild().getY()
                    //90000
            );

            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int newVal = (int) animation.getAnimatedValue();
                    listViewer.getScrollView().setScrollY(newVal);
                }
            });
            valueAnimator.setDuration(400);
            valueAnimator.start();

        }



        /*
        if (listViewer.hasChildren()) {

            if (ViewUtils.needToScroll(listViewer.lastChild(), listViewer.getContainer(), listViewer.getScrollView().getWidth(), listViewer.getScrollView().getHeight())) {
                //MessageBox.show("scroll needed",context);
                ValueAnimator valueAnimator = ValueAnimator.ofInt(
                        listViewer.getScrollView().getScrollY(),
                        //(int)listViewer.lastChild().getY()
                        90000
                );


                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int newVal = (int) animation.getAnimatedValue();
                        listViewer.getScrollView().setScrollY(newVal);
                    }
                });
                valueAnimator.setDuration(400);
                valueAnimator.start();
            } else {
                //MessageBox.show("no need to scroll",context);
            }

        }
        */
    }

    @Override
    protected void renderEmpty() {
        view.render(emptyList());
    }

    @Override
    protected void renderLoading() {
        view.render(Atom.centeredText(context, "Loading..."));
    }

    @Override
    protected void renderError(String message, final Runnable runnable) {
        view.renderError(message, new HerbuyStateView.OnRetryLoad() {
            @Override
            public void run() {
                runnable.run();
            }
        });
    }

    @Override
    protected Call<List<FamilyMessage>> getCall() {

        return Rest2.api().getFamilyMessages(paramsForGetFamilyMessages());
    }

    @Override
    protected String[] getNotifTypesInterestedIn() {
        return new String[]{
                NotificationMessage.Type.FAMILY_MESSAGE,
                NotificationMessage.Type.MATCH_FOUND,
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
    public void listenForRefreshTriggers(final Runnable runnable) {
        refreshRunnable = runnable;
        super.listenForRefreshTriggers(runnable);

    }


    private void markUIMessagesAsSeen() {
        for(FamilyMessageView familyMessageView : familyMessageViews){
            familyMessageView.setStatusToSeen();
        }
    }


    Family familyOrMarriage;
    HerbuyViewGroup<FamilyMessage> listViewer;
    FrameLayoutBasedHerbuyStateView view;

    private Comparator<FamilyMessage> sortByTimestampAscending() {
        return new Comparator<FamilyMessage>() {
            @Override
            public int compare(FamilyMessage lhs, FamilyMessage rhs) {
                if (lhs.getTimestampCreatedMillis() < rhs.getTimestampCreatedMillis()) {
                    return -1;
                } else if (lhs.getTimestampCreatedMillis() > rhs.getTimestampCreatedMillis()) {
                    return 1;
                } else {
                    return 0;
                }

            }
        };
    }

    private String className() {
        return getClass().getSimpleName();
    }

    private View emptyList() {
        return Atom.centeredText(context, "No previous chats. They will appear here once available.");
    }

    private ParamsForGetFamilyMessages paramsForGetFamilyMessages() {

        ParamsForGetFamilyMessages params = new ParamsForGetFamilyMessages();
        params.setFamilyId(familyOrMarriage.getFamilyId());
        params.setForUserId(LocalSession.instance().getUserId());
        params.setSessionId(LocalSession.instance().getId());
        params.setSeen(isActive);
        return params;


    }


    public View getView() {
        return view.getView();
    }

    public void sendMessage(ParamsForSendFamilyMessage paramsForSendFamilyMessage) {
        FamilyMessage familyMessage = createMessageFromSendParameters(paramsForSendFamilyMessage);
        listViewer.add(familyMessage);
        listViewer.getView().post(new Runnable() {
            @Override
            public void run() {
                listViewer.scrollToBottom();
            }
        });
        //listViewer.scrollToBottom();


    }

    private FamilyMessage createMessageFromSendParameters(ParamsForSendFamilyMessage paramsForSendFamilyMessage) {
        FamilyMessage familyMessage = new FamilyMessage();
        familyMessage.setFromUserId(paramsForSendFamilyMessage.getFromUserId());
        familyMessage.setToFamilyId(paramsForSendFamilyMessage.getToFamilyId());
        familyMessage.setMessageType(paramsForSendFamilyMessage.getMessageType());
        familyMessage.setMessageText(paramsForSendFamilyMessage.getMessageText());
        familyMessage.setCorrelationId(paramsForSendFamilyMessage.getCorrelationId());
        familyMessage.setStatus(FamilyMessage.Status.SENDING);
        familyMessage.setTimestampCreatedMillis(System.currentTimeMillis());
        return familyMessage;
    }

    private boolean isActive = true;
    public void setStatusToStopped() {
        log("Chat stopped");
        isActive = false;
    }

    public void setStatusToDestroyed() {
        log("Chat destroyed");
        isActive = false;
    }

    public void setStatusToPaused() {
        log("Chat paused");
        isActive = false;
    }

    public void setStatusToResumed() {
        log("Chat resumed");
        isActive = true;


    }

    public void setStatusToStarted() {
        log("Chat started");
        isActive = true;


    }
    public void setStatusToRestarted() {
        log("Chat re-started");
        isActive = true;
    }

    boolean initialized = false;
    Runnable onInitialize;
    @Override
    protected void updateView(final List<FamilyMessage> data) {
        if(initialized){
            doUpdateView(data);
        }
        else{
            onInitialize = new Runnable() {
                @Override
                public void run() {
                    doUpdateView(data);
                }
            };
        }

    }

    public void setStatusToPostResume() {
        log("Chat post-resumed");
        isActive = true;
    }

    public void setIsVisibleToTrue() {
        log("Enter animation complete");
        isActive = true;

        initialized = true;
        if(onInitialize != null){
            onInitialize.run();
            onInitialize = null;
        }
    }
}
