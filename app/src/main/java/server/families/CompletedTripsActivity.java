package server.families;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import shared.BaseActivity;

public class CompletedTripsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Completed Trips");
        setContentView(new CompletedTripsList(this).getView());
    }
}
