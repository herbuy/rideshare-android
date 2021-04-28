package client.ui.display.Trip.schedule_trip.p20210410;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
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
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPicker;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPickerForApplication;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;
import core.businessobjects.Location;
import layers.render.Atom;
import libraries.android.AddressListFromString;
import libraries.android.GetParentActivity;
import libraries.android.HideKeyboard;
import libraries.android.MakeDummy;
import libraries.android.ShowKeyboard;
import libraries.android.TextChangedListener;
import libraries.underscore.Singleton;
import resources.Dp;
import resources.ItemColor;

public abstract class LocationInputDialog{
    private final Context context;
    View view;
    public LocationInputDialog(Context context) {
        this.context = context;
        view = finalView.instance();
        refreshList();
    }

    public View getView() {
        return view;
    }


    Singleton<View> finalView = new Singleton<View>() {
        @Override
        protected View onCreate() {

            View body = MakeDummy.linearLayoutVertical(
                    context,

                    editArea.instance(),
                    itemList.instance()
            );

            return body;
        }
    };


    protected Singleton<TextView> inputLabel = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            TextView textView = Atom.textViewPrimaryBold(context,getQuestion());
            return textView;
        }
    };


    protected abstract String getQuestion();

    Singleton<ViewGroup> editArea = new Singleton<ViewGroup>() {
        @Override
        protected ViewGroup onCreate() {
            return MakeDummy.linearLayoutVertical(
                    context,
                    editBoxWrapper(),
                    searchIndicator.instance()
            );
        }
    };

    protected View editBoxWrapper(){
        LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                context,
                editBox.instance()
        );
        wrapper.setPadding(24,24,24,24);
        wrapper.setClipToPadding(false);
        wrapper.setBackgroundColor(ItemColor.primary());
        return wrapper;
    }

    Singleton<EditText> editBox = new Singleton<EditText>() {
        @Override
        protected EditText onCreate() {

            EditText editText = Atom.editText(
                    context,
                    getQuestion(),
                    "",
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD,
                    editBoxTextChangedListener
            );
            editText.setElevation(10);
            ShowKeyboard.where(editText);
            return editText;
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
                    LinearLayout wrapper = MakeDummy.linearLayoutVertical(context,renderListItem(location));
                    wrapper.setPadding(
                            Dp.one_point_5_em(),
                            Dp.scaleBy(0.5f),
                            Dp.one_point_5_em(),
                            Dp.scaleBy(0.5f)
                    );
                    //wrapper.setClickable(true);
                    wrapper.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            searchResultClicked(location);
                        }
                    });
                    return wrapper;

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
        TransitionManager.beginDelayedTransition(itemList.instance());
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
            public SelectableItem.Padding getPaddingOrNull() {
                return new SelectableItem.Padding() {
                    @Override
                    public int left() {
                        return Dp.normal();
                    }

                    @Override
                    public int top() {
                        return Dp.scaleBy(0.5f);
                    }

                    @Override
                    public int right() {
                        return Dp.normal();
                    }

                    @Override
                    public int bottom() {
                        return Dp.scaleBy(0.5f);
                    }
                };
            }

            @Override
            protected boolean isMultipleItemPicker() {
                return LocationInputDialog.this.isMultipleItemPicker();
            }

            @Override
            protected void onItemSelected(SelectableItem<Location> sender, Location newlySelectedItem, List<Location> allSelectedItems) {
                LocationInputDialog.this.onItemSelected(sender.getView(),newlySelectedItem,allSelectedItems);
            }

            @Override
            protected void onItemUnselected(SelectableItem<Location> sender, Location unselectedItem, List<Location> allSelectedItems) {
                LocationInputDialog.this.onItemSelected(sender.getView(), null, allSelectedItems);
            }

            @Override
            public String getDisplayText(Location value) {
                return new CSVFromLocation(value).getText();
            }


            @Override
            public ItemRenderer<Location> getItemRenderer() {
                return new ItemRenderer<Location>() {
                    @Override
                    public View render(Location item, boolean isSelected) {
                        return renderListItem(item);
                    }
                };
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

    public View renderListItem(Location item) {
        TextView textView = Atom.textViewPrimaryNormal(context,new CSVFromLocation(item).getText());
        textView.setPadding(
                Dp.two_em(),
                0,//Dp.half_em(),
                Dp.two_em(),
                Dp.half_em()
        );

        textView.setBackgroundColor(Color.parseColor("#eeeeee"));
        return textView;
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

    protected abstract void onItemSelected(View view, Location newlySelectedItem, List<Location> allSelectedItems);


    Singleton<ViewGroup> itemList = new Singleton<ViewGroup>() {
        @Override
        protected ViewGroup onCreate() {
            return MakeDummy.linearLayoutVertical(context);
        }
    };


}
