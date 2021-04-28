package client.ui.display.family;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import client.ui.IntentExtras;
import core.businessobjects.Family;
import libraries.JsonEncoder;
import shared.BaseActivity;

public class ReportArrivedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Confirm Completed");
        setContentView(contentView());
    }

    private View contentView() {
        Family family = JsonEncoder.decode(getIntent().getStringExtra(IntentExtras.family), Family.class);
        return new ArrivalConfirmationDialog(this,family).getView();
    }
}
