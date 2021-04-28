package client.ui.display.Trip.schedule_trip.p20210303.dataform.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners.TripDataInputListener;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPicker;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.ItemPickerForApplication;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.utils.SelectableItem;
import layers.render.Atom;
import libraries.android.HideKeyboard;
import libraries.android.MakeDummy;
import libraries.android.TextChangedListener;
import libraries.android.RelativeLayoutFactory;
import libraries.underscore.Singleton;
import resources.Dp;

public abstract class TextInputDialog<ListItemType> extends TripInputDialog {
    public TextInputDialog(Context context, TripDataInputListener inputListener) {
        super(context, inputListener);
    }

    public View getView() {
        View view = finalView.instance();
        refreshList();
        return view;
    }

    Singleton<View> finalView = new Singleton<View>() {
        @Override
        protected View onCreate() {

            return makeWrapperWithScrollableBody(getQuestion(),body());
        }


    };

    private View header() {
        return MakeDummy.linearLayoutVertical(
                context,
                question.instance(),
                selectedValue.instance()
        );
    }

    private View body() {
        return MakeDummy.linearLayoutVertical(
                context,
                editArea.instance(),
                itemList.instance()
        );
    }


    //creates the question else returns already created one
    Singleton<TextView> question = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            TextView textView = Atom.textViewPrimaryBold(context,TextInputDialog.this.getQuestion());
            return textView;
        }
    };

    protected abstract String getQuestion();

    Singleton<TextView> selectedValue = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            TextView textView = Atom.textView(context,"Pick or add new");
            return textView;
        }
    };

    Singleton<ViewGroup> editArea = new Singleton<ViewGroup>() {
        @Override
        protected ViewGroup onCreate() {
            return RelativeLayoutFactory.alignleftOfWidget(
                    editBoxPadding.instance(),saveButton.instance()
            );
        }
    };

    Singleton<View> editBoxPadding = new Singleton<View>() {
        @Override
        protected View onCreate() {
            LinearLayout wrapper = MakeDummy.linearLayoutVertical(context,editBox.instance());
            wrapper.setPadding(0,0,Dp.normal(),0);
            return wrapper;
        }
    };

    Singleton<EditText> editBox = new Singleton<EditText>() {
        @Override
        protected EditText onCreate() {
            EditText editText = Atom.editText(context,getHint(),editBoxTextChangedListener);

            //editText.setSingleLine(true);
            return editText;
        }
    };

    protected abstract String getHint();

    TextChangedListener editBoxTextChangedListener = new TextChangedListener() {
        @Override
        public void afterTextChanged(Editable editable) {
            String newText = editable.toString().trim();
            if(newText.length() < 3){
                TransitionManager.beginDelayedTransition(editArea.instance());
                saveButton.instance().setVisibility(View.INVISIBLE);
            }
            else{
                TransitionManager.beginDelayedTransition(editArea.instance());
                saveButton.instance().setVisibility(View.VISIBLE);
            }
        }
    };

    Singleton<TextView> saveButton = new Singleton<TextView>() {
        @Override
        protected TextView onCreate() {
            TextView btn = Atom.textView(context,"Ok");
            btn.setVisibility(View.GONE);
            btn.setPadding(Dp.scaleBy(0.7f),Dp.scaleBy(1f),Dp.scaleBy(0.7f),Dp.scaleBy(1f));
            btn.setTextSize(Dp.scaleBy(0.7f));
            btn.setTextColor(Color.WHITE);
            btn.setBackgroundResource(R.drawable.button);
            btn.setClickable(true);
            btn.setOnClickListener(saveListener);
            return btn;
        }
    };

    View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String newValue = editBox.instance().getText().toString();
            ListItemType item = createListItem(newValue);
            saveListItem(item);
            editBox.instance().setText("");
            HideKeyboard.run(editBox.instance());
            refreshList();
            picker.selectFirstItem();
        }
    };

    public void refreshList() {
        itemList.instance().removeAllViews();
        itemList.instance().addView(cachedCarModels());
    }

    ItemPicker<ListItemType> picker;
    private View cachedCarModels() {
        picker = new ItemPickerForApplication<ListItemType>(context) {

            @Override
            protected void onItemSelected(SelectableItem<ListItemType> sender, ListItemType newlySelectedItem, List<ListItemType> allSelectedItems) {
                TextInputDialog.this.onItemSelected(newlySelectedItem);
            }

            @Override
            protected void onItemUnselected(SelectableItem<ListItemType> item, ListItemType unselectedItem, List<ListItemType> allSelectedItems) {
                TextInputDialog.this.onItemSelected(null);
            }

            @Override
            public String getDisplayText(ListItemType value) {
                return TextInputDialog.this.getDisplayText(value);
            }
        };
        List<ListItemType> data = getListItems();
        Collections.sort(data, new Comparator<ListItemType>() {
            @Override
            public int compare(ListItemType item1, ListItemType item2) {
                return TextInputDialog.this.getLongCompare(item2,item1);
            }
        });
        picker.setData(data);
        return picker.getView();

    }

    protected abstract int getLongCompare(ListItemType item2, ListItemType item1);


    protected abstract String getDisplayText(ListItemType value);

    protected abstract void onItemSelected(ListItemType newlySelectedItem);


    protected abstract ListItemType createListItem(String newValue);
    protected abstract void saveListItem(ListItemType item);

    protected abstract List<ListItemType> getListItems();

    Singleton<ViewGroup> itemList = new Singleton<ViewGroup>() {
        @Override
        protected ViewGroup onCreate() {
            return MakeDummy.linearLayoutVertical(context);
        }
    };
}
