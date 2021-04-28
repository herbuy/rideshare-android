package client.ui.display.Location;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import client.ui.IntentExtras;
import client.ui.display.Location.location_picker.LocationPicker;
import core.businessobjects.Location;
import libraries.JsonEncoder;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;
import resources.strings.TransitionName;
import shared.BaseActivity;

public class SelectLocationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Select Location");
        setContentView(contentView());
    }

    private View contentView() {
        //can modify ui here in future to include other elements before or after picker e.g cancel button
        View locationPicker = createLocationPicker();
        locationPicker.setTransitionName(TransitionName.location);

        return MakeDummy.linearLayoutVertical(
                this,
                title(),
                locationPicker
        );

    }

    private TextView title() {

        String title = getIntent().getStringExtra(IntentExtras.title);
        title = title == null ? "Choose Address" : title;

        TextView textView = Atom.textViewPrimaryBold(this, String.format("<big>%s</big>",title));
        textView.setPadding(Dp.two_em(), Dp.two_em(),Dp.two_em(),Dp.two_em());
        textView.setBackgroundResource(R.drawable.header_background);
        return textView;
    }

    private View createLocationPicker() {
        LocationPicker picker = new LocationPicker(this){
            @Override
            public void onLocationSelected(Location location) {
                //MessageBox.show("Location: "+ JsonEncoder.encode(location),SelectLocationActivity.this);
                //Log.d("SELECTED LOCATION", JsonEncoder.encode(location));

                setResult(RESULT_OK, makeResultIntent(location));
                finish();

            }

            private Intent makeResultIntent(Location location) {
                Intent data = new Intent();
                data.putExtra(IntentExtras.selectedLocation,JsonEncoder.encode(location));
                return data;
            }
        };
        return picker.getView();

    }
}
