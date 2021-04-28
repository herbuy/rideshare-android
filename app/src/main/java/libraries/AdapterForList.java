package libraries;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


abstract public class AdapterForList<T> extends BaseAdapter {
    private List<T> items = new ArrayList<>();

    public AdapterForList(List<T> items) {
        if(items != null){
            this.items = items;
        }

    }
    protected List<T> items(){
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
            view = makeView(parent,items.get(index));
        }
        return view;
    }

    protected abstract View makeView(ViewGroup parent, T data);
}
