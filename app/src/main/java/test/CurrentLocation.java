package test;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class CurrentLocation {

    public interface Callback{

        void locationChanged(Location location, LocationManager mgr, LocationListener listener);
        void providerDisabled();
    }


    @SuppressWarnings("MissingPermission")
    private static void get(final Context context, final boolean useGPS, final boolean once, final Callback callback){

        final LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(once){
                    mgr.removeUpdates(this);
                }
                callback.locationChanged(location, mgr,this);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                callback.providerDisabled();
            }
        };

        Location lastKnownLocation = mgr.getLastKnownLocation(useGPS ? LocationManager.GPS_PROVIDER : LocationManager.NETWORK_PROVIDER);
        if(lastKnownLocation != null ){
            listener.onLocationChanged(lastKnownLocation);
            if(once){
                return;
            }
        }

        if(useGPS){
            mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        }
        else{
            mgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        }

    }

    public static void getOnceUsingNetworkProvider(Context context, Callback callback){
        get(context,false,true,callback);
    }
    public static void getOnceUsingGPS(Context context, Callback callback){
        get(context,true,true,callback);
    }

    public static void getPeriodicUsingNetworkProvider(Context context, Callback callback){
        get(context,false,false,callback);
    }

    public static void getPeriodicUsingGPS(Context context, Callback callback){
        get(context,true,false,callback);
    }
}
