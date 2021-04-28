package my_scripts.home_screen;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import core.businessobjects.Client;
import libraries.AdapterForList;
import resources.Dp;

public class clientGridBuilderScript {
    public static View run(Context context,List<Client> body) {
        final List<View> gridItems = viewMakerScript(context, body);

        BaseAdapter adapter = makeAdapter(gridItems);

        //make grid
        GridView gridView = makeGridView(context);
        gridView.setAdapter(adapter);
        return gridView;
    }

    @NonNull
    private static List<View> viewMakerScript(Context context, List<Client> body) {
        final List<View> gridItems = new ArrayList<>();
        for(Client itemDetails: body){
            gridItems.add(
                    gridItemMakerScript(context, itemDetails)
            );
        }
        return gridItems;
    }

    private static View gridItemMakerScript(Context context, Client itemDetails) {
        return ClientItemDisplayScript.run(context, itemDetails);
    }

    @NonNull
    private static GridView makeGridView(Context context) {
        GridView gridView = new GridView(context);
        gridView.setNumColumns(2);
        gridView.setHorizontalSpacing(Dp.scaleBy(1));
        gridView.setVerticalSpacing(Dp.scaleBy(1));
        return gridView;
    }

    private static BaseAdapter makeAdapter(final List<View> gridItems) {
        return new AdapterForList<View>(gridItems) {
            @Override
            protected View makeView(ViewGroup parent, View data) {
                return data;
            }
        };

    }
}
