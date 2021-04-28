package client.ui.display.Trip;

import android.content.Context;
import android.view.View;
import client.ui.libraries.HerbuyView;
import libraries.android.MakeDummy;
import server.families.CompletedTripsList;

public class CompletedTripsTab implements HerbuyView {
    private final Context context;

    public CompletedTripsTab(Context context) {
        this.context = context;
    }

    @Override
    public View getView() {
        return MakeDummy.linearLayoutVertical(
                context,
                new CompletedTripsList(context).getView()
        );
    }
}
