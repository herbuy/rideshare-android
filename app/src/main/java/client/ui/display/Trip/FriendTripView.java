package client.ui.display.Trip;

import android.content.Context;
import android.view.View;

import client.ui.libraries.HerbuyView;
import core.businessobjects.Trip;
import client.data.DummyData;
import libraries.android.MakeDummy;

public class FriendTripView implements HerbuyView{
    Context context;
    Trip trip;

    public FriendTripView(Context context, Trip trip) {
        this.context = context;
        this.trip = trip;
    }

    @Override
    public View getView() {
        return MakeDummy.textView(context, DummyData.randomName());
    }
}
