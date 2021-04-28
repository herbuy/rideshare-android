package client.ui.display.Trip;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import client.data.LocalSession;
import client.data.Rest2;
import client.ui.GotoActivity;
import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskBrowsePotentialMatches;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.display.family.AppListUpdater;
import client.ui.display.notification.NotificationService;
import core.businessmessages.toServer.ParamsForGetTrips;
import core.businessobjects.NotificationData;
import core.businessobjects.NotificationMessage;
import core.businessobjects.Proposal;
import core.businessobjects.Trip;
import layers.render.Atom;
import libraries.JsonEncoder;
import libraries.android.MakeDummy;
import libraries.ObserverList;
import libraries.android.RelativeLayoutFactory;
import libraries.android.SquaredImageVIew;
import libraries.android.AdapterForFragmentPager;
import libraries.android.HerbuyStateView;

import libraries.android.MinOfScreenWidthAndHeight;
import libraries.android.HerbuyViewPager;
import resources.Dp;
import resources.ItemColor;
import retrofit2.Call;


public abstract class BachelorListUpdater extends AppListUpdater<Trip> {
    FrameLayoutBasedHerbuyStateView view;
    AppCompatActivity activity;
    private Trip proposingTrip;

    public BachelorListUpdater(AppCompatActivity context, Trip proposingTrip) {
        super(context);
        this.proposingTrip = proposingTrip;
        start();

    }

    @Override
    protected boolean autoStart() {
        return false;
    }

    @Override
    protected void init() {
        view = new FrameLayoutBasedHerbuyStateView(context);
        activity = (AppCompatActivity)context;

        super.init();


    }

    List<Trip> cachedData = new ArrayList<>();
    @Override
    protected List<Trip> getCachedData() {
        return cachedData;
    }

    @Override
    protected void clearCache() {
        cachedData = new ArrayList<>();
    }

    @Override
    protected void addToCache(Trip item) {
        cachedData.add(item);
    }

    @Override
    protected void updateView(List<Trip> data) {
        view.render(success(data));
    }


    ViewPager viewPager;
    List<Trip> itemList = new ArrayList<>();

    private Comparator<Trip> relevancyBasedComparator = new Comparator<Trip>() {
        @Override
        public int compare(Trip lhs, Trip rhs) {
            TripRelevancyScore scoreForLeft = new TripRelevancyScore(lhs, proposingTrip);
            TripRelevancyScore scoreForRight = new TripRelevancyScore(rhs, proposingTrip);

            if(scoreForLeft.get() > scoreForRight.get()){
                return -1;
            }
            else if(scoreForLeft.get() < scoreForRight.get()){
                return 1;
            }
            else{
                return 0;
            }

        }
    };

    private View success(List<Trip> body) {
        itemList = body;
        Collections.sort(itemList,relevancyBasedComparator);

        View root = LayoutInflater.from(context).inflate(R.layout.view_pager,null);
        viewPager = (HerbuyViewPager)root.findViewById(R.id.container);
        AdapterForFragmentPager adapter = new AdapterForFragmentPager(activity.getSupportFragmentManager());

        for(Trip trip: body){
            adapter.addTab("",new SwipableTripView(context,trip, proposingTrip){
                @Override
                protected void onProposalSent(Proposal sentProposal, Trip tripBeingViewed, Trip proposingTrip) {

                    //if a match was found, goto the matched activity()
                    if(sentProposal.isAccepted()){
                        indicateMatchFound(tripBeingViewed,proposingTrip,sentProposal);
                    }
                    else{
                        indicateProposalSent(tripBeingViewed, proposingTrip,sentProposal);

                    }

                }
            }.getView());
            //viewPager.addView(new SwipableTripView(context,trip).getView());
        }
        viewPager.setAdapter(adapter);
        return root;


    }

    protected abstract void indicateProposalSent(Trip tripBeingViewed, Trip proposingTrip, Proposal sentProposal);

    protected abstract void indicateMatchFound(Trip tripBeingViewed, Trip proposingTrip, Proposal sentProposal);

