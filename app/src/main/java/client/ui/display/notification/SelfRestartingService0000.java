package client.ui.display.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public abstract class SelfRestartingService0000 extends Service {
    public int counter=0;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground()
    {
        NotificationChannel chan = createAndConfigureChannel();;
        registerChannel(chan);

        startForeground(1, createAndConfigureNotif());
    }

    private Notification createAndConfigureNotif() {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                this,
                channelId()
        );
        return notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
    }

    protected abstract String channelId();

    @RequiresApi(Build.VERSION_CODES.O)
    private void registerChannel(NotificationChannel chan) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private NotificationChannel createAndConfigureChannel() {
        NotificationChannel chan = new NotificationChannel(channelId(), channelName(), NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        //whether the channel shd be visible on the lock screen
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        return chan;
    }

    protected abstract String channelName();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        onStartService();
        return START_STICKY;
    }

    protected abstract void onStartService();

    protected static String getClassName() {
        return SelfRestartingService0000.class.getSimpleName();
    }


    @Override
    public final void onDestroy() {
        super.onDestroy();
        this.sendBroadcast(restart(this));
    }

    private static final String ACTION_RESTART = "restartservice";
    public static final Intent restart(Context context) {
        Intent restartIntent = new Intent();
        restartIntent.setAction(ACTION_RESTART);
        restartIntent.setClass(context, RestartBroadcastReceiver.class);
        return restartIntent;
    }

    protected final Context getContext(){
        return SelfRestartingService0000.this;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //restarts a service upon notification that it has been destroyed
    public class RestartBroadcastReceiver extends BroadcastReceiver {

        //like every broadcast receiver, it must provide instructions to execute when the broad cast is received
        @Override
        public void onReceive(Context context, Intent intent) {
            SelfRestartingService0000.this.beforeRestart();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, NotificationService.class));
            } else {
                context.startService(new Intent(context, NotificationService.class));
            }
        }
    }

    protected abstract void beforeRestart();
}
