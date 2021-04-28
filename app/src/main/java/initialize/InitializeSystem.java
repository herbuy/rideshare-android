package initialize;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.skyvolt.jabber.R;

import client.data.LocalSession;
import client.ui.AccountSwitchedEvent;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.display.notification.NotificationService;
import client.ui.display.users.ProfilePhotoChangedEvent;
import config.DomainNameOrIPAndPort;
import core.businessobjects.FileDetails;
import core.businessobjects.SessionDetails;
import layers.render.Atom;
import libraries.JsonEncoder;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.android.NetStatus;
import libraries.ObserverList;
import libraries.SecondsSinceEpoch;
import libraries.android.ServiceIsRunning;
import libraries.android.WebSocketConnection;
import libraries.data.LocalDatabase;
import libraries.android.HerbuyDevice;
import libraries.android.TabInterceptor;
import resources.ItemColor;

public class InitializeSystem {
    public static void where(final Context context) {

        FrameLayoutBasedHerbuyStateView.setOptions(new FrameLayoutBasedHerbuyStateView.Options() {
            @Override
            public View errorView(String message) {
                message = preProcessErrorMessage(message);
                return MakeDummy.wrapContent(Atom.textView(context, message));
            }

            @Override
            public Button retryButton(Context context) {
                Button btn = Atom.button(context, "Retry", null);
                MakeDummy.wrapContent(btn);
                return btn;
            }

            private String preProcessErrorMessage(String message) {

                if (!message.contains("failed to connect")) {
                    return message;
                }
                return "Ooops... server unreachable";
            }
        });

        createNotificationChannel(context);

        //subscribeToFirebase(context);

        NetStatus.setContext(context);

        SecondsSinceEpoch.setImplementation(new SecondsSinceEpoch.Implementation() {
            @Override
            public long get() {
                return System.currentTimeMillis() / 1000;
            }
        });

        LocalSession.setContext(context);

        LocalDatabase.setContext(context);

        ItemColor.setContext(context);

        HerbuyDevice.setContext(context);

        TabInterceptor.setUp(
                Color.WHITE,
                Color.WHITE,
                6,
                Color.WHITE
        );

        initializeJsonEncoder();

        //set up some observers here
        AccountSwitchedEvent.instance().add(new ObserverList.Observer<SessionDetails>() {
            @Override
            public void notifyEvent(SessionDetails sessionDetails) {
                LocalSession.instance().save(sessionDetails);
            }
        });


        ProfilePhotoChangedEvent.instance().add(new ObserverList.Observer<FileDetails>() {
            @Override
            public void notifyEvent(FileDetails eventArgs) {
                LocalSession.instance().setProfilePhoto(eventArgs.getShortNameWithExtension());
            }
        });

        //SendHeartbeat.start();

        startNotificationService(context);

        WebSocketConnection.initialize(
                String.format("ws://%s/RideServer/chat", DomainNameOrIPAndPort.get())
        );


    }

    public static void startNotificationService(Context context) {
        if (!ServiceIsRunning.where(context, NotificationService.class)) {
            context.startService(new Intent(context, NotificationService.class));
        }
    }

    private static void subscribeToFirebase(final Context context) {
        //FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseMessaging.getInstance().subscribeToTopic("news").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    MessageBox.show("Subscribed successfully", context);
                } else {
                    MessageBox.show("subscription failed", context);
                }
            }
        });
    }

    private static void initializeJsonEncoder() {
        JsonEncoder.setParameters(new JsonEncoder.Parameters() {
            Gson gson = new Gson();

            public String encode(Object object) {
                return gson.toJson(object);
            }

            public <T> T decode(String json, Class<T> classOfT) {
                try {
                    return gson.fromJson(json, classOfT);
                } catch (Throwable ex) {
                    Log.d("GSON ERROR", ex.getMessage());
                    return null;
                }
            }
        });
    }

    private static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(context.getString(R.string.notif_channel_id), context.getString(R.string.notif_channel_name), importance);
            channel.setDescription(context.getString(R.string.channel_description));
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
