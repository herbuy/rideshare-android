package client.ui.display.Trip;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import client.data.Rest;
import client.ui.libraries.HerbuyGridView;
import core.businessmessages.toServer.ParamsForGetTrips;
import core.businessobjects.Trip;
import client.data.DummyData;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.SquaredImageVIew;
import resources.Dp;

public abstract class TripGridViewForOtherTripsToDestination extends HerbuyGridView<Trip> {
    public TripGridViewForOtherTripsToDestination(Context context) {
        super(context);
        Rest.api().getTrips(new ParamsForGetTrips()).enqueue(this);

    }

    @Override
    protected View createItemView(Trip item) {
        int randomProfilePicResource = DummyData.randomProfilePicResource();
        String name = DummyData.randomName();
        String time = "3pm";

        final ImageView sharedElement = randomSquareImageView(getContext(),randomProfilePicResource);


        LinearLayout layout = MakeDummy.linearLayoutVertical(
                getContext(),
                sharedElement,
                Atom.textViewPrimaryBold(getContext(), name),
                Atom.textViewSecondary(getContext(), time)
        );
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onZoomIn(sharedElement);
            }
        });

        return layout;
    }

    protected abstract void onZoomIn(View sharedElement);

    public static ImageView randomSquareImageView(Context context, int randomProfilePicResource){


        ImageView imageView = new SquaredImageVIew(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(randomProfilePicResource);
        return imageView;
    }

    @Override
    protected Parameters getGridParameters() {
        return new Parameters(3, Dp.normal(),Dp.normal());
    }

}
