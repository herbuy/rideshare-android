package client.ui.display.files;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class FileSelector {

    public Intent getRequestIntent() {
        Intent myIntent = new Intent(
                Intent.ACTION_GET_CONTENT
        );
        myIntent.setType("*/*");
        return myIntent;
    }

    public final int getRequestCode() {
        return 10000;
    }


    public interface OnCancel {
        void onCancel(FileSelector sender);
    }

    private OnCancel onCancel;

    public void setOnCancel(OnCancel onCancel) {
        this.onCancel = onCancel;
    }

    public void notifyCancelled() {
        if (onCancel != null) {
            onCancel.onCancel(this);
        }
    }

    public interface FileSelected {
        void fileSelected(FileSelector sender, Uri filePath);
    }

    private FileSelected fileSelected;

    public void setFileSelected(FileSelected fileSelected) {
        this.fileSelected = fileSelected;
    }

    public void notifyFileSelected(Uri filePath) {
        if (fileSelected != null && filePath != null) {
            fileSelected.fileSelected(this, filePath);
        }
    }

    public void processActivityResult(int requestCode, int resultCode, Intent data, int RESULT_OK) {
        if (requestCode == getRequestCode()) {
            if (resultCode == RESULT_OK) {

                notifyFileSelected(data.getData());

            } else {
                notifyCancelled();
            }
        }


    }

    public void startActivityForResult(Activity activity){
        activity.startActivityForResult(getRequestIntent(), getRequestCode());
    }


}

