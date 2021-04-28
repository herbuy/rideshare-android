package client.ui.display.Trip.reactions;

import android.content.Context;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import client.ui.libraries.HerbuyView;
import core.businessobjects.Trip;
import core.businessobjects.TripReaction;
import libraries.android.MakeDummy;
import resources.Dp;

public abstract class ListOfPossibleReactionsToTrip implements HerbuyView {
    Context context;
    Trip trip;
    HorizontalScrollView view;

    public ListOfPossibleReactionsToTrip(Context context, Trip trip) {
        this.context = context;
        this.trip = trip;
        view  = new HorizontalScrollView(context);
        view.setHorizontalScrollBarEnabled(false);
        view.addView(possibleReactions());
        view.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
    }

    private View possibleReactions() {
        LinearLayout layout = MakeDummy.linearLayoutHorizontal(
                context,
                likeButton(),
                safeJourney(),
                welcome()
        );
        return layout;


        /*return MakeDummy.padding(
                MakeDummy.textView(context,"LIKE | SAFE JOURNEY | WELCOME | AWESOME |GOOD 2 KNOW | WHO CARES"), Dp.normal()
        );*/
    }

    private View likeButton() {
        return new SendReactionController(context,trip){
            @Override
            protected String getReactionType() {
                return TripReaction.Type.LIKE;
            }

            @Override
            protected CharSequence getDisplayText() {
                return "Like";
            }

            @Override
            protected void onReactionSent(TripReaction tripReaction) {
                ListOfPossibleReactionsToTrip.this.onReactionSent(tripReaction);
            }
        }.getView();
    }

    private View safeJourney() {
        return new SendReactionController(context,trip){
            @Override
            protected String getReactionType() {
                return TripReaction.Type.SAFE_JOURNEY;
            }

            @Override
            protected CharSequence getDisplayText() {
                return "Safe Journey";
            }

            @Override
            protected void onReactionSent(TripReaction tripReaction) {
                ListOfPossibleReactionsToTrip.this.onReactionSent(tripReaction);
            }
        }.getView();
    }

    private View welcome() {
        return new SendReactionController(context,trip){
            @Override
            protected String getReactionType() {
                return TripReaction.Type.WELCOME;
            }

            @Override
            protected CharSequence getDisplayText() {
                return "Welcome";
            }

            @Override
            protected void onReactionSent(TripReaction tripReaction) {
                ListOfPossibleReactionsToTrip.this.onReactionSent(tripReaction);
            }
        }.getView();
    }

    protected abstract void onReactionSent(TripReaction tripReaction);

    @Override
    public View getView() {

        return view;
    }

}
