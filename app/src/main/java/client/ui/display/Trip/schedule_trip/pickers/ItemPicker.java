package client.ui.display.Trip.schedule_trip.pickers;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.skyvolt.jabber.R;

import java.util.ArrayList;
import java.util.List;

import client.ui.display.Trip.schedule_trip.SelectableItem;
import libraries.android.MakeDummy;
import libraries.android.ColorCalc;
import resources.Dp;
import resources.ItemColor;

public abstract class ItemPicker<ListItemType> {
    //a list of currently selected items. when we select an item, it is added to this list
    final List<SelectableItem<ListItemType>> selectedItems = new ArrayList<>();
    //a list of all items in the database. can be used for traversing all items and instructing them what to do
    //it is like tracking a list of all aliens
    //since we dont want duplicate items, we might consider using sets for selected items
    List<SelectableItem<ListItemType>> allItems;
    //to be use for rendering items into views
    Context context;
    //the view that contains the entire UI of the list, and accessible by object that wants to embed this list
    LinearLayout list;

    public ItemPicker(Context context) {
        this.context = context;
        init();
    }


    private void init() {

        initializeListContainers();
        initializeListViews();

        List<ListItemType> data = getData();
        renderData(data);


    }

    private void renderData(List<ListItemType> data) {
        for (final ListItemType item : data) {
            final SelectableItem selectableItem = makeSelectableItem(item);
            selectableItem.setText(getDisplayText(item));
            addToInvisibleList(selectableItem);
            addToVisibleList(selectableItem);

        }
        ;
    }

    private void addToVisibleList(SelectableItem selectableItem) {
        list.addView(selectableItem.getView());
    }

    private void addToInvisibleList(SelectableItem selectableItem) {
        allItems.add(selectableItem);
    }

    protected abstract int maxItemsCanSelect();

    protected abstract int minItemsCanSelect();

    private SelectableItem makeSelectableItem(final ListItemType item) {
        return new SelectableItem<ListItemType>(context) {

            @Override
            public ListItemType getValue() {
                return item;
            }

            @Override
            protected void beforeSelect(SelectableItem selectableItem1) {

            }



            @Override
            protected final String reasonNotToAllowSelect(ListItemType value) {
                if (selectedItems.size() >= maxItemsCanSelect()) {
                    return errorOnExceedSelectionLimit();
                }
                return "";
            }

            @Override
            protected final void onSelectFailed(String reason) {
                ItemPicker.this.onSelectFailed(reason);
            }

            @Override
            protected final String reasonNotToAllowUnSelect(ListItemType value) {
                if (selectedItems.size() <= minItemsCanSelect()) {
                    return errorIfSelectionCountCantFallBelowMinimum();
                }
                return "";
            }

            @Override
            protected void onUnselectFailed(String reason) {
                ItemPicker.this.onUnselectFailed(reason);
            }

            @Override
            protected final void afterSelect(SelectableItem selectableItem1) {

                //for single item
                if(isSingleSelect()){
                    unSelectAllItemsExceptThis(selectableItem1);
                }

                selectedItems.add(selectableItem1);
                ItemPicker.this.onItemSelected(item);


            }

            private void unSelectAllItemsExceptThis(SelectableItem selectableItem1) {
                for (SelectableItem item1 : allItems) {
                    if (item1 != selectableItem1) {
                        item1.unselect();
                    }
                }
            }

            @Override
            protected void beforeUnselect(SelectableItem selectableItem1) {

            }

            @Override
            protected void afterUnSelect(SelectableItem selectableItem1) {
                selectedItems.remove(selectableItem1);
                ItemPicker.this.onItemUnSelected(item, selectedItems);
            }

            @Override
            protected int imageResourceToIndicateSelected() {
                return R.drawable.ic_menu_tick;
            }

            @Override
            protected int backgroundColorWhenSelected() {
                return ColorCalc.mixColors(ItemColor.primary(), Color.WHITE, 0.9f);
                //return Color.parseColor("#eeeeee");
            }

            @Override
            protected int textColorWhenSelected() {
                return ItemColor.primary();
            }

            @Override
            protected float textSizeWhenSelected() {
                return Dp.scaleBy(1.3f);
            }
        };
    }


    private boolean isSingleSelect() {
        return maxItemsCanSelect() == 1;
    }

    protected abstract String errorIfSelectionCountCantFallBelowMinimum();

    protected abstract void onUnselectFailed(String reason);

    protected abstract void onSelectFailed(String reason);

    protected abstract String errorOnExceedSelectionLimit();

    private void initializeListViews() {
        list = MakeDummy.linearLayoutVertical(context);
    }

    private void initializeListContainers() {
        allItems = new ArrayList<>();
    }

    protected abstract void onItemSelected(ListItemType item);

    protected abstract void onItemUnSelected(ListItemType item, List<SelectableItem<ListItemType>> selectedItems);


    protected abstract String getDisplayText(ListItemType item);

    protected abstract List<ListItemType> getData();

    public final List<ListItemType> getSelectedValues(){
        List<ListItemType> selectedValues = new ArrayList<>();
        if(selectedItems != null){
            for(SelectableItem<ListItemType> item: selectedItems){
                selectedValues.add(item.getValue());
            }
        }
        return selectedValues;

    }

    public final ListItemType getSelectedValue() {
        if (selectedItems == null || selectedItems.size() < 1) {
            return null;
        } else {
            return selectedItems.get(0).getValue();
        }
    }

    public final View getView() {
        return list;
    }

    public final void selectFirstItem() {
        if (allItems.size() < 1) {
            return;
        }
        allItems.get(0).select();
    }
}
