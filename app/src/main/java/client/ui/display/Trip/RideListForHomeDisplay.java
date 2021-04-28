package client.ui.display.Trip;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import client.data.DummyData;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Trip;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.HerbuyAbsListViewer;
import resources.Dp;

public class RideListForHomeDisplay implements HerbuyView {
    private final Context context;

    public RideListForHomeDisplay(Context context) {
        this.context = context;
    }

    @Override
    public View getView() {
        //generate some fake data
        List<Trip> topRides = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            Trip trip = new Trip();
            trip.setForUserName(DummyData.randomName());
            topRides.add(trip);

        }
        HerbuyAbsListViewer<Trip> viewer = new HerbuyAbsListViewer<Trip>(context) {
            @Override
            protected AbsListView createAbsListView(Context context) {
                GridView gridView = new GridView(context);
                gridView.setNumColumns(2);
                gridView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                gridView.setVerticalScrollBarEnabled(false);
                //gridView.setHorizontalSpacing(Dp.normal());
                //gridView.setVerticalSpacing(Dp.normal());
                return gridView;
            }

            @Override
            protected View createItemView(Context context, Trip item) {
                View itemView = MakeDummy.linearLayoutVertical(
                        context,
                        DummyData.randomSquareImageView(context),
                        Atom.textViewPrimaryBold(context,item.getForUserName())
                );
                itemView.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());
                return itemView;
            }

        };
        viewer.setContent(topRides);
        //viewer.getView().setPadding(Dp.normal(),Dp.normal(),Dp.n);
        return viewer.getView();
    }
}
