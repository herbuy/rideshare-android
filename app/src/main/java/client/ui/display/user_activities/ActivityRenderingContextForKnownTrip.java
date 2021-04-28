package client.ui.display.user_activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import client.data.AppCallback;
import client.data.Rest;
import client.ui.GotoActivity;
import core.businessobjects.Trip;
import core.businessobjects.TripActivity;
import client.data.DummyData;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import resources.Dp;
import retrofit2.Call;
import retrofit2.Response;

public class ActivityRenderingContextForKnownTrip extends ActivityRenderingContext {
    private Context context;

    public ActivityRenderingContextForKnownTrip(Context context) {
        this.context = context;
    }


    @Override
    protected View unSupportedActivity(TripActivity tripActivity) {
        return MakeDummy.textView(context, "Unsupported activity type: " + tripActivity.getActivityType());
    }

    @Override
    protected View recipientScheduledTrip(TripActivity tripActivity) {
        View description = Atom.textViewPrimaryNormal(context,
                String.format(
                        "You are travelling to Fort Portal on %s at %s",
                        tripActivity.getActivityDate(),
                        tripActivity.getActivityTime()

                )
        );

        View activityDate = Atom.textViewSecondary(context, tripActivity.getActivityDate());
        View activityTime = Atom.textViewSecondary(context,tripActivity.getActivityTime());

        View actions = MakeDummy.linearLayoutHorizontal(
                context,
                Atom.button(context, "Cancel Trip", new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        Rest.api().cancelTrip("").enqueue(new AppCallback<List<Trip>>() {
                            @Override
                            protected void onSuccess(Call<List<Trip>> call, Response<List<Trip>> response) {
                                Button btn = (Button) v;
                                btn.setText("Trip Cancelled");
                                btn.setClickable(false);
                                btn.setAlpha(0.5f);
                            }

                            @Override
                            protected void onError(Call<List<Trip>> call, Throwable t) {
                                MessageBox.show(t.getMessage(),context);
                            }
                        });
                    }
                }),
                MakeDummy.verticalSeparator(context, Dp.normal()),
                Atom.button(context, "Find Travel Mates", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GotoActivity.otherTripsToDestinationExceptTripId(context,"");
                    }
                }),
                MakeDummy.verticalSeparator(context,Dp.normal()),
                Atom.button(context,"Notify friends",null)
        );

        return MakeDummy.linearLayoutVertical(
                context,

                description,
                activityDate,
                activityTime,
                actions

        );
    }

    @Override
    protected View actorSentTTRequest(TripActivity tripActivity) {
        View description = Atom.textViewPrimaryNormal(context,
                String.format(
                        "Wants to travel with you [to fort portal on Friday]",
                        tripActivity.getActivityDate(),
                        tripActivity.getActivityTime()

                )
        );

        ImageView actorImage = DummyData.randomSquareImageView(context);
        actorImage.setLayoutParams(new ViewGroup.LayoutParams(Dp.scaleBy(4f),Dp.scaleBy(4f)));

        View actorName = Atom.textViewPrimaryBold(context, tripActivity.getActorName());

        View activityDate = Atom.textViewSecondary(context, tripActivity.getActivityDate());
        View activityTime = Atom.textViewSecondary(context,tripActivity.getActivityTime());

        View actions = MakeDummy.linearLayoutHorizontal(
                context,
                Atom.button(context,"View Profile",null),
                MakeDummy.verticalSeparator(context,Dp.normal()),
                Atom.button(context,"Accept",null),
                MakeDummy.verticalSeparator(context,Dp.normal()),
                Atom.button(context,"Reject",null)
        );

        return MakeDummy.linearLayoutVertical(
                context,
                actorImage,
                actorName,
                description,
                activityDate,
                activityTime,
                actions

        );
    }

    @Override
    protected View youSentTTRequest(TripActivity tripActivity) {
        View description = Atom.textViewPrimaryNormal(context,
                String.format(
                        "You want to travel with Herbert to Fort Portal on Friday at 2pm",
                        tripActivity.getActivityDate(),
                        tripActivity.getActivityTime()

                )
        );

        ImageView actorImage = DummyData.randomSquareImageView(context);
        actorImage.setLayoutParams(new ViewGroup.LayoutParams(Dp.scaleBy(4f),Dp.scaleBy(4f)));

        View actorName = Atom.textViewPrimaryBold(context,tripActivity.getActorName());

        View activityDate = Atom.textViewSecondary(context, tripActivity.getActivityDate());
        View activityTime = Atom.textViewSecondary(context,tripActivity.getActivityTime());

        View actions = MakeDummy.linearLayoutHorizontal(
                context,
                Atom.button(context,"Cancel Request",null),
                MakeDummy.verticalSeparator(context,Dp.normal())

        );

        return MakeDummy.linearLayoutVertical(
                context,
                actorImage,
                actorName,
                description,
                activityDate,
                activityTime,
                actions

        );
    }


    @Override
    protected View actorScheduledTrip(TripActivity tripActivity) {
        View description = Atom.textViewPrimaryNormal(context,
                String.format(
                        "Is travelling to Fort Portal On Friday at 3pm"
                )
        );

        ImageView actorImage = DummyData.randomSquareImageView(context);
        actorImage.setLayoutParams(new ViewGroup.LayoutParams(Dp.scaleBy(4f),Dp.scaleBy(4f)));

        View actorName = Atom.textViewPrimaryBold(context,tripActivity.getActorName());

        View activityDate = Atom.textViewSecondary(context, tripActivity.getActivityDate());
        View activityTime = Atom.textViewSecondary(context,tripActivity.getActivityTime());

        View actions = MakeDummy.linearLayoutHorizontal(
                context,
                Atom.button(context,"Trip Details",null),
                MakeDummy.verticalSeparator(context,Dp.normal()),
                Atom.button(context,"Request Travel Together",null),
                MakeDummy.verticalSeparator(context,Dp.normal()),
                Atom.button(context,"Chat",null),
                MakeDummy.verticalSeparator(context,Dp.normal()),
                Atom.button(context,"Safe Journey",null)
        );

        return MakeDummy.linearLayoutVertical(
                context,
                actorImage,
                actorName,
                description,
                activityDate,
                activityTime,
                actions

        );
    }
}
