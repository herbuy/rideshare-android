package client.ui.display.Location.location_picker;

import android.content.Context;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import java.util.List;
import java.util.UUID;

import client.ui.display.Location.GetLocationFromAddress;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Location;
import layers.render.Atom;
import libraries.android.AddressListFromString;
import libraries.android.CSVFromAddress;
import libraries.android.MakeDummy;
import libraries.android.SquaredImageVIew;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;

public abstract class ResultAreaForLocationPicker implements HerbuyView {

    Context context;
    LinearLayout resultArea;

    public ResultAreaForLocationPicker(Context context) {
        this.context = context;
        init();
        showPreviousSearches();
    }

    private void showPreviousSearches() {
        List<AddressListFromString.Address> previousAddresses = new AddressSearchHistory().selectAll();
        displayAddresses(previousAddresses);
    }

    private void init() {
        resultArea = MakeDummy.linearLayoutVertical(context);
        resultArea.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public View getView() {
        return resultArea;
    }

    public void displayAddresses(List<AddressListFromString.Address> addresses) {
        TransitionManager.beginDelayedTransition(resultArea);
        resultArea.removeAllViews();
        for(AddressListFromString.Address address : addresses){
            resultArea.addView(addressView(address));
        }
    }



    private View addressView(final AddressListFromString.Address address) {
        CSVFromAddress csvFromAddress = new CSVFromAddress(address);
        String text = "No text";
        if(csvFromAddress.notEmpty()){
            text = csvFromAddress.getText();
        }
        TextView addressText = addressText(address, text);
        ImageView icon = addressIcon();
        RelativeLayout wrapper = RelativeLayoutFactory.alignRightOfWidget(icon, addressText);
        wrapper.setClickable(true);
        wrapper.setBackgroundResource(R.drawable.selectable_item);
        wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!new AddressSearchHistory().hasAddress(address)){
                    new AddressSearchHistory().save(UUID.randomUUID().toString(),address);

                }
                final Location selectedLocation = GetLocationFromAddress.where(address);
                onLocationSelected(selectedLocation);


            }
        });
        return wrapper;
    }

    private ImageView addressIcon() {
        ImageView icon = new SquaredImageVIew(context);
        icon.setImageResource(R.drawable.bmp_ic_location);
        icon.setLayoutParams(new ViewGroup.LayoutParams(Dp.normal(), Dp.normal()));
        icon.setAlpha(0.5f);
        return icon;
    }

    private TextView addressText(final AddressListFromString.Address address, String text) {
        TextView addressText = Atom.textViewSecondary(context, text);
        addressText.setPadding(0, Dp.one_point_5_em(), 0, Dp.one_point_5_em());
        addressText.setBackgroundResource(R.drawable.border_bottom);
        addressText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return addressText;
    }

    protected abstract void onLocationSelected(Location selectedLocation);

    public void indicateError(String error) {
        TransitionManager.beginDelayedTransition(resultArea);
        resultArea.removeAllViews();
        resultArea.addView(errorView(error));

    }

    private View errorView(String error) {
        TextView errorView = Atom.textViewSecondary(context,error);
        errorView.setPadding(0, Dp.normal(), 0, Dp.normal());
        return errorView;
    }
}
