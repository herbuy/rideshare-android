package client.ui.display;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import client.ui.libraries.AdapterForViewList;

public class HerbuySwipeFlingAdapterView extends SwipeFlingAdapterView {


    private List<View> viewList = new ArrayList<>();
    private BaseAdapter adapter;
    public HerbuySwipeFlingAdapterView(Context context, List<View> viewList) {
        super(context);
        this.viewList = viewList;
        adapter = new AdapterForViewList(viewList);
        setAdapter(adapter);
        setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        ));


        //setMaxVisible(4);
        //setMinStackInAdapter(6);

    }

    public List<View> getViewList() {
        return viewList;
    }





    @Override
    public BaseAdapter getAdapter() {
        return adapter;
    }
}
