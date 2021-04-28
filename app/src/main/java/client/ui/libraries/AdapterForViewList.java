package client.ui.libraries;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


public class AdapterForViewList extends BaseAdapter {
    private List<View> items = new ArrayList<>();

    public AdapterForViewList(List<View> items) {
        if(items != null){
            this.items = items;
        }

    }
    protected List<View> items(){
        return this.items;
    }

    public int getCount(){
        return items.size();
    }
    public long getItemId(int index){
        return index;
    }
    public Object getItem(int index){
        return items.get(index);
    }
    public View getView(int index, View view, ViewGroup parent){
        if(view == null){
            view = items.get(index);
        }
        return view;
    }
}
