
package firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import libraries.android.MessageBox;

public class FirebaseMessagingService2 extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("FIRE BASE TOKEN", s);

        //hey, i have a token for your app. who is interested
        //send token to server [
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        Log.d("________FIREB MSG","MESSAGE RECEIVED");
        new android.os.Handler().post(new Runnable() {
            @Override
            public void run() {
                MessageBox.show("Message Received",getApplicationContext());
            }
        });

    }
}
