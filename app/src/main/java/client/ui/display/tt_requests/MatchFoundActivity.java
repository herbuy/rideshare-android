package client.ui.display.tt_requests;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import layers.render.Atom;
import shared.BaseActivity;

public class MatchFoundActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MATCH FOUND");
        setContentView(contentView());
    }

    private View contentView() {
        //we can either receive this info my REST or websocket
        //matchfound(forTripId, detailsId) -- show details of each trip to see where they match and percentage match
        return Atom.centeredText(this,"MATCH FOUND !!");
    }
}
