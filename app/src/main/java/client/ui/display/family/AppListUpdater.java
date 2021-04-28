package client.ui.display.family;

import android.content.Context;
import android.view.View;

import client.data.LocalSession;
import client.ui.AccountSwitchedEvent;
import client.ui.display.notification.NotificationService;
import core.businessobjects.SessionDetails;
import libraries.android.ListViewUpdater;
import libraries.android.MessageBox;
import libraries.android.NetStatus;
import libraries.ObserverList;

//could even pass in the family just, so that we add the logic for updating the family
//updating a family list is a single responsibility even if it could be initiated by so many events and status changes
public abstract class AppListUpdater<T> extends ListViewUpdater<T> {

    public AppListUpdater(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        //run notification subscription helper
        getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            private NotificationService.Listener onReceiveNotification = onReceiveNotification();
            private NotificationService.Callback callback = onReceiveNotification.callback();
            private String[] notifTypesInterestedIn = onReceiveNotification.getTypesInterestedIn();
            private NotificationService.Callback onMyPhotoChanged = onMyPhotoChanged();

            @Override
            public void onViewAttachedToWindow(View view) {
                //subscribe to notification service
                NotificationService.addListenerIfNotNull(onReceiveNotification);
                NotificationService.addCallbackForProfilePhotoChangedIfNotNull(LocalSession.instance().getUserId(), onMyPhotoChanged);
                //checkTotalCallbacks();
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                if(notifTypesInterestedIn != null){
                    for(String notifType : notifTypesInterestedIn){
                        NotificationService.removeCallbackIfNotNull(notifType,callback);
                    }
                }
                NotificationService.removeCallbackForProfilePhotoChangedIfNotNull(LocalSession.instance().getUserId(), onMyPhotoChanged);
                //checkTotalCallbacks();
            }
        });
    }

    private void checkTotalCallbacks() {
        MessageBox.show("Tot. Notif Callback: "+ NotificationService.getTotalCallbacks(),context);
    }

    /** override this to be notified when my profile photo changes */
    protected NotificationService.Callback onMyPhotoChanged() {
        return null;
    }

    private NotificationService.Listener onReceiveNotification() {
        return new NotificationService.Listener() {
            @Override
            public String[] getTypesInterestedIn() {
                return getNotifTypesInterestedIn();
            }

            @Override
            public NotificationService.Callback callback() {
                return AppListUpdater.this.getNotificationCallback();
            }
        };
    }

    /** a list of events for which u want to be notified */
    protected String[] getNotifTypesInterestedIn(){
        return null;
    }

    /** called when u receive a notification, override to implement */
    protected NotificationService.Callback getNotificationCallback() {
        return null;
    }



    @Override
    protected void listenForRefreshTriggers(final Runnable runnable) {

        NetStatus.instance().addListener(new NetStatus.Listener() {
            @Override
            public void onConnected() {
                runnable.run();

            }

            @Override
            public void onDisconnected() {

            }
        });


        AccountSwitchedEvent.instance().add(new ObserverList.Observer<SessionDetails>() {
            @Override
            public void notifyEvent(SessionDetails eventArgs) {
                runnable.run();
            }
        });

        //can be good, if not for getting events we have not been notified of, at least for updating time on the UI
        //test to see that time changes
        //chalelenge is, timers are expensive, so we need to see if we can use one time but different timer tasks

        /*
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tryUpdateCache();
            }
        }, 20000, 20000);*/

    }

}
