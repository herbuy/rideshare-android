package client.ui.display.family;

import android.content.Context;
import android.graphics.Color;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import client.data.AppCallback;
import client.data.DummyData;
import client.data.LocalSession;
import client.data.Rest2;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForRateFamilyMember;
import core.businessobjects.FamilyMemberRating;
import core.businessobjects.FamilyMember;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.android.HerbuyRatingBar;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;
import retrofit2.Call;
import retrofit2.Response;

public class RateFamilyMemberDialog implements HerbuyView {
    private final Context context;
    private final FamilyMember member;

    public RateFamilyMemberDialog(Context context, FamilyMember member) {
        this.context = context;
        this.member = member;
    }

    final ParamsForRateFamilyMember paramsForRateFamilyMember = new ParamsForRateFamilyMember();
    @Override
    public View getView() {
        final LinearLayout itemView = MakeDummy.linearLayoutVertical(context);


        paramsForRateFamilyMember.setFamilyId(member.getFamilyId());
        paramsForRateFamilyMember.setMemberIdToRate(member.getMemberId());


        final Button saveButton = Atom.button(context, "Save", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doRate((Button) v);


            }

            private void doRate(final Button sender) {
                TransitionManager.beginDelayedTransition(itemView, new AutoTransition().setDuration(400));

                paramsForRateFamilyMember.setSessionId(LocalSession.instance().getId());
                //MessageBox.show(JsonEncoder.encode(paramsForRateFamilyMember), context);
                //Log.d("==== RATE DATA",JsonEncoder.encode(paramsForRateFamilyMember));

                sender.setText("Saving...");
                sender.setClickable(false);
                Rest2.api().rateFamilyMember(paramsForRateFamilyMember).enqueue(new AppCallback<List<FamilyMemberRating>>() {
                    @Override
                    protected void onSuccess(Call<List<FamilyMemberRating>> call, Response<List<FamilyMemberRating>> response) {

                        sender.setText("Done");
                        sender.setClickable(false);
                        sender.setBackgroundColor(Color.TRANSPARENT);
                        sender.setTextColor(Color.parseColor("#666666"));
                    }

                    @Override
                    protected void onError(Call<List<FamilyMemberRating>> call, Throwable t) {
                        sender.setText("Save");
                        sender.setClickable(true);
                        MessageBox.show(t.getMessage(), context);

                    }
                });
            }
        });
        MakeDummy.wrapContent(saveButton);
        itemView.addView(
                MakeDummy.paddingLeft(
                        RelativeLayoutFactory.alignRightOfWidget(
                        image(),
                        MakeDummy.paddingTop(MakeDummy.paddingLeft(name(),Dp.normal()),Dp.normal())
                        ),
                        Dp.scaleBy(3)
                )
        );
        itemView.addView(vspaceNormal());
        itemView.addView(rate());
        itemView.addView(vspaceNormal());
        itemView.addView(saveButton);

        itemView.setPadding(Dp.scaleBy(3), Dp.normal(), Dp.scaleBy(3), Dp.four_em());
        itemView.setGravity(Gravity.CENTER_HORIZONTAL);
        return itemView;
    }

    private View rate() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                Atom.textViewPrimaryNormal(context,"Time Keeping"),
                createRatingBarTimeKeeping(paramsForRateFamilyMember),
                vspaceNormal(),
                Atom.textViewPrimaryNormal(context, "Friendliness"),
                createRatingBarFriendliness(paramsForRateFamilyMember),
                vspaceNormal(),
                Atom.textViewPrimaryNormal(context, "Safe driving"),
                createRatingBarSafeDriving(paramsForRateFamilyMember),
                vspaceNormal(),
                Atom.textViewPrimaryNormal(context, "Other factors"),
                createRatingBarOtherFactors(paramsForRateFamilyMember)
        );

        MakeDummy.wrapContent(layout);
        return layout;
    }

    private View vspaceNormal() {
        return MakeDummy.lineSeparator(context,Dp.normal());
    }

    private View name() {
        TextView textView = Atom.textViewPrimaryBold(context, member.getMemberName());
        MakeDummy.wrapContent(textView);
        return textView;
    }

    private View image() {
        View view = MakeDummy.linearLayoutVertical(context, DummyData.randomCircleImageView(context, Dp.scaleBy(7), Dp.scaleBy(7)));
        MakeDummy.wrapContent(view);
        return view;
    }

    private View createRatingBarTimeKeeping(final ParamsForRateFamilyMember paramsForRateFamilyMember) {
        RatingBar ratingBarTimeKeeping = new HerbuyRatingBar(context);
        ratingBarTimeKeeping.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                paramsForRateFamilyMember.setRatingTimeKeeping(rating);
            }
        });
        return ratingBarTimeKeeping;
        //return MakeDummy.linearLayoutVertical(context,ratingBarTimeKeeping);
    }

    private RatingBar createRatingBarFriendliness(final ParamsForRateFamilyMember paramsForRateFamilyMember) {
        RatingBar ratingBarTimeKeeping = new HerbuyRatingBar(context);
        ratingBarTimeKeeping.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                paramsForRateFamilyMember.setRatingFriendliness(rating);
            }
        });
        return ratingBarTimeKeeping;
    }

    private RatingBar createRatingBarSafeDriving(final ParamsForRateFamilyMember paramsForRateFamilyMember) {
        RatingBar ratingBarTimeKeeping = new HerbuyRatingBar(context);
        ratingBarTimeKeeping.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                paramsForRateFamilyMember.setRatingSafeDriving(rating);
            }
        });
        return ratingBarTimeKeeping;
    }

    private RatingBar createRatingBarOtherFactors(final ParamsForRateFamilyMember paramsForRateFamilyMember) {
        RatingBar ratingBarTimeKeeping = new HerbuyRatingBar(context);
        ratingBarTimeKeeping.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                paramsForRateFamilyMember.setRatingOtherFactors(rating);
            }
        });
        return ratingBarTimeKeeping;
    }


}
