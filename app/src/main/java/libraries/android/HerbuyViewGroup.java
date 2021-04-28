package libraries.android;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** class only does rendering, nothing about fetchinf data or changing states when loading data
 * Command a herbuy state view to change state to busy, and when yo controller loads the data,
 * it can create this class and load data in it and then inject this object into the success method
 * of the state view.
 * That is what was envisioned when it was created.
 * Yo clent code is the controller to make the two classes work together
 * */
public abstract class HerbuyViewGroup<T> {

    private Context context;
    ViewGroup absListView;
    ScrollView scrollView;
    List<T> itemList;


    public HerbuyViewGroup(Context context) {
        this.context = context;
        itemList = new ArrayList<>();
        absListView = createAbsListView(context);
        scrollView = createScrollView(context);
        scrollView.addView(absListView);
    }

    private ScrollView createScrollView(Context context) {
        return new ScrollView(context);
    }

    protected abstract ViewGroup createAbsListView(Context context);

    public View getView() {
        if(scrollView == null){
            return absListView;
        }
        return scrollView;
    }

    public HerbuyViewGroup<T> setContent(List<T> body) {
        itemList = body;


        //update UI
        //some view groups like adapter view may not allow direct removal of items -- so can call their specific way to remove item
        absListView.removeAllViews();
        //call their specific way to add items
        add(body);
        return this;
    }

    public void add(T item){
        add(Collections.singletonList(item));
    }


    public void add(List<T> inputList){
        if(inputList != null){
            sortItems(inputList);
            //could so the list before adding
            for(T item : inputList){
                absListView.addView(createItemView(context,item));
                absListView.requestLayout();
            }
        }
        else{
            Log.d(getClassName(),"Tried adding null list");
        }


    }

    protected String getClassName(){
        return getClass().getSimpleName();
    }


    public void scrollToBottom(){
        /*if(adapter.getCount() >= 1){
            absListView.setSelection(adapter.getCount() - 1);
        }*/
    }

    private boolean hasChildrenBeforeFirstVisible(){
        return false;//return adapter.getCount() > 0 && absListView.getFirstVisiblePosition() > 0;
    }

    private boolean hasChildrenAfterLastVisible(){
        return false;//return adapter.getCount() > 0 && absListView.getLastVisiblePosition() < adapter.getCount() - 1;
    }

    public boolean isAtEnd(){
        return !hasChildrenAfterLastVisible();
    }
    public boolean isAtBeginning(){
        return !hasChildrenBeforeFirstVisible();
    }

    public void scrollToTop(){
        /*if(adapter.getCount() >= 1){
            absListView.setSelection(0);
        }*/

    }

    private Comparator<T> comparator;

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
        //sortItems();

    }

    private void sortItems(List<T> inputList) {
        if(comparator != null && inputList != null){
            Collections.sort(itemList,comparator);
        }
    }


    protected abstract View createItemView(Context context,T item);


    public View lastChild() {
        if(hasChildren()){
            return absListView.getChildAt(absListView.getChildCount() - 1);
        }
        return null;

    }

    public boolean hasChildren() {
        return absListView.getChildCount() > 1;
    }

    public View getContainer() {
        return absListView;
    }

    public View getScrollView() {
        return scrollView;
    }
}
