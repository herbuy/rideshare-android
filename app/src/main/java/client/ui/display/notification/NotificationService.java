package client.ui.display.notification;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.skyvolt.jabber.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.data.LocalSession;
import client.ui.AccountSwitchedEvent;
import client.ui.display.family.MarkCachedFamilyMessagesAsSeen;
import client.ui.display.notifications.NotifyForegroundListeners;
import core.businessmessages.toServer.NotifRequestForProfilePhotoChanged;
import core.businessmessages.toServer.OpeningHandshake;
import core.businessobjects.NotificationMessage;
import core.businessobjects.SessionDetails;
import initialize.InitializeSystem;
import libraries.android.DisplayNotificationMessage;
import libraries.android.GetBitmapFromUrl;
import libraries.JsonEncoder;
import libraries.ObserverList;
import libraries.android.WebSocketConnection;
import shared.AppIsInForeGround;

public class NotificationService extends SelfRestartingService {

    public static void addCallbackForProfilePhotoChangedIfNotNull(String forUserId, final Callback callback) {
        sendNotifRequestForProfilePhotoChanged(forUserId);
        addCallbackIfNotNull(NotificationMessage.Type.PROFILE_PHOTO_CHANGED, callback);
    }

    public static void removeCallbackForProfilePhotoChangedIfNotNull(String forUserId, final Callback callback) {
        //sendNotifCancelationForProfilePhotoChanged(forUserId);
        removeCallbackIfNotNull(NotificationMessage.Type.PROFILE_PHOTO_CHANGED, callback);
    }

    private static void sendNotifRequestForProfilePhotoChanged(String forUserId) {
        NotifRequestForProfilePhotoChanged request = new NotifRequestForProfilePhotoChanged();
        request.forUserId = forUserId;
        WebSocketConnection.send(JsonEncoder.encode(request));

    }

    @Override
    protected String channelId() {
        return getContext().getString(R.string.notif_channel_id);
    }

    @Override
    protected String channelName() {
        return getContext().getString(R.string.notif_channel_name);
    }

    ObserverList.Observer<SessionDetails> accountSwitchedObserver = new ObserverList.Observer<SessionDetails>() {
        @Override
        public void notifyEvent(SessionDetails eventArgs) {
            new NotificationCache().clear();
            sendOpeningHandshake();
        }
    };

    @Override
    protected void onStartService() {
        InitializeSystem.where(getApplicationContext());

        connectToWebsocketServer();

        AccountSwitchedEvent.instance().remove(accountSwitchedObserver);
        AccountSwitchedEvent.instance().add(accountSwitchedObserver);
    }

    /**
     * an object that can be called back. Difference with listener is that listener is a
     * data structure that stores both types of notifications to listen out for
     * and callback to be notified
     */
    public interface Callback {
        void run(NotificationMessage notificationMessage);
    }

    //the key is the type of notification yo interested in
    private static Map<String, List<Callback>> notificationListeners = new HashMap<>();

    synchronized public static int getTotalCallbacks() {
        int totalCount = 0;
        for (String notifyType : notificationListeners.keySet()) {
            List<Callback> callbackList = notificationListeners.get(notifyType);
            if (callbackList != null) {
                totalCount += callbackList.size();
            }
        }
        return totalCount;
    }

    //makes it easier and less code to subscribe and un-subscribe for notifications
    //when u want the same callback for all notification types provided
    public interface Listener {
        String[] getTypesInterestedIn();

        Callback callback();
    }

    public static void addListenerIfNotNull(Listener listener) {
        if (!listenerValid(listener)) {
            return;
        }
        for (String notificationTypeInterestedIn : listener.getTypesInterestedIn()) {

            addCallbackIfNotNull(notificationTypeInterestedIn, listener.callback());
        }
    }

    public static void removeListenerIfNotNull(Listener listener) {
        if (!listenerValid(listener)) {
            return;
        }
        for (String notificationTypeInterestedIn : listener.getTypesInterestedIn()) {

            removeCallbackIfNotNull(notificationTypeInterestedIn, listener.callback());
        }
    }

    public static boolean listenerValid(Listener listener) {
        return listener != null && listener.getTypesInterestedIn() != null && listener.callback() != null;
    }

    synchronized public static void addCallbackIfNotNull(String notificationTypeInterestedIn, Callback callback) {
        if (callBackInvalid(notificationTypeInterestedIn, callback)) {
            return;
        }
        if (listenerListNotYetCreatedForType(notificationTypeInterestedIn)) {
            createListenerListForType(notificationTypeInterestedIn);
        }
        addListenerToListenerListForType(notificationTypeInterestedIn, callback);
    }

    synchronized public static void removeCallbackIfNotNull(String notificationTypeInterestedIn, Callback callback) {

        if (callBackInvalid(notificationTypeInterestedIn, callback)) {
            return;
        }
        if (notificationListeners.get(notificationTypeInterestedIn) != null) {
            notificationListeners.get(notificationTypeInterestedIn).remove(callback);
            if (notificationListeners.get(notificationTypeInterestedIn).size() < 1) {
                notificationListeners.remove(notificationTypeInterestedIn);
            }
        }

    }

    private static void removeListenerFromListenerListForType(String notificationTypeInterestedIn, Callback callback) {
        if (listenerListNotYetCreatedForType(notificationTypeInterestedIn)) {
            return;
        }
        getListenerListForType(notificationTypeInterestedIn).remove(callback);
        if (getListenerListForType(notificationTypeInterestedIn).size() < 1) {
            removeListForType(notificationTypeInterestedIn);
        }
    }

