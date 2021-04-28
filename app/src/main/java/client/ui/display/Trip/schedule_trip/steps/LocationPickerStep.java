package client.ui.display.Trip.schedule_trip.steps;

import android.content.Context;
import android.text.Editable;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import client.ui.display.Location.CSVFromLocation;
import client.ui.display.Location.GetLocationFromAddress;
import client.ui.display.Location.GeoLocationTask;
import client.ui.display.Location.location_picker.LocationCache;
import client.ui.display.Location.location_picker.LocationPicker;
import client.ui.display.Trip.schedule_trip.pickers.ItemPicker;
import client.ui.libraries.Form;
import core.businessobjects.Location;
import layers.render.Atom;
import libraries.android.AddressListFromString;
import libraries.android.HideKeyboard;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.android.TextChangedListener;
import resources.Dp;

public abstract class LocationPickerStep extends TripSchedulingStep implements Form.Step {
    protected final Context context;

    public LocationPickerStep(Context context) {
        this.context = context;
    }

    protected LocationPickerStep getThis(){
        return this;
    }


    @Override
    public final View getAnswerArea() {

        return MakeDummy.linearLayoutVertical(
                context,
                searchArea(),
                MakeDummy.scrollView(listArea())
        );
        //return createLocationPicker();
    }

    LinearLayout itemList;
    private View listArea() {
        itemList = MakeDummy.linearLayoutVertical(context);
        mediator.refreshItemList();
        return itemList;
    }


    LinearLayout searchArea;
    private View searchArea() {
        searchArea = MakeDummy.linearLayoutVertical(context, searchBox(), searchIndicator());
        searchArea.setPadding(paddingHorizontal(),0,paddingHorizontal(),0);
        return searchArea;
    }

    private int paddingHorizontal() {
        return Dp.one_point_5_em();
    }

    TextView statusIndicator;
    private View searchIndicator() {
        statusIndicator = Atom.textView(context,"Searching...");
        statusIndicator.setVisibility(View.GONE);
        return statusIndicator;
    }

    EditText searchBox;

    private View searchBox() {
        searchBox = Atom.editText(context, searchPrompt(),mediator.textChanged());
        return searchBox;
    }

    private String searchPrompt() {
        return "Type name of a location";
    }

    public String getDisplayText(Location item) {
        return new CSVFromLocation(item).getText();
    }

    public List<Location> getData() {
        List<Location> data = new LocationCache().selectAll();
        Collections.sort(data, comparator());
        return data;
    }


    private Comparator<? super Location> comparator() {
        return new Comparator<Location>() {
            @Override
            public int compare(Location first, Location second) {
                return Long.compare(second.getTimeStampLastModifiedInSeconds(),first.getTimeStampLastModifiedInSeconds());
            }
        };
    }

    public void selectFailed(String reason) {
        MessageBox.show(reason,context);
    }

    private interface Mediator{
        TextChangedListener textChanged();
        void searchResultClicked(Location location);

        void refreshItemList();

    }

    public void locationSelected(Location item) {

        HideKeyboard.run(searchBox);
    }

    ItemPicker<Location> locationSelector;

    protected final ItemPicker<Location> getLocationPicker(){
        return locationSelector;
    }

    private Mediator mediator = new Mediator() {
        @Override
        public void refreshItemList() {
            itemList.removeAllViews();
            itemList.addView(listContent());

        }



        private View listContent() {
            locationSelector = makeLocationPicker();
            return locationSelector.getView();
        }



        @Override
        public TextChangedListener textChanged() {
            return new TextChangedListener() {
                @Override
                public void afterTextChanged(Editable editable) {
                    String locationName = editable.toString();
                    if(locationName.length() < 3){
                        return;
                    }
                    geoLocator.search(locationName);

                }
            };
        }

        GeoLocationTask geoLocator = new GeoLocationTask() {
            @Override
            protected Context getContext() {
                return context;
            }

            @Override
            protected void beforeGeolocate() {
                showSearchIndicator();
            }

            @Override
            protected void AfterGeolocate(AddressListFromString addressListFromString) {
                hideSearchIndicator();
                showResults(addressListFromString);
            }

        };

        @Override
        public void searchResultClicked(Location location) {
            cacheLocationIfNecessary(location);
            searchBox.setText("");
            refreshItemList();
            locationSelector.selectFirstItem();


        }


    };

    protected abstract ItemPicker<Location> makeLocationPicker();

    private void cacheLocationIfNecessary(Location location) {
        LocationCache locationCache = new LocationCache();
        locationCache.deleteLocation(location);

        location.setId(UUID.randomUUID().toString());
        location.setTimeStampLastModifiedInSeconds(System.currentTimeMillis());
        locationCache.save(location.getId(),location);
    }

    private void showResults(AddressListFromString addressListFromString) {

        if(addressListFromString.hasError()){
            showError(addressListFromString.getError());

        }
        else{
            doShowResults(addressListFromString);
        }
    }

    private void doShowResults(AddressListFromString addressListFromString) {
        itemList.removeAllViews();

        for(AddressListFromString.Address address : addressListFromString.getAddresses()){
            final Location location = GetLocationFromAddress.where(address);
            itemList.addView(searchResult(location));

        }
    }

    private View searchResult(final Location location) {
        TextView textView = new TextView(context);
        textView.setText(new CSVFromLocation(location).getText());
        textView.setPadding(Dp.one_point_5_em(),Dp.normal(),Dp.one_point_5_em(),Dp.normal());
        textView.setClickable(true);
        textView.setBackgroundResource(R.drawable.selectable_item);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediator.searchResultClicked(location);
            }
        });
        return textView;

    }

    private void showError(String message) {
        statusIndicator.setText(message);
        statusIndicator.setVisibility(View.VISIBLE);
    }


    private void showSearchIndicator() {
        TransitionManager.beginDelayedTransition(searchArea);
        statusIndicator.setText("Searching...");
        statusIndicator.setVisibility(View.VISIBLE);
    }

    private void hideSearchIndicator() {
        TransitionManager.beginDelayedTransition(searchArea);
        statusIndicator.setVisibility(View.GONE);
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    LocationPicker picker;
    Location currentLocation;

    //allows us receive and act upon commands targed at the origin selector
    //also, we only pass part of ourselves to the dialog when notifying that origin was selected
    private OriginSelector originSelector = new OriginSelector() {
    };

    protected abstract ScheduleTripSystem dialog();

    public Location getSelectedLocation() {
        return currentLocation;
    }
}
