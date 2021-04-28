package client.ui.display.Trip;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import client.data.DummyData;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Trip;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;

public class TripDetailsView2 implements HerbuyView {
    private Context context;
    private Trip trip;

    public TripDetailsView2(Context context, Trip trip) {
        this.context = context;
        this.trip = trip;
    }

    @Override
    public View getView() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                space(),
                image(),
                space(),
                name(),
                route(),
                date(),
                space()
                //ownerActions(),
                //nonOwnerActions()
        );
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        return layout;
    }

    private View route() {
        TextView about = Atom.textViewPrimaryNormal(
                context,
                String.format(
                        "Leaving <b>%s</b> to <b>%s</b>",
                        trip.getOriginName(),trip.getDestinationName()
                )
        );
        MakeDummy.wrapContent(about);
        return about;
    }

    private View date() {
        //if date is today or 2moro, indicate so
        TextView view = Atom.textViewPrimaryNormal(
                context,
                String.format(
                        "On %s",
                        trip.getTripDate()
                )
        );
        MakeDummy.wrapContent(view);
        return view;
    }

    private View name() {
        return MakeDummy.wrapContent(Atom.textViewPrimaryBold(context,trip.getActorName()));
    }

    private View space() {
        return MakeDummy.lineSeparator(context,Dp.normal());
    }

    private View image() {
        ImageView imageView = DummyData.randomCircleImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(Dp.scaleBy(16),Dp.scaleBy(16)));
        return imageView;
    }
}
