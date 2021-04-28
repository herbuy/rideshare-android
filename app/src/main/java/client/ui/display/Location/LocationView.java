package client.ui.display.Location;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import client.ui.GotoActivity;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Location;
import client.data.DummyData;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;

public class LocationView implements HerbuyView{
    private Context context;
    private Location location;

    public LocationView(Context context, Location location) {
        this.context = context;
        this.location = location;
    }

    @Override
    public View getView() {
        LinearLayout itemView = MakeDummy.linearLayoutVertical(
                context,
                Atom.textViewPrimaryBold(context, location.getName()),
                Atom.textView(context, String.format("%s of your friends have been there", DummyData.randomInt(0, 100)))
        );
        itemView.setPadding(Dp.normal(),Dp.normal(),Dp.normal(),Dp.normal());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.locationProfileGeneric(context,location);
            }
        });

        return itemView;
    }
}
