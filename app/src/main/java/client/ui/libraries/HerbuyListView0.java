package client.ui.libraries;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import client.data.AppCallback;
import retrofit2.Call;
import retrofit2.Response;

public abstract class HerbuyListView0<ItemDataType> extends AppCallback<List<ItemDataType>> implements HerbuyView {


    private Context context;
    private List<View> viewList = new ArrayList<>();
    private BaseAdapter adapter;
    private ListView listView;

    public HerbuyListView0(Context context) {
        this.context = context;
        adapter = new AdapterForViewList(viewList);
        listView = new ListView(context);
        listView.setAdapter(adapter);
    }

    public Context getContext() {
        return context;
    }

    @Override
    protected void onSuccess(Call<List<ItemDataType>> call, Response<List<ItemDataType>> response) {
        populateList(response.body());
    }

    private void populateList(List<ItemDataType> itemList) {
        for(ItemDataType item: itemList){
            viewList.add(createItemView(item));
        }
        adapter.notifyDataSetChanged();
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
