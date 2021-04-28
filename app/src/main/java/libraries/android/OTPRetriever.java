package libraries.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OTPRetriever {

    public interface Parameters{
        Activity activity();

        void onFindOtp(String otp);

        void otpNotFoundInMessage();

        void onMessageNullOrEmpty();

        void onStatusNull();

    }

    private Parameters parameters;
    public OTPRetriever(Parameters parameters) {
        this.parameters = parameters;

        SmsRetriever.getClient(parameters.activity()).startSmsRetriever();
        parameters.activity().registerReceiver(smsReceiver, new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION));

    }

    public void onDestroy() {
        parameters.activity().unregisterReceiver(smsReceiver);
        smsReceiver = null;
    }

    private BroadcastReceiver smsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(broadcastActionSaysSmsRetrieved(intent)){
                Status status = (Status) intent.getExtras().get(SmsRetriever.EXTRA_STATUS);
                if(status == null){
                    parameters.onStatusNull();
                    return;
                }

                if(status.getStatusCode() == CommonStatusCodes.SUCCESS){

                    String sms = intent.getExtras().getString(SmsRetriever.EXTRA_SMS_MESSAGE);
                    if(sms == null || sms.trim().equalsIgnoreCase("")){
                        parameters.onMessageNullOrEmpty();
                        return;
                    }

                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(sms);
                    if(! m.find()){
                        parameters.otpNotFoundInMessage();
                        return;
                    }

                    String otp = m.group();
                    parameters.onFindOtp(otp);
                }

            }
        }

        private boolean broadcastActionSaysSmsRetrieved(Intent intent) {
            return intent.getAction().equalsIgnoreCase(SmsRetriever.SMS_RETRIEVED_ACTION);
        }
    };
}
