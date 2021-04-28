package libraries.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetBitmapFromUrl {
    public static Bitmap where(String urlString) {
        if(urlString == null || urlString.trim().equalsIgnoreCase("")){
            Log.d(getClassName(),"URL not specified");
            return null;
        }
        urlString = urlString.trim();

        try{
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        catch (Exception ex){
            Log.e(getClassName(),"failed to load bitmap: "+urlString);
            return null;
        }

    }

    private static String getClassName() {
        return GetBitmapFromUrl.class.getSimpleName();
    }
}
