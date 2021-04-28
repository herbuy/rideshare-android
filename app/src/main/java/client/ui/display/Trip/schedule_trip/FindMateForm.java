package client.ui.display.Trip.schedule_trip;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import libraries.android.MakeDummy;
import libraries.android.FormDesign;
import resources.Dp;

public abstract class FindMateForm extends FormDesign {
    @Override
    protected final ViewGroup getBody(Context context) {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                MakeDummy.lineSeparator(context, Dp.normal()),
                origin(context),
                destination(context),
                date(context),
                time(context),
                carModel(context),
                carRegNumber(context),
                mateCountSought(context),
                fuelCharge(context),
                passingVia(context)
        );
        return layout;
    }

    protected abstract View passingVia(Context context);

    protected abstract View carModel(Context context);


    protected abstract View date(Context context);

    protected abstract View time(Context context);

    protected abstract View carRegNumber(Context context);

    protected abstract View mateCountSought(Context context);

    protected abstract View fuelCharge(Context context);


    protected abstract View origin(Context context);

    protected abstract View destination(Context context);

    @Override
    protected final View getFooter(Context context) {
        ViewGroup footerContainer = footerContainer(context);
        footerContainer.addView(btnScheduleTrip(context));
        return footerContainer;
    }

    protected abstract View btnScheduleTrip(Context context);

    protected abstract ViewGroup footerContainer(Context context);

}
