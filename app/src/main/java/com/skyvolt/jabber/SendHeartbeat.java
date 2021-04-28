package com.skyvolt.jabber;

import android.os.Handler;
import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest2;
import core.businessmessages.toServer.ParamsForSendHeartbeat;
import core.businessobjects.Heartbeat;
import retrofit2.Call;
import retrofit2.Response;

public class SendHeartbeat {

    public static void start() {
        if(1 == 1){
            return;
        }

        final Handler handler = new Handler();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        log("sending heart beat");
                        Rest2.api().sendHeartbeat(params()).enqueue(new AppCallback<List<Heartbeat>>() {
                            @Override
                            protected void onSuccess(Call<List<Heartbeat>> call, Response<List<Heartbeat>> response) {
                                log("heart beat sent");
                            }

                            @Override
                            protected void onError(Call<List<Heartbeat>> call, Throwable t) {
                                log("failed: "+ t.getMessage());
                            }
                        });

                    }
                });


            }
        }, 5000, 10000);
    }

    private static void log(String message) {
        Log.d(SendHeartbeat.class.getSimpleName(),message);
    }

    private static ParamsForSendHeartbeat params() {
        ParamsForSendHeartbeat params = new ParamsForSendHeartbeat();
        params.setSessionId(LocalSession.instance().getId());
        return params;
    }
}
