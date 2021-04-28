package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import client.ui.display.Location.CSVFromLocation;
import client.ui.display.Location.GeoLocationTask;
import client.ui.display.Location.GetLocationFromAddress;
import client.ui.display.Location.location_picker.LocationCache;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPicker;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPickerForApplication;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;
import core.businessobjects.Location;
import layers.render.Atom;
import libraries.android.AddressListFromString;
import libraries.android.MakeDummy;
import libraries.android.TextChangedListener;
import libraries.underscore.Singleton;
import resources.Dp;
import resources.ItemColor;

public abstract class LocationInputDialog extends TripInputDialog {
    View view;
    public LocationInputDialog(Context context, TripDataInputListener inputListener) {
        super(context, inputListener);
        view = finalView.instance();
        refreshList();
    }

    public View getView() {
        return view;
    }



    protected TitleSetCallback.TitleChangerLink titleChangerLink;

    Singleton<View> finalView = new Singleton<View>() {
        @Override
        protected View onCreate() {

            View body = MakeDummy.linearLayoutVertical(
                    context,

                    editArea.instance(),
                    itemList.instance()
            );

            return makeWrapper(getQuestion(), body, new TitleSetCallback() {
                @Override
                public void receiveLink(TitleChangerLink changer) {
                    titleChangerLink = changer;
                }
            });
        }
    };


    protected Singleton<TextView> inputLabel = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            TextView textView = Atom.textViewPrimaryBold(context,getQuestion());
            return textView;
        }
    };



    //creates the question else returns already created one

    protected abstract String getQuestion();

    Singleton<ViewGroup> editArea = new Singleton<ViewGroup>() {
        @Override
        protected ViewGroup onCreate() {
            return MakeDummy.linearLayoutVertical(
                    context,
                    editBox.instance(),
                    searchIndicator.instance()
            );
        }
    };

    Singleton<EditText> editBox = new Singleton<EditText>() {
        @Override
        protected EditText onCreate() {
            return Atom.editText(context,"Search here. <small>Example: Fort Portal</small>",editBoxTextChangedListener);
        }
    };

    TextChangedListener editBoxTextChangedListener = new TextChangedListener() {
        @Override
        public void afterTextChanged(Editable editable) {
            String searchQuery = editable.toString().trim();
            if(searchQuery.length() < 3){
                return;
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

                private void showSearchIndicator() {
                    TransitionManager.beginDelayedTransition(editArea.instance());
                    searchIndicator.instance().setVisibility(View.VISIBLE);
                }

                @Override
                protected void AfterGeolocate(AddressListFromString addressListFromString) {
                    hideSearchIndicator();
                    showResults(addressListFromString);
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
                    itemList.instance().removeAllViews();

                    itemList.instance().addView(resultHeader());

                    for(AddressListFromString.Address address : addressListFromString.getAddresses()){
                        final Location location = GetLocationFromAddress.where(address);
                        itemList.instance().addView(searchResult(location));

                    }
                }

                private View resultHeader() {
                    TextView textView = Atom.textViewPrimaryBold(context,"DID YOU MEAN...");
                    textView.setTextColor(ItemColor.primary());
                    textView.setTextSize(Dp.scaleBy(0.7f));
                    textView.setTypeface(null,Typeface.BOLD);
                    textView.setPadding(Dp.one_point_5_em(),Dp.normal(),0,0);
                    return textView;
                }

                private View searchResult(final Location location) {
                    TextView textView = new TextView(context);
                    textView.setText(Html.fromHtml(new CSVFromLocation(location).getText()));
                    textView.setPadding(Dp.one_point_5_em(),Dp.normal(),Dp.one_point_5_em(),Dp.normal());
                    textView.setClickable(true);
                    textView.setBackgroundResource(R.drawable.selectable_item);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            searchResultClicked(location);
                        }

                        
                    });
                    return textView;

                }

                private void searchResultClicked(Location location) {
                    cacheLocationIfNecessary(location);
                    editBox.instance().setText("");
                    refreshList();
                    picker.selectFirstItem();
                }


                private void showError(String error) {
                    searchIndicator.instance().setText(error);
                }

                private void hideSearchIndicator() {
                    TransitionManager.beginDelayedTransition(editArea.instance());
                    searchIndicator.instance().setVisibility(View.GONE);
                }

            };
            geoLocator.search(searchQuery);
        }

        private void cacheLocationIfNecessary(Location location) {
            LocationCache locationCache = new LocationCache();
            locationCache.deleteLocation(location);

            location.setId(UUID.randomUUID().toString());
            location.setTimeStampLastModifiedInSeconds(System.currentTimeMillis());
            locationCache.save(location.getId(),location);
        }
    };

    Singleton<TextView> searchIndicator = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            TextView view = Atom.textView(context,"Searching...");
            view.setVisibility(View.GONE);
            view.setTextColor(ItemColor.textPrimary());
            view.setTextSize(Dp.scaleBy(0.7f));
            return view;
        }
    };


    public void refreshList() {
        itemList.instance().removeAllViews();
        itemList.instance().addView(listContent());
    }

    ItemPicker<Location> picker;
    private View listContent() {
        List<Location> data = new LocationCache().selectAll();
        if(data.size() < 1){
            return purposeAndUsageTips();
        }

        picker = new ItemPickerForApplication<Location>(context) {

            @Override
            protected boolean isMultipleItemPicker() {
                return LocationInputDialog.this.isMultipleItemPicker();
            }

            @Override
            protected void onItemSelected(SelectableItem<Location> sender, Location newlySelectedItem, List<Location> allSelectedItems) {
                LocationInputDialog.this.onItemSelected(newlySelectedItem,allSelectedItems);
            }

            @Override
            protected void onItemUnselected(SelectableItem<Location> item, Location unselectedItem, List<Location> allSelectedItems) {
                LocationInputDialog.this.onItemSelected(null, allSelectedItems);
            }

            @Override
            public String getDisplayText(Location value) {
                return new CSVFromLocation(value).getText();
            }

            @Override
            public String getReasonToRejectSelectOrNull(Location value) {
                return LocationInputDialog.this.getReasonToRejectSelectOrNull(value);
            }




        };

        Collections.sort(data, new Comparator<Location>() {
            @Override
            public int compare(Location item1, Location item2) {
                return Long.compare(item2.getTimeStampLastModifiedInSeconds(),item1.getTimeStampLastModifiedInSeconds());
            }
        });
        picker.setData(data);
        return MakeDummy.linearLayoutVertical(
                context,
                pickerPrompt(),
                picker.getView()
        );

    }

    private View pickerPrompt() {
        TextView pickerPrompt = Atom.textView(context,"YOU CAN QUICKLY CHOOSE FROM LIST BELOW");
        pickerPrompt.setTextSize(Dp.scaleBy(0.7f));
        pickerPrompt.setPadding(Dp.scaleBy(3),Dp.two_em(),0,0);
        pickerPrompt.setTextColor(ItemColor.primary());
        pickerPrompt.setTypeface(null, Typeface.BOLD);
        return pickerPrompt;
    }

    private View purposeAndUsageTips() {
        return Atom.centeredText(context,getPurposeAndUsage());
    }

    protected abstract String getPurposeAndUsage();

    protected abstract String getReasonToRejectSelectOrNull(Location value);

    protected abstract boolean isMultipleItemPicker();

    protected abstract void onItemSelected(Location newlySelectedItem, List<Location> allSelectedItems);


    Singleton<ViewGroup> itemList = new Singleton<ViewGroup>() {
        @Override
        protected ViewGroup onCreate() {
            return MakeDummy.linearLayoutVertical(context);
        }
    };


}
