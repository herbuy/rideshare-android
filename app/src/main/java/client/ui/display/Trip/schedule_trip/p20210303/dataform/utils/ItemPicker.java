package client.ui.display.Trip.schedule_trip.p20210303.dataform.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libraries.android.HideKeyboard;
import resources.ItemColor;


public abstract class ItemPicker<ItemType> implements SelectableItem.Participant<ItemType> {
    private Context context;
    private ViewGroup container;
    //defines a participant in this conversation
    //and the messages or requests they can receive

    public ItemPicker(Context context) {
        this.context = context;
        init();
    }

    @Override
    public ItemRenderer<ItemType> getItemRenderer() {
        return null;
    }

    private void init() {
        container = getContainer(context);
    }

    List<SelectableItem<ItemType>> allItems = new ArrayList<>();

    public void setData(List<ItemType> data){
        if(data != null){
            allItems.clear();
            selectedItems.clear();
            for(ItemType item : data){
                allItems.add(itemView(item));
            }
            updateUI();
        }

    }


    private void updateUI() {
        container.removeAllViews();
        for(SelectableItem<ItemType> item: allItems){
            container.addView(item.getView());
        }
    }

    private SelectableItem<ItemType> itemView(ItemType item) {
        SelectableItem<ItemType> selectableItem = new SelectableItem<>(context,this);
        selectableItem.setValue(item);
        return selectableItem;
    }

    public View getView(){
        return container;
    }

    //container for the rest of the items
    protected abstract ViewGroup getContainer(Context context);
    protected abstract void onItemSelected(SelectableItem<ItemType> sender, ItemType newlySelectedValue, List<ItemType> allSelectedItems);
    protected abstract void onItemUnselected(SelectableItem<ItemType> sender, ItemType unselectedItem, List<ItemType> allSelectedItems);
    //protected abstract int getStatusImageResourceWhenSelected();
    //protected abstract String getDisplayText(ItemType itemType);

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    //items and the value object they map to
    Map<SelectableItem<ItemType>, ItemType> selectedItems = new HashMap<>();

    protected  boolean isMultipleItemPicker(){
        return false;
    }

    @Override
    public void afterSelect(SelectableItem<ItemType> newlySelected) {
        HideKeyboard.run(newlySelected.getView());

        if(!isMultipleItemPicker()){
            for(SelectableItem<ItemType> item : selectedItems.keySet()){
                item.tryUnselect();
            }
        }
        selectedItems.put(newlySelected,newlySelected.getValue());
        onItemSelected(newlySelected,newlySelected.getValue(), new ArrayList<ItemType>(selectedItems.values()));
    }

    @Override
    public void afterUnSelect(SelectableItem<ItemType>  item) {
        selectedItems.remove(item);
        onItemUnselected(item,item.getValue(), new ArrayList<>(selectedItems.values()));
    }

    @Override
    public String getReasonToRejectSelectOrNull(ItemType value) {
        return "";
    }



    @Override
    public String getReasonToRejectUnSelectOrNull(ItemType newValue) {
        return null;
    }



    @Override
    public float textSizeWhenSelected() {
        return 20;
    }

    @Override
    public int textColorWhenSelected() {
        return ItemColor.primary();
    }

    @Override
    public int backgroundColorWhenSelected() {
        return Color.parseColor("#eeeeee");
    }

    @Override
    public void onSelectFailed(String reason) {
        //Toast.makeText(context,reason,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUnselectFailed(String reason) {
        //Toast.makeText(context,reason,Toast.LENGTH_SHORT).show();
    }

    public void selectFirstItem() {
        if(allItems.size() > 0){
            allItems.get(0).trySelect();
        }
    }


}
