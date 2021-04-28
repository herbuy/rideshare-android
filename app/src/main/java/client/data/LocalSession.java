package client.data;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import core.businessobjects.SessionDetails;
import core.businessobjects.Trip;
import libraries.JsonEncoder;

public class LocalSession {
    private SessionDetails sessionDetails;


    private static LocalSession instance_ = null;

    public static LocalSession instance() {

        if (instance_ == null) {
            instance_ = new LocalSession();
        }
        return instance_;
    }
    private static Context context;

    public static void setContext(Context context) {
        LocalSession.context = context;
    }

    private LocalSession() {
        try {
            sessionDetails = retrieve("session_details", SessionDetails.class);
            if(sessionDetails == null){
                sessionDetails = new SessionDetails();
            }


        } catch (Exception ex) {
            Log.d("Local session: ","could not load session, reason: " + ex.getMessage());
        }

    }

    public <T> T retrieve(String key, Class<T> classOfT, T defaultValue){
        try{
            return JsonEncoder.decode(
                    getDictionary().getString(key, ""),
                    classOfT
            );
        }
        catch (Exception ex){
            return defaultValue;
        }
    }
    public <T> T retrieve(String key, Class<T> classOfT){
        return retrieve(key,classOfT,null);
    }

    private SharedPreferences getDictionary() {
        return context.getSharedPreferences("session_details", Context.MODE_PRIVATE);
    }

    public void save(SessionDetails newSessionDetails) {

        try {
            sessionDetails = newSessionDetails;
            SharedPreferences.Editor editor = getEditor();
            editor.putString("session_details", JsonEncoder.encode(newSessionDetails));
            editor.commit();
            Log.d("Local session: ","session id saved!!");
        } catch (Exception ex) {
            Log.d("Local session: ","failed to save session details, reason: " + ex.getMessage());
            //try again later
        }
    }

    private SharedPreferences.Editor getEditor() {
        SharedPreferences sharedpreferences = getDictionary();
        return sharedpreferences.edit();
    }

    public Boolean exists() {
        return sessionDetails.exists();
        //return null != sessionDetails;
    }

    public String getId() {
        return sessionDetails.getSessionId();
    }

    public String getUserId() {
        return sessionDetails.getUserId();
    }


    public String getUserProfilePic() {
        return sessionDetails.getUserProfilePic();
    }

    public String getOwnerType() {
        return sessionDetails.getOwnerType();
    }

    public String getOwnerAccountNumber() {
        return sessionDetails.getMobileNumber();
    }

    public SessionDetails getDetails() {
        return sessionDetails;
    }

    public void setLastTripScheduled(Trip lastTripScheduled) {
        save("last_trip_scheduled",lastTripScheduled);
    }
    public Trip getLastTripScheduled(Trip defaultValue){
        return retrieve("last_trip_scheduled",Trip.class,defaultValue);
    }

    private void save(String key,Object value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, JsonEncoder.encode(value));
        editor.commit();
    }


    public void setProfilePhoto(String shortNameWithExtension) {
        if(sessionDetails != null){
            sessionDetails.setUserProfilePic(shortNameWithExtension);
            save(sessionDetails);
        }
    }
}

