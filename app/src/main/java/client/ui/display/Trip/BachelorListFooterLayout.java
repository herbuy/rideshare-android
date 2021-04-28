package client.ui.display.Trip;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import java.util.List;

import client.data.AppCallback;
import client.data.LocalSession;
import client.data.Rest2;
import client.ui.GotoActivity;
import core.businessmessages.toServer.ParamsForGetFamilies;
import core.businessobjects.Family;
import core.businessobjects.Proposal;
import core.businessobjects.Trip;
import layers.render.Atom;
import libraries.android.GetParentActivity;
import libraries.android.MakeDummy;
import libraries.android.FooterLayout;
import libraries.android.MessageBox;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;
import resources.ItemColor;
import retrofit2.Call;
import retrofit2.Response;

public class BachelorListFooterLayout extends FooterLayout {

    BachelorListUpdater bachelorListUpdater;
    BachelorListNavigation listNavigation;

    public BachelorListFooterLayout(AppCompatActivity context, Trip trip) {
        super(context);
        initBachelorListUpdater(context, trip);
        initNavigation(context);

        setMainContent(
                RelativeLayoutFactory.alignAboveWidget(
                        mainContent(),
                        listNavigation.getView()
                )
        );
    }

    public View mainContent() {
        View mainContent = bachelorListUpdater.getView();
        mainContent.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return mainContent;
    }

    public void initBachelorListUpdater(final AppCompatActivity context, final Trip trip) {
        bachelorListUpdater = new BachelorListUpdater(context,trip){
            @Override
            protected void indicateProposalSent(Trip tripBeingViewed, Trip proposingTrip, Proposal sentProposal) {
                setFooterContent(proposalSent(tripBeingViewed, proposingTrip));
                setTransitionToSlideInFromBottom();
                showFooter();

            }

            @Override
            protected void indicateMatchFound(Trip tripBeingViewed, Trip proposingTrip, Proposal sentProposal) {
                setFooterContent(matchFound(tripBeingViewed, proposingTrip,sentProposal));
                setTransitionToSlideInFromBottom();
                showFooter();

            }
        };
    }


    public void initNavigation(final AppCompatActivity context) {
        listNavigation = new BachelorListNavigation(context){


            @Override
            protected void onShowNext() {
                if(bachelorListUpdater.hasNext()){
                    bachelorListUpdater.showNext();
                }
                else{
                    setFooterContent(endOfList());
                    setTransitionToSlideInFromBottom();
                    showFooter();
                    hideFooterAfterMillis(5000);
                }
            }

            @Override
            protected void onShowPrevious() {
                if(bachelorListUpdater.hasPrevious()){
                    bachelorListUpdater.showPrevious();
                }
                else{
                    setFooterContent(thisIsFirstItem());
                    setTransitionToSlideInFromBottom();
                    showFooter();
                    hideFooterAfterMillis(5000);
                }
            }

        };
    }

    private View endOfList() {
        return newSnackbar("That is all we have for now");
    }
    private View thisIsFirstItem() {
        return newSnackbar("This is the first person on the list");
    }
    private View newSnackbar(String text) {
        TextView snackbar = Atom.textView(getContext(),text);
        snackbar.setTextColor(Color.WHITE);
        snackbar.setBackgroundColor(Color.parseColor("#444444"));
        snackbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        snackbar.setPadding(Dp.normal(), Dp.two_em(), Dp.normal(), Dp.two_em());
        snackbar.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        return snackbar;
    }

    private View matchFound(Trip tripBeingViewed, Trip proposingTrip, final Proposal sentProposal) {

        View textView = newSnackbar(
                String.format(
                        "<b>Match Found</b><br/>You matched with %s<br/><small>You can now chat to plan your trip together.<br/><b>Enjoy your journey!!</b></small>",
                        tripBeingViewed.getForUserName()
                )
        );

        final Button btnChatNow = chatNowButton(sentProposal);

        Button btnContinueBrowsing = chatLaterButton();

        MakeDummy.wrapContent(btnContinueBrowsing);
        MakeDummy.wrapContent(btnChatNow);

        LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                getContext(),
                textView,
                btnChatNow,
                space(),
                btnContinueBrowsing
        );
        wrapper.setGravity(Gravity.CENTER_HORIZONTAL);
        wrapper.setBackgroundColor(Color.parseColor("#444444"));
        wrapper.setPadding(Dp.normal(), 0, Dp.normal(), Dp.normal());
        wrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return wrapper;
    }

    private View space() {
        return MakeDummy.invisible(Atom.textView(getContext(),"h"));
    }

    private Button chatLaterButton() {
        Button button = Atom.button(getContext(), "Later", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideFooterAndRefreshList();
                }
            });
        button.setBackgroundResource(R.drawable.secondary_button);
        button.setTextColor(ItemColor.primary());
        return button;
    }

    private Button chatNowButton(final Proposal sentProposal) {
        return Atom.button(getContext(), "Chat Now", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openChatActivity(v,sentProposal);
                }

                private void openChatActivity(View sender, Proposal sentProposal1) {
                    final Button btn = (Button) sender;
                    //=================
                    final ParamsForGetFamilies params = new ParamsForGetFamilies();
                    params.setSessionId(LocalSession.instance().getId());
                    params.setFamilyId(sentProposal1.getFamilyId());

                    btn.setClickable(false);
                    btn.setText("Wait...");
                    Rest2.api().getFamilies(params).enqueue(new AppCallback<List<Family>>() {
                        @Override
                        protected void onSuccess(Call<List<Family>> call, Response<List<Family>> response) {
                            btn.setClickable(true);
                            btn.setText("Chat Now");
                            hideFooterAndRefreshList();

                            if(response.body().size() > 0){
                                GotoActivity.familyChat(getContext(),response.body().get(0));
                            }
                            else{
                                MessageBox.show("ride info not found",getContext());
                            }

                        }

                        @Override
                        protected void onError(Call<List<Family>> call, Throwable t) {
                            btn.setClickable(true);
                            btn.setText("Chat Now");

                        }
                    });
                }
            });
    }

    public void hideFooterAndRefreshList() {
        bachelorListUpdater.getView().post(new Runnable() {
            @Override
            public void run() {
                bachelorListUpdater.refresh();
            }
        });
        hideFooter();
    }

    private View proposalSent(Trip tripBeingViewed, Trip proposingTrip){
        String title = proposingTrip.isDriver() ? "Your offer has been sent" : "Your request has been sent";
        View textView = newSnackbar(String.format(
                "<b>%s</b><br/><small>You will be notified when you match with %s.</small>",
                title, tripBeingViewed.getForUserName()
        ));


        Button btnOk = Atom.button(getContext(), "Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFooterAndRefreshList();
            }
        });
        MakeDummy.wrapContent(btnOk);

        LinearLayout wrapper = MakeDummy.linearLayoutVertical(getContext(),textView,btnOk);
        wrapper.setGravity(Gravity.CENTER_HORIZONTAL);
        wrapper.setBackgroundColor(Color.parseColor("#444444"));
        wrapper.setPadding(Dp.normal(), 0, Dp.normal(), Dp.normal());
        wrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return wrapper;

    }

}
