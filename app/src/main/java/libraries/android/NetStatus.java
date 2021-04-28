package libraries.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * uv seful for detecting changes in network status. For instance, u might want yo app
 * to always update the screen each time the connection is established or re-established
 * after disconnecting. You might also want to avoid yo code from trying to re-peatedly connect
 * yet the internet is off and perhaps run a task once each time the network comes back
 * */

public class NetStatus {

    private static Context context;
    boolean connected = false;
    private List<Listener> listeners = new ArrayList<>();
    private boolean usingMobileData = false;
    private boolean usingWifi = false;

    private NetStatus(Context context) {
        IntentFilter networkFilter = new IntentFilter();
        networkFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(
                new BroadcastReceiver() {
                    //trakcing if connected helps us prevent calling connected twice when already connected


                    @Override
                    public void onReceive(final Context context, final Intent intent) {
                        int status = NetworkUtil.getConnectivityStatusString(context);

                        if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                            connected = false;
                            usingMobileData = false;
                            usingWifi = false;
                            notifyDisconnected();
                        } else {
                            if(connected == false){
                                connected = true;
                                usingMobileData = status == NetworkUtil.NETWORK_STATUS_MOBILE;
                                usingWifi = status == NetworkUtil.NETWORK_STATUS_WIFI;
                                notifyConnected();
                            }

                        }
                    }
                },
                networkFilter
        );


    }


    /** Shd be set at the beginning of the application, prefereably in the main method */
    public static void setContext(Context context) {
        NetStatus.context = context;
    }

    public boolean isConnected() {
        return connected;
    }

    public void addListener(Listener listener){
        listeners.add(listener);
    }
    public void removeListener(Listener listener){
        listeners.remove(listener);
    }
    private void notifyConnected() {
        for(Listener listener : listeners){
            listener.onConnected();
        }
    }
    private void notifyDisconnected(){
        for(Listener listener : listeners){
            listener.onDisconnected();
        }
    }

    private static NetStatus instance_;
    public static NetStatus instance(){
        if(instance_ == null){
            instance_ = new NetStatus(context);
        }
        return instance_;
    }




    public boolean isUsingMobileData() {
        return usingMobileData;
    }

    public boolean isUsingWifi() {
        return usingWifi;
    }


    public interface Listener{
        void onConnected();
        void onDisconnected();
    }

    public static class NetworkUtil {
        public static final int TYPE_WIFI = 1;
        public static final int TYPE_MOBILE = 2;
        public static final int TYPE_NOT_CONNECTED = 0;
        public static final int NETWORK_STATUS_NOT_CONNECTED = 0;
        public static final int NETWORK_STATUS_WIFI = 1;
        public static final int NETWORK_STATUS_MOBILE = 2;

        public static int getConnectivityStatus(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI;

                if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE;
            }
            return TYPE_NOT_CONNECTED;
        }

        public static int getConnectivityStatusString(Context context) {
            int conn = NetworkUtil.getConnectivityStatus(context);
            int status = 0;
            if (conn == NetworkUtil.TYPE_WIFI) {
                status = NETWORK_STATUS_WIFI;
            } else if (conn == NetworkUtil.TYPE_MOBILE) {
                status = NETWORK_STATUS_MOBILE;
            } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
                status = NETWORK_STATUS_NOT_CONNECTED;
            }
            return status;
        }
    }
}
