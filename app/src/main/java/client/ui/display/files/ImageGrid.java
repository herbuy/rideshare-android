package client.ui.display.files;

import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.List;

import libraries.android.ImagesOnDevice;

public abstract class ImageGrid {
    private GridLayout layout;
    private ScrollView scrollView;
    public ImageGrid(AppCompatActivity context) {
        scrollView = new ScrollView(context);
        layout = new GridLayout(context);
        layout.setColumnCount(3);
        scrollView.addView(layout);

        List<ImagesOnDevice.ImageModel> files = ImagesOnDevice.getAll(context);
        for (final ImagesOnDevice.ImageModel model : files) {

            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            int dim = context.getResources().getDisplayMetrics().widthPixels / 3;
            imageView.setLayoutParams(new LinearLayout.LayoutParams(dim, dim));
            imageView.setPadding(1,1,1,1);
            layout.addView(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageGrid.this.onImageClick(view, model);
                }
            });

            Glide
                    .with(context)
                    .load(model.getImage())
                    .centerCrop()
                    //.placeholder(R.drawable.loading_spinner)
                    .into(imageView);


        }
    }

    public View getView(){
        return scrollView;
    }

    protected abstract void onImageClick(View view, ImagesOnDevice.ImageModel model);
}
