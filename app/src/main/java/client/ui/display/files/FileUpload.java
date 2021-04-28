package client.ui.display.files;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.util.List;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest2;
import core.businessobjects.FileDetails;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public abstract class FileUpload {

    public FileUpload(Context context, Uri filePath) {

        //MessageBox.show("file path: " + filePath, MainActivity.this);
        String fullFilePath = UriUtils.getPath(context, filePath);
        File file = new File(fullFilePath);
        if (!file.exists()) {
            error("file does not exist: " + file.getAbsolutePath());

        }

        //MessageBox.show("is working",MainActivity.this);


        Rest2.api().uploadFile2(
                MultipartBody.Part.createFormData(
                        "new_image",
                        file.getName(),
                        RequestBody.create(
                                //the value that will be sent in the content type header
                                MediaType.parse("image/*"),
                                file

                        )
                )
                ,
                MultipartBody.Part.createFormData(
                        "sessionId",
                        LocalSession.instance().getId()
                )

        ).enqueue(new AppCallback<List<FileDetails>>() {
            @Override
            protected void onSuccess(Call<List<FileDetails>> call, Response<List<FileDetails>> response) {
                success(response.body().get(0));
            }

            @Override
            protected void onError(Call<List<FileDetails>> call, Throwable t) {
                error(t.getMessage());
            }
        });
    }

    protected abstract void success(FileDetails fileDetails);

    protected abstract void error(String message);
}
