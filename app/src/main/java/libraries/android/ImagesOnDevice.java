package libraries.android;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImagesOnDevice {
    public static List<ImageModel> getAll(AppCompatActivity context) {
        List<ImageModel> imageList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            try {
                String absolutePathOfImage = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                ImageModel ImageModel = new ImageModel();
                ImageModel.setImage(absolutePathOfImage);
                imageList.add(ImageModel);
            } catch (Exception ex) {
                Log.e(ImagesOnDevice.class.getSimpleName(), ex.getMessage());
            }
        }
        cursor.close();
        return imageList;
    }

    public static class ImageModel {

        String image;
        String title;
        int resImg;
        boolean isSelected;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getResImg() {
            return resImg;
        }

        public void setResImg(int resImg) {
            this.resImg = resImg;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }


    }

    /** can be used to load an image asynchronously */
    private static class LoadImageTask extends AsyncTask<ImageModel, Bitmap, Bitmap> {
        @Override
        protected Bitmap doInBackground(ImagesOnDevice.ImageModel... imageModels) {
            File image = new File(imageModels[0].getImage());
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
            return bitmap;
        }

        @Override
        protected final void onPostExecute(Bitmap bitmap) {
            if (onSuccess != null) {
                onSuccess.run(bitmap);
            }
        }

        private OnSuccess onSuccess;

        public final LoadImageTask setOnSuccess(OnSuccess onSuccess) {
            this.onSuccess = onSuccess;
            return this;
        }

        public interface OnSuccess {
            void run(Bitmap bitmap);
        }
    }

}
