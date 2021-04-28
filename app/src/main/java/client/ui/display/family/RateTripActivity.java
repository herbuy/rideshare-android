package client.ui.display.family;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import client.ui.IntentExtras;
import core.businessobjects.Family;
import libraries.JsonEncoder;
import shared.BaseActivity;

public class RateTripActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Rate Trip");
        setContentView(contentView());
    }

    private View contentView() {
        Family family = JsonEncoder.decode(getIntent().getStringExtra(IntentExtras.family), Family.class);
        return new RateTripDialog(this,family).getView();
    }
}
