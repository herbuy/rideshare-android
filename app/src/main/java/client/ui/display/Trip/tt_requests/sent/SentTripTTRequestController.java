package client.ui.display.Trip.tt_requests.sent;

import android.content.Context;
import android.view.View;

import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Proposal;
import libraries.android.MakeDummy;

public class SentTripTTRequestController implements HerbuyView {
    Context context;
    Proposal proposal;
    FrameLayoutBasedHerbuyStateView view;
    public SentTripTTRequestController(Context context, Proposal proposal) {
        this.context = context;
        this.proposal = proposal;
        view = new FrameLayoutBasedHerbuyStateView(context);
        view.render(defaultView());
    }

    private View defaultView() {
        return MakeDummy.linearLayoutVertical(
                context,
                description()
        );
    }

    private View description() {
        return MakeDummy.textView(
                context,
                String.format(
                        "Success"
                )
        );
    }

    @Override
    public View getView() {
        return view.getView();
    }
}