    public void showNext(){
        if(hasNext()){
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    public boolean hasNext() {
        return viewPager != null && viewPager.getCurrentItem() < itemList.size() - 1;
    }

    public void showPrevious(){
        if(hasPrevious()){
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public boolean hasPrevious() {
        return viewPager != null && viewPager.getCurrentItem() > 0;
    }


    @Override
    protected void renderEmpty() {



        ViewGroup wrapper = RelativeLayoutFactory.alignAboveWidget(
                upperPartOfEmpty(),
                deadEndActions()
        );
        wrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        wrapper.setBackgroundResource(R.drawable.header_background);
        wrapper.setPadding(Dp.two_em(),0,Dp.two_em(),Dp.normal());

        FrameLayout finalWrapper = new FrameLayout(context);
        finalWrapper.addView(wrapper);
        finalWrapper.setPadding(Dp.normal(),0,Dp.normal(),Dp.normal());

        view.render(finalWrapper);

    }

    private View upperPartOfEmpty() {

        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                textOfEmpty(),
                space(),
                imageOfEmpty(),
                space()
        );
        layout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        return layout;
    }

    private View textOfEmpty() {
        String travellerTypeInterestedIn = proposingTrip.isDriver() ? "passengers" : "drivers";

        TextView text = Atom.textView(
                context,
                String.format("You will be notified when %s heading your direction become available.",travellerTypeInterestedIn)
        );

        MakeDummy.wrapContent(text);
        text.setLayoutParams(new ViewGroup.LayoutParams(
                MinOfScreenWidthAndHeight.twoThirds(context),
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        text.setGravity(Gravity.CENTER_HORIZONTAL);

        return text;
    }

    private View imageOfEmpty() {
        ImageView image = new SquaredImageVIew(context);
        image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        image.setImageResource(R.drawable.img_trips_empty2);
        image.setAlpha(0.5f);
        image.setLayoutParams(new ViewGroup.LayoutParams(
                MinOfScreenWidthAndHeight.oneQuarter(context),
                MinOfScreenWidthAndHeight.oneQuarter(context)
        ));
        return image;

    }

    private View deadEndActions() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                scheduleTrip(),
                MakeDummy.lineSeparator(context,Dp.scaleBy(0.5f)),
                viewMyTrips()
        );

        return layout;
    }

    private View space() {
        return MakeDummy.invisible(MakeDummy.textView(context,"h"));
    }

    private View scheduleTrip() {
        String text = "Schedule another trip";

        TextView btn = Atom.buttonTextView(context,text,new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                GotoActivity.scheduleTrip(context,null);
                EventBusForTaskBrowsePotentialMatches.browsingEnded.notifyObservers(null);
            }
        });
        return btn;
    }

    private View viewMyTrips() {
        String text = "View my trips";

        TextView btn = Atom.buttonTextView(context,text,new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                GotoActivity.tripList(context);
                EventBusForTaskBrowsePotentialMatches.browsingEnded.notifyObservers(null);
            }
        });

        btn.setBackgroundResource(R.drawable.secondary_button);
        btn.setTextColor(ItemColor.primary());
        return btn;
    }


    @Override
    protected void renderLoading() {
        view.render(Atom.centeredText(context, "Loading..."));
    }

    @Override
    protected void renderError(String message, final Runnable runnable) {
        view.renderError(message, new HerbuyStateView.OnRetryLoad() {
            @Override
            public void run() {
                runnable.run();
            }
        });
    }

    @Override
    protected Call<List<Trip>> getCall() {
        return Rest2.api().getTrips(params());
    }

    protected ParamsForGetTrips params() {
        ParamsForGetTrips params = new ParamsForGetTrips();
        params.setSessionId(LocalSession.instance().getId());
        params.setAcceptingProposalsFromTripId(proposingTrip.getTripId());
        params.setTripIdMessagesSeen(proposingTrip.getTripId());
        return params;
    }



    @Override
    public View getView() {
        return view.getView();
    }


    @Override
    public void listenForRefreshTriggers(final Runnable runnable) {
        super.listenForRefreshTriggers(runnable);
        TripScheduledEvent.instance().add(new ObserverList.Observer<Trip>() {

            @Override
            public void notifyEvent(Trip eventArgs) {
                runnable.run();
            }
        });

    }

    @Override
    protected String[] getNotifTypesInterestedIn() {
        return new String[]{NotificationMessage.Type.POTENTIAL_MATCH_FOUND};
    }

    //we shall need to receive data updates
    NotificationService.Callback callback;
    @Override
    protected NotificationService.Callback getNotificationCallback() {
        if(callback == null){
            callback = new NotificationService.Callback() {
                @Override
                public void run(NotificationMessage notificationMessage) {
                    NotificationData data = JsonEncoder.decode(JsonEncoder.encode(notificationMessage.getData()),NotificationData.class);
                    //check if the notification was meant for this particular trip
                    if(data.getTripId().equalsIgnoreCase(proposingTrip.getTripId())){
                        refresh();
                    }
                }
            };
        }
        return callback;
    }

    public void refresh() {
        tryUpdateCache();
    }
}
