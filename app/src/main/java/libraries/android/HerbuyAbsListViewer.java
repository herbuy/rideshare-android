package libraries.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/** class only does rendering, nothing about fetchinf data or changing states when loading data
 * Command a herbuy state view to change state to busy, and when yo controller loads the data,
 * it can create this class and load data in it and then inject this object into the success method
 * of the state view.
 * That is what was envisioned when it was created.
 * Yo clent code is the controller to make the two classes work together
 * */
public abstract class HerbuyAbsListViewer<T> {

    private Context context;
    private List<T> itemList = new ArrayList<>();
    private BaseAdapter adapter;
    private AbsListView absListView;


    public HerbuyAbsListViewer(Context context) {
        adapter = makeAdapter(itemList);
        absListView = createAbsListView(context);
        notifyListChanged();
    }

    protected abstract AbsListView createAbsListView(Context context);

    public View getView() {
        return absListView;
    }

    public HerbuyAbsListViewer<T> setContent(List<T> body) {
        itemList.clear();
        add(body);
        return this;

    }

    public void add(T item){
        add(Collections.singletonList(item));
    }


    public void add(List<T> inputList){

        if(itemList != null){
            for(T item : inputList){

                itemList.add(item);
            }
        }
        HashSet<T> hashSet = new HashSet<>(itemList);
        itemList.clear();
        itemList.addAll(hashSet);
        sortItems();
        notifyListChanged();

    }


    public void scrollToBottom(){
        if(adapter.getCount() >= 1){
            absListView.setSelection(adapter.getCount() - 1);
        }
    }

    private boolean hasChildrenBeforeFirstVisible(){
        return adapter.getCount() > 0 && absListView.getFirstVisiblePosition() > 0;
    }

    private boolean hasChildrenAfterLastVisible(){
        return adapter.getCount() > 0 && absListView.getLastVisiblePosition() < adapter.getCount() - 1;
    }

    public boolean isAtEnd(){
        return !hasChildrenAfterLastVisible();
    }
    public boolean isAtBeginning(){
        return !hasChildrenBeforeFirstVisible();
    }

    public void scrollToTop(){
        if(adapter.getCount() >= 1){
            absListView.setSelection(0);
        }

    }

    private void notifyListChanged() {
        if(absListView != null){
            absListView.setAdapter(adapter);
        }

    }

    private Comparator<T> comparator;

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
        sortItems();
        notifyListChanged();
    }

    private void sortItems() {
        if(comparator != null){
            Collections.sort(itemList,comparator);
        }
    }


    private BaseAdapter makeAdapter(final List<T> itemList){
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return itemList.size();
            }

            @Override
            public T getItem(int position) {
                return itemList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = createItemView(parent.getContext(),getItem(position));
                }
                return convertView;
            }
        };
    }

    protected abstract View createItemView(Context context,T item);


}
