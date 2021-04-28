package client.ui.libraries;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import client.data.AppCallback;
import retrofit2.Call;
import retrofit2.Response;

public abstract class HerbuyAbsListView<ItemDataType> extends AppCallback<List<ItemDataType>> implements HerbuyView {


    private Context context;
    private List<View> viewList = new ArrayList<>();
    private BaseAdapter adapter;
    private AbsListView listView;



    public HerbuyAbsListView(Context context) {

        this.context = context;
        adapter = new AdapterForViewList(viewList);
        listView = makeAbsListView();
        listView.setAdapter(adapter);
    }

    //this is where u decide whether u want to use a listView, gridView, or any other adapter view coz they inherit from AbsListView
    protected abstract AbsListView makeAbsListView();

    public Context getContext() {
        return context;
    }

    @Override
    protected void onSuccess(Call<List<ItemDataType>> call, Response<List<ItemDataType>> response) {
        populateList(response.body());
    }

    private void populateList(List<ItemDataType> itemList) {
        viewList.clear();

        for(ItemDataType item: itemList){
            viewList.add(createItemView(item));
        }
        listView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

    protected abstract View createItemView(ItemDataType item);

    @Override
    protected void onError(Call<List<ItemDataType>> call, Throwable t) {

    }

    @Override
    public View getView() {
        return listView;
    }
}
