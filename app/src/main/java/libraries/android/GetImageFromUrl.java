package libraries.android;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    public GetImageFromUrl(ImageView img){
        this.imageView = img;
    }
    private Bitmap bitmap;
    @Override
    protected Bitmap doInBackground(String... url) {
        String stringUrl = url[0];
        bitmap = null;
        InputStream inputStream;
        try {
            inputStream = new java.net.URL(stringUrl).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap){
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
    }
}