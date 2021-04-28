package libraries.android;


import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import client.ui.libraries.AdapterForViewList;


public abstract class MultiStateNetworkListView {
    private Context context;
    private List<View> viewList = new ArrayList<>();
    private BaseAdapter adapter;
    private AbsListView view;

    public MultiStateNetworkListView(Context context) {
        this.context = context;

        adapter = new AdapterForViewList(viewList);
        view = createListView();
        view.setAdapter(adapter);

        changeViewToDefault();
        refresh();
    }

    public abstract void refresh();

    protected abstract AbsListView createListView();

    protected abstract View defaultView();

    public MultiStateNetworkListView changeViewToDefault(){
        viewList.clear();
        viewList.add(defaultView());
        adapter.notifyDataSetChanged();

        return this;
    }
    public MultiStateNetworkListView changeViewToBusy(){
        viewList.clear();
        viewList.add(busyView());
        adapter.notifyDataSetChanged();

        return this;
    }

    protected abstract View busyView();

    public MultiStateNetworkListView changeViewToSuccess(){

        viewList.clear();

        List<View> items = successView();
        if(items == null || items.size() < 1){
            viewList.add(emptyView());
        }
        else{
            viewList.addAll(items);
        }

        view.setAdapter(adapter);
        return this;
    }

    protected abstract View emptyView();

    protected abstract List<View> successView();

    public MultiStateNetworkListView changeViewToError(){
        viewList.clear();
        viewList.add(errorView());
        adapter.notifyDataSetChanged();

        return this;
    }

    protected abstract View errorView();

    public View getView() {
        return view;
    }
}
