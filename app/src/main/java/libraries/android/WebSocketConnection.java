package libraries.android;


import android.os.Handler;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class WebSocketConnection {

    private static WebSocketClient wsClient;
    private static String urlString = "";
    public static void initialize(String urlString0) {
        urlString = urlString0;

        connect();

    }

    private static void connect() {
        try{
            wsClient = new WebSocketClient(new URI(urlString),new Draft_17()) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    notifyOpenHandlers();
                }

                @Override
                public void onMessage(String message) {
                    notifyMessageHandlers(message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    reConnectAfter(5000);
                    notifyCloseHandlers();
                }

                @Override
                public void onError(Exception ex) {
                    reConnectAfter(5000);
                    notifyErrorHandlers(ex.getMessage());

                }
            };
            wsClient.connect();
        }
        catch (Exception ex){
            notifyErrorHandlers(ex.getMessage());
        }
    }

    private static void reConnectAfter(long millis) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                connect();
            }
        }, millis);
    }



    private static Handler handler = new Handler();

    private static void notifyOpenHandlers() {
        for(final EventListener listener: listeners){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onOpen();
                }
            });
        }
    }

    private static void notifyCloseHandlers() {
        for(final EventListener listener: listeners){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onClose();
                }
            });
        }
    }

    private static void notifyErrorHandlers(final String message) {
        for(final EventListener listener: listeners){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onError(message);
                }
            });
        }
    }

    private static void notifyMessageHandlers(final String message) {
        for(final EventListener listener: listeners){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onMessage(message);
                }
            });
        }
    }


    private static List<EventListener> listeners = new ArrayList<>();

    synchronized public static void addListener(EventListener eventListener) {
        listeners.add(eventListener);
    }

    public interface EventListener{
        void onOpen();
        void onClose();
        void onError(String message);
        void onMessage(String message);
    }

    public static void send(String message){
        if(wsClient != null){
            try{
                wsClient.send(message);
            }
            catch (Exception ex){
                Log.d(getClassName(),"could not send message: "+ex.getMessage());
            }
        }
    }

    public static String getClassName() {
        return WebSocketConnection.class.getSimpleName();
    }
}
