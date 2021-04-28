package characters;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class BusDisplayScript {
    public static void run(final Activity activity) {
        BusDownloadScript.run(new DownloadFinishedListener<List<BusData>>() {
            @Override
            public void onSuccess(List<BusData> responseBody) {
                activity.setContentView(BusGridDisplayScript.run(responseBody));
            }

            @Override
            public void onError(String error) {
                Toast.makeText(activity,error,Toast.LENGTH_SHORT).show();
            }
        });


    }

}
