package client.ui.display.users.profile_photo;

import androidx.annotation.NonNull;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import client.ui.display.files.FileUpload;
import client.ui.display.files.ImageGrid;
import client.ui.display.users.ProfilePhotoChangedEvent;
import core.businessobjects.FileDetails;
import layers.render.Atom;
import libraries.android.ImagesOnDevice;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.android.ObjectTaskPermit;
import libraries.underscore.Singleton;
import resources.Dp;
import resources.ItemColor;
import shared.BaseActivity;

public class ChangeProfilePictureActivity extends BaseActivity {

    Singleton<ObjectTaskPermit> fileReadWritePermit = new Singleton<ObjectTaskPermit>() {
        @Override
        protected ObjectTaskPermit onCreate() {
            return new ObjectTaskPermit(ChangeProfilePictureActivity.this).addGroupReadWriteExternalStorage();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fileReadWritePermit.instance().requestThenRun(new ObjectTaskPermit.Callback() {
            @Override
            public void ifAllGranted() {
                setContentView(contentView());
            }

            @Override
            public void ifSomeDenied() {
                setContentView(permissionsDeniedView());
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        fileReadWritePermit.instance().processPermissionsResult(requestCode,permissions,grantResults);
    }


    private View permissionsDeniedView() {
        return Atom.centeredText(this,"You need to grant file permissions to upload a profile photo");
    }

    private View contentView() {
        return MakeDummy.linearLayoutVertical(
                this,
                title(),
                list()
        );
    }

    private View list() {

        ImageGrid imageGrid = new ImageGrid(this) {
            @Override
            protected void onImageClick(View view, ImagesOnDevice.ImageModel model) {
                uploadingProfilePhoto(model);
            }
        };
        return imageGrid.getView();
    }

    private void uploadingProfilePhoto(ImagesOnDevice.ImageModel model) {
        title.setText("Uploading...");
        try{
            new FileUpload(this,Uri.fromFile(new File(model.getImage()))){
                @Override
                protected void success(FileDetails fileDetails) {
                    title.setText("Success!!");
                    revertToDefaultText();
                    finish();
                    ProfilePhotoChangedEvent.instance().notifyObservers(fileDetails);
                }

                @Override
                protected void error(String message) {
                    title.setText("Failed!!");
                    showError(message);
                    revertToDefaultText();
                }
            };
        }
        catch (Exception ex){
            showError(ex.getMessage());
        }
    }

    private void revertToDefaultText() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                title.setText(defaultTitle());
            }
        }, 5000);
    }

    private String defaultTitle() {
        return "Choose your profile photo";
    }

    private void showError(String message) {
        MessageBox.show(message,this);
        Log.e("UPLOAD ERROR",message);
    }

    TextView title;
    private View title() {
        title = Atom.textViewPrimaryBold(this,defaultTitle());
        title.setBackgroundColor(ItemColor.primary());
        title.setTextColor(Color.WHITE);
        title.setPadding(Dp.one_point_5_em(),Dp.normal(),Dp.one_point_5_em(),Dp.normal());
        return title;
    }


}
