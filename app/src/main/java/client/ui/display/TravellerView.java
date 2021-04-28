package client.ui.display;


import android.content.Context;
import android.view.View;

import core.businessobjects.Trip;
import libraries.android.MakeDummy;
import client.ui.listeners.ListenerForSubmitTravelTogetherRequest;

public abstract class TravellerView {
    private Context context;
    private Trip trip;
    View view;
    private ListenerForSubmitTravelTogetherRequest listenerForSubmitTravelTogetherRequest;

    public View getView(){
        return view;
    }

    public TravellerView(Context context, Trip trip) {
        this.context = context;
        this.trip = trip;
        view = createView();
    }

    private View createView() {
        return MakeDummy.textView(context,"item in container");
    }



    protected abstract void onViewTravelTogetherRequests(Trip trip);

}
