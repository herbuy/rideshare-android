package client.ui.display.Trip;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import java.util.Locale;

import client.data.DummyData;
import client.ui.display.Trip.tt_requests.ProposalSentEvent;
import client.ui.display.Trip.tt_requests.TTRSendController;
import client.ui.display.files.LoadImage;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Proposal;
import core.businessobjects.Trip;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.UnixTimestampInMillis;
import libraries.HerbuyCalendar;
import libraries.android.HerbuyRatingBar;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;

public abstract class SwipableTripView implements HerbuyView {
    private final Trip proposingTrip;
    private Context context;
    private Trip tripBeingViewed;

    public SwipableTripView(Context context, Trip tripBeingViewed, Trip proposingTrip) {
        this.context = context;
        this.tripBeingViewed = tripBeingViewed;
        this.proposingTrip = proposingTrip;
    }
    @Override
    public View getView() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                space(),
                space(),
                overview(),
                space(),
                from(),
                via(),
                to(),
                date(),
                carNumberPlate(),
                seatsLeft(),
                fuelCharge(),
                actions(),
                space(),
                space()
                //ownerActions(),
                //nonOwnerActions()
        );
        layout.setTag(this);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setBackgroundColor(Color.WHITE);
        layout.setPadding(Dp.normal(),0,Dp.normal(),0);

        //DELETE THIS CODE AFTER TESTING THAT FEEDBACK IS APPROPRIATE AFTER MATCH
        //we want to simulate what will happen when proposal sent before actual sending, so lets try it here
        /*
        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                Proposal proposal = new Proposal();
                String[] potentialStatuses = new String[]{
                        Proposal.Status.ACCEPTED,
                        Proposal.Status.PENDING
                };
                int selectedIndex = (Math.abs(new Random().nextInt())) % potentialStatuses.length;
                String selectedStatus = potentialStatuses[selectedIndex];
                proposal.setProposalStatus(selectedStatus);
                SwipableTripView.this.onProposalSent(proposal,tripBeingViewed,proposingTrip);
            }
        }, 2000);*/

        return MakeDummy.scrollView(layout);

    }

    private View overview() {

        View image = image();

        View right = MakeDummy.linearLayoutVertical(
                context,
                name(),
                lastSeen(),
                rating()
        );
        right.setPadding(Dp.scaleBy(1.5f),0,Dp.normal(),0);


        RelativeLayout wrapper = RelativeLayoutFactory.alignRightOfWidget(
                image,
                right
        );

        image.setLayoutParams(new RelativeLayout.LayoutParams(Dp.scaleBy(10),Dp.scaleBy(10)));

        return wrapper;
    }

    private View rating() {
        RatingBar ratingBar = new HerbuyRatingBar(context);
        ratingBar.setRating(tripBeingViewed.getUserRatingOverall());
        ratingBar.setIsIndicator(true);
        return ratingBar;
    }

    private View lastSeen() {
        //THIS VIEW IS HIDDEN COZ IT IS BUGGY IN COMPUTING TIME AGO
        HerbuyCalendar date = new HerbuyCalendar(tripBeingViewed.getForUserTimestampLastSeenMillis()); //new HerbuyCalendar(System.currentTimeMillis());
        String dateLastSeen = date.getFriendlyDate();
        String timeLastSeen = date.getTimeTwelveHour();
        String lastSeenText = String.format("Last Seen: %s, %s", dateLastSeen, timeLastSeen);
        //String lastSeenText = String.format("Last Seen: %s", date.getTimeAgo());

        TextView textView = Atom.textView(context,lastSeenText,Dp.scaleBy(0.7f));
        MakeDummy.wrapContent(textView);
        textView.setVisibility(View.GONE);
        return textView;
    }

    private View via() {
        String viaPrefix = tripBeingViewed.isDriver() ? "Via" : "Passing via";

        TextView view = Atom.textViewPrimaryNormal(
                context,
                String.format("<b>%s</b> %s",viaPrefix, tripBeingViewed.getPassingViaCSV())
        );
        view.setBackgroundResource(R.drawable.border_bottom);
        view.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());

        if(tripBeingViewed.getPassingViaCSV() == null || tripBeingViewed.getPassingViaCSV().trim().equalsIgnoreCase("")){
            view.setVisibility(View.GONE);
        }
        return view;
    }

    private View fuelCharge() {
        TextView view = Atom.textViewPrimaryNormal(
                context,
                String.format(
                        "<b>Fuel Contribution</b>: %s",
                        tripBeingViewed.getFuelCharge() == 0 ? "To be discussed" : "Ugx. " + tripBeingViewed.getFuelCharge()
                )
        );
        view.setBackgroundResource(R.drawable.border_bottom);
        view.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());

        if (!tripBeingViewed.isDriver()) {
            view.setVisibility(View.GONE);
        }

        return view;
    }

    private View seatsLeft() {
        int seatsLeft = tripBeingViewed.getMaxConcurrentTTMarriages();
        String seatLabel = tripBeingViewed.getMaxConcurrentTTMarriages() == 1 ? "seat" : "seats";

        String text = tripBeingViewed.isDriver() ?
                String.format(Locale.ENGLISH,"Has <b>%s %s</b> available",seatsLeft,seatLabel) :
                String.format(Locale.ENGLISH,"Wants <b>%s %s</b>",seatsLeft,seatLabel);

        TextView view = Atom.textViewPrimaryNormal(
                context,
                text
        );
        view.setBackgroundResource(R.drawable.border_bottom);
        view.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());

        if (!tripBeingViewed.isDriver()) {
            view.setVisibility(View.GONE);
        }
        return view;
    }

    private View carNumberPlate() {
        TextView view = Atom.textViewPrimaryNormal(
                context,
                String.format("Driving <b>%s</b><br/>%s", tripBeingViewed.getCarModel(), tripBeingViewed.getCarRegNumber())
        );
        view.setBackgroundResource(R.drawable.border_bottom);
        view.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());

        if (!tripBeingViewed.isDriver()) {
            view.setVisibility(View.GONE);
        }

        return view;
    }

    private View date() {
        HerbuyCalendar date = new HerbuyCalendar(UnixTimestampInMillis.fromDate(tripBeingViewed.getTripDate()));
        String dateString = (date.isToday() || date.isTomorrow() || date.isYesterday() ? "" : "<b>On</b> ") + date.getFriendlyDate();

        String timeString = UnixTimestampInMillis.toTimeTwelveHour(UnixTimestampInMillis.fromHourAndMinute(
                tripBeingViewed.getTripTime()
        ));

        TextView dateTimeView = Atom.textViewPrimaryNormal(
                context,
                String.format(
                        "%s<br/><b>At</b> around %s",
                        dateString,
                        timeString
                )
        );
        dateTimeView.setBackgroundResource(R.drawable.border_bottom);
        dateTimeView.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());
        return dateTimeView;
    }


    private View actions() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                space(),
                sendRequest()

        );
        MakeDummy.wrapContent(layout);
        return layout;

    }


    private View sendRequest() {

        TTRSendController ttrSendController = new TTRSendController(context, tripBeingViewed, proposingTrip) {
            @Override
            protected void onSuccess(Proposal sentProposal) {

                SwipableTripView.this.onProposalSent(sentProposal,tripBeingViewed,proposingTrip);
                ProposalSentEvent.instance().notifyObservers(new ProposalSentEvent.TransactionData(proposingTrip,tripBeingViewed,sentProposal));
            }
        };
        View view = ttrSendController.getView();
        MakeDummy.wrapContent(view);
        return view;

    }

    protected abstract void onProposalSent(Proposal sentProposal, Trip receivingTrip, Trip proposingTrip);


    private View name() {
        return MakeDummy.wrapContent(Atom.textViewPrimaryBold(context, tripBeingViewed.getForUserName()));
    }

    private View from() {
        //pick up location is for passenger, for driver is setting off from

        String label =  tripBeingViewed.isDriver() ? "<b>Setting off from</b>" : "<b>Pick-up location</b>";


        TextView view = Atom.textViewPrimaryNormal(context, String.format("%s<br/>%s", label, tripBeingViewed.getOriginName()));
        view.setBackgroundResource(R.drawable.border_bottom);
        view.setPadding(Dp.normal(),Dp.normal(),Dp.normal(),Dp.normal());

        return view;


    }

    private View to() {
        if (tripBeingViewed.isToSameDestination(proposingTrip)) {


        } else if (tripBeingViewed.isToSameDestination(proposingTrip)) {

            //say stopping in:xyz
        } else {

        }

        //drop of location is for passenger, driver is
        TextView view = Atom.textViewPrimaryNormal(context, String.format("<b>To:</b> %s", tripBeingViewed.getDestinationName()));
        view.setBackgroundResource(R.drawable.border_bottom);
        view.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());
        return view;
    }

    private View space() {
        return MakeDummy.lineSeparator(context, Dp.normal());
    }

    private View image() {
        ImageView imageView = DummyData.randomCircleImageView(context);
        LoadImage.fromFileShortName(imageView,tripBeingViewed.getForUserProfilePic());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(Dp.scaleBy(10), Dp.scaleBy(10)));
        return imageView;
    }


}
