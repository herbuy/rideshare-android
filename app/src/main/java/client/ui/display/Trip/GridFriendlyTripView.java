package client.ui.display.Trip;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import client.ui.libraries.HerbuyView;
import core.businessobjects.Trip;
import client.data.DummyData;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;

public abstract class GridFriendlyTripView implements HerbuyView {
    private Context context;
    private Trip trip;

    public GridFriendlyTripView(Context context, Trip trip) {
        this.context = context;
        this.trip = trip;
    }

    @Override
    public View getView() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                DummyData.randomSquareImageView(context),
                Atom.textViewPrimaryBold(context, trip.getActorName()),
                Atom.textViewSecondary(context, "Leaving "+trip.getOriginName()),
                Atom.textViewSecondary(context, "For "+trip.getDestinationName()),
                Atom.textViewSecondary(context,"On "+trip.getTripDate())
        );
        layout.setOnClickListener(listenerForViewDetails());

        layout.setPadding(Dp.normal(),Dp.normal(),Dp.normal(),Dp.normal());
        return layout;
    }


    private View.OnClickListener listenerForViewDetails() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewDetails(trip);
            }
        };
    }

    protected abstract void onViewDetails(Trip trip);
}