    private static void removeListForType(String notificationTypeInterestedIn) {
        notificationListeners.remove(notificationTypeInterestedIn);
    }

    private static List<Callback> getListenerListForType(String notificationTypeInterestedIn) {
        return notificationListeners.get(notificationTypeInterestedIn);
    }

    public static boolean callBackInvalid(String notificationTypeInterestedIn, Callback callback) {
        return notificationTypeInterestedIn == null || callback == null;
    }


    private static boolean addListenerToListenerListForType(String notificationTypeInterestedIn, Callback callback) {
        return notificationListeners.get(notificationTypeInterestedIn).add(callback);
    }

    private static void createListenerListForType(String notificationTypeInterestedIn) {
        notificationListeners.put(notificationTypeInterestedIn, new ArrayList<Callback>());
    }

    private static boolean listenerListNotYetCreatedForType(String notificationTypeInterestedIn) {
        return notificationListeners.get(notificationTypeInterestedIn) == null;
    }

    public interface Interceptor{
        void intercept(NotificationMessage notificationMessage);
    }
    public static final List<Interceptor> interceptors = new ArrayList<>();

    private void connectToWebsocketServer() {

        WebSocketConnection.addListener(new WebSocketConnection.EventListener() {
            @Override
            public void onOpen() {
                connected();
            }

            @Override
            public void onClose() {
                Log.d(getClassName(), "Connection lost: Will try to re-establish it shortly");
                /*displayNotification(
                        "Connection lost",
                        "Will try to re-establish it shortly.",
                        MainActivity.class
                );*/

            }

            @Override
            public void onError(String message) {

                Log.d(getClassName(), "Connection error: Will try to re-establish connection");


                /*displayNotification(
                        "Connection error",
                        message+". Will try to re-establish connection",
                        MainActivity.class
                );*/

            }


            @Override
            public void onMessage(String message) {

                final NotificationMessage notificationMessage = JsonEncoder.decode(message, NotificationMessage.class);
                if (notificationMessage == null) {
                    Log.d(getClassName(), "expected a notification message");
                    return;
                }

                EnrichNotificationWithNotificationId.where(notificationMessage);

                //test code to see if family message list receives the message
                for(Interceptor interceptor : interceptors){
                    interceptor.intercept(notificationMessage);
                }

                if (AppIsInForeGround.isTrue() || notificationMessage.isInvisibleInDrawer()) {
                    processInForeground(notificationMessage);
                } else {

                    processInBackground(notificationMessage);


                }

            }

            public void processInBackground(NotificationMessage notificationMessage) {
                if (notificationMessage.getNotificationType().equalsIgnoreCase(NotificationMessage.Type.FAMILY_MESSAGES_SEEN)) {
                    MarkCachedFamilyMessagesAsSeen.where(notificationMessage.getData().get("familyId"));
                } else {
                    SaveNotificationToCache.where(notificationMessage);
                    displayNotificationInTheNotificationDrawer(notificationMessage);
                }
            }

            public void processInForeground(NotificationMessage notificationMessage) {
                if (notificationMessage.getNotificationType().equalsIgnoreCase(NotificationMessage.Type.FAMILY_MESSAGES_SEEN)) {
                    MarkCachedFamilyMessagesAsSeen.where(notificationMessage.getData().get("familyId"));
                }

                NotifyForegroundListeners.where(
                        notificationMessage,
                        notificationListeners,
                        getClassName()
                );

            }
        });

    }

    public void displayNotificationInTheNotificationDrawer(NotificationMessage notificationMessage) {
        DisplayInBoxStyleNotification.where(
                getContext(),
                notificationMessage,
                TargetActivityClassFromNotificationType.where(notificationMessage.getNotificationType())
        );
    }


    private void connected() {
        Log.d(getClassName(), "Connected: waiting for notifications");

        /*displayNotification(
                "Connected",
                "Waiting for notifications...",
                MainActivity.class
        );*/

        sendOpeningHandshake();
    }

    private static void sendOpeningHandshake() {
        Log.d(getClassName(), "Sending opening handshake");
        OpeningHandshake openingHandshake = new OpeningHandshake();
        openingHandshake.setSessionId(LocalSession.instance().getId());
        WebSocketConnection.send(JsonEncoder.encode(openingHandshake));
    }

    private void displayNotification(String title, String message, Class<? extends Activity> onClick) {
        DisplayNotificationMessage.where(
                getContext(),
                1,
                getContext().getString(R.string.notif_channel_id),
                R.mipmap.ic_launcher,
                title,
                message,
                onClick,
                new DisplayNotificationMessage.OnGetSenderIcon() {
                    @Override
                    public Bitmap run() {
                        return null;
                    }
                }

        );
    }

    private void displayNotification(String title, String message, Class<? extends Activity> onClick, final String sendIconURL) {
        DisplayNotificationMessage.where(
                getContext(),
                1,
                getContext().getString(R.string.notif_channel_id),
                R.mipmap.ic_launcher,
                title,
                message,
                onClick,
                new DisplayNotificationMessage.OnGetSenderIcon() {
                    @Override
                    public Bitmap run() {
                        return GetBitmapFromUrl.where(sendIconURL);
                    }
                }

        );
    }

    @Override
    protected void beforeRestart() {
        //inform the user that the service tried to stop but is being restarted
        Log.i("Broadcast Listened", "Service tried to stop");
        Toast.makeText(getContext(), "Service restarted", Toast.LENGTH_SHORT).show();

    }
}
