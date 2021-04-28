package client.ui.display.files;

import android.content.Context;
import android.util.Log;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.skyvolt.jabber.R;
import com.squareup.picasso.Picasso;

import shared.MakeFilePath;

public class LoadImage {
    public static void fromFileShortName(ImageView imageView, String fileShortName) {
        if(fileShortName == null || fileShortName.trim().equalsIgnoreCase("")){
            imageView.setImageResource(R.drawable.ic_profile_no_image);
        }
        else{

            String fullPath = MakeFilePath.fromFileShortName(fileShortName);

            //Bitmap bitmap = GetBitmapFromUrl.where(fullPath);
            //imageView.setImageBitmap(bitmap);

            //new GetImageFromUrl(imageView).execute(fullPath);




            /*Log.d("             IMAGE PATH",fullPath);
            Picasso
                    .with(imageView.getContext())
                    .load(fullPath)
                    .centerCrop()
                    .fit()
                    .into(imageView);*/

            try{
                Glide
                        .with(imageView.getContext())
                        .load(fullPath)
                        .centerCrop()
                        .placeholder(R.drawable.ic_profile_no_image)
                        .into(imageView);
            }
            catch (Exception ex){
                Log.e(LoadImage.class.getName(),ex.getMessage());
            }
        }



    }

    public static ImageView fromFileShortName(Context context,String fileShortName) {
        ImageView imageView = new ImageView(context);
        String fullPath = MakeFilePath.fromFileShortName(fileShortName);

        Log.d("             IMAGE PATH", fullPath);
        Picasso
                .with(imageView.getContext())
                .load(fullPath)
                .centerCrop()
                .fit()
                .into(imageView);

        return imageView;

    }


}
