package client.ui.display.Location.location_picker;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import core.businessobjects.Location;
import layers.render.Atom;
import libraries.android.AddressListFromString;
import libraries.android.HerbuyAsyncTask;
import libraries.android.MakeDummy;
import libraries.android.TextChangedListener;
import resources.Dp;

public abstract class LocationPicker {
    private final Context context;
    private Location currentLocation;

    public LocationPicker(Context context) {
        this.context = context;
    }

    LinearLayout wrapper;

    public View getView() {
        wrapper = MakeDummy.linearLayoutVertical(
                context,
                innerWrapper()
        );
        formatWrapper(wrapper);
        return wrapper;
    }

    private View innerWrapper() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                searchArea(),
                statusIndicator(),
                resultArea().getView()
        );
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return layout;
    }

    TextView searchingIndicator;

    private View statusIndicator() {
        searchingIndicator = Atom.textViewSecondary(context, "Searching..");
        searchingIndicator.setVisibility(View.GONE);
        return searchingIndicator;
    }

    private void formatWrapper(LinearLayout wrapper) {
        wrapper.setPadding(Dp.scaleBy(3), Dp.normal(), Dp.scaleBy(3), Dp.normal());
        wrapper.setBackgroundColor(Color.WHITE);
        wrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    ResultAreaForLocationPicker resultArea;

    private boolean searchEnabled = true;

    private ResultAreaForLocationPicker resultArea() {
        resultArea = new ResultAreaForLocationPicker(context) {
            @Override
            protected void onLocationSelected(Location selectedLocation) {
                setCurrentLocation(selectedLocation);
                LocationPicker.this.onLocationSelected(selectedLocation);
            }
        };
        return resultArea;
    }

    public void setCurrentLocation(Location selectedLocation) {
        LocationPicker.this.currentLocation = selectedLocation;

        searchEnabled = false;
        searchBox.setText(Html.fromHtml(renderLocationText(selectedLocation)));
        searchEnabled = true;
    }

    protected String renderLocationText(Location location) {
        return location.getSubAdminArea();
    }

    EditText searchBox;

    protected View searchArea() {
        searchBox = Atom.editText(context, "Type Name of Place", "", new TextChangedListener() {

            @Override
            public void afterTextChanged(Editable s) {

                if (!searchEnabled) {
                    return;
                }

                String searchQuery = s.toString().trim();

                if (searchQuery.length() < 3) {
                    return;
                }

                scheduleSearchTask(searchQuery);


            }


            private void scheduleSearchTask(final String searchQuery) {

                LocationSearchTask locationSearchTask = new LocationSearchTask();
                locationSearchTask.setBeforeSearch(new Runnable() {
                    @Override
                    public void run() {
                        indicateSearching();
                    }

                    private void indicateSearching() {
                        TransitionManager.beginDelayedTransition(wrapper);
                        searchingIndicator.setVisibility(View.VISIBLE);
                    }



                });

                locationSearchTask.setDoInBackground(new HerbuyAsyncTask.DoInBackground<String, AddressListFromString>() {
                    @Override
                    public AddressListFromString run(String... params) {
                        return new AddressListFromString(context, params[0]);
                    }
                });

                locationSearchTask.setOnComplete(new HerbuyAsyncTask.OnComplete<AddressListFromString>() {
                    @Override
                    public void run(AddressListFromString addressListFromString) {
                        hideSearchIndicator();
                        showResults(addressListFromString);
                    }

                    private void hideSearchIndicator() {
                        TransitionManager.beginDelayedTransition(wrapper);
                        searchingIndicator.setVisibility(View.GONE);
                    }

                    private void showResults(AddressListFromString addressListFromString) {
                        if (addressListFromString.isEmpty()) {
                            resultArea.indicateError(addressListFromString.getError());
                        } else {
                            resultArea.displayAddresses(addressListFromString.getAddresses());
                        }

                    }
                });


                locationSearchTask.execute(searchQuery);


            }

        });
        searchBox.setPadding(paddingHorizontal(), Dp.normal(), paddingHorizontal(), Dp.normal());
        searchBox.postDelayed(new Runnable() {
            @Override
            public void run() {
                searchBox.requestFocus();
            }
        }, 400);
        return searchBox;
    }

    private int paddingHorizontal() {
        return Dp.one_point_5_em();
    }

    public abstract void onLocationSelected(Location location);

    private static class LocationSearchTask extends HerbuyAsyncTask<String,Void,AddressListFromString>{

    }


}
