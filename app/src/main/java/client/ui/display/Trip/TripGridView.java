package client.ui.display.Trip;

import android.content.Context;
import android.view.View;

import java.util.List;

import client.ui.GotoActivity;
import client.ui.libraries.HerbuyGridView;
import core.businessobjects.Trip;
import resources.Dp;
import retrofit2.Call;

public abstract class TripGridView extends HerbuyGridView<Trip> {
    public TripGridView(Context context) {
        super(context);
        getApiCall().enqueue(this);

    }

    @Override
    protected View createItemView(Trip item) {
        return new GridFriendlyTripView(getContext(),item){
            @Override
            protected void onViewDetails(Trip trip) {
                GotoActivity.tripDetails(getContext(), "");

            }
        }.getView();
    }

    @Override
    protected Parameters getGridParameters() {
        return new Parameters(3, Dp.normal(),Dp.normal());
    }

    protected abstract Call<List<Trip>> getApiCall();
}
