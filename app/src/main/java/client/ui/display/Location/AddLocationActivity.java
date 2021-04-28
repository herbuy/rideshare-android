package client.ui.display.Location;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import client.ui.IntentExtras;
import core.businessobjects.Location;
import libraries.JsonEncoder;
import shared.BaseActivity;

public class AddLocationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Location");
        setContentView(contentView());
    }

    private View contentView() {
        return new FormForAddLocation(this){
            @Override
            protected void onLocationAdded(Location location) {
                Intent data = new Intent();
                data.putExtra(IntentExtras.location, JsonEncoder.encode(location));
                setResult(RESULT_OK, data);
                finish();
            }
        }.getView();
    }
}
