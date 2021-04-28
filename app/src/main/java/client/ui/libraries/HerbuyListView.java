package client.ui.libraries;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import client.data.AppCallback;
import client.ui.libraries.AdapterForViewList;
import client.ui.libraries.HerbuyView;
import retrofit2.Call;
import retrofit2.Response;

public abstract class HerbuyListView<ItemDataType> extends HerbuyAbsListView<ItemDataType> {
    public HerbuyListView(Context context) {
        super(context);
    }

    @Override
    protected AbsListView makeAbsListView() {
        return new ListView(getContext());
    }
}
