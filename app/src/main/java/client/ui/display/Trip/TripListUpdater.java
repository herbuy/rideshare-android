package client.ui.display.Trip;


import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import java.util.Comparator;
import java.util.List;

import client.data.LocalSession;
import client.data.Rest2;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.display.Trip.tt_requests.ProposalSentEvent;
import client.ui.display.family.AppListUpdater;
import client.ui.display.notification.NotificationService;
import core.businessmessages.toServer.ParamsForGetTrips;
import core.businessobjects.NotificationMessage;
import core.businessobjects.Trip;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.ObserverList;
import libraries.android.SquaredImageVIew;
import libraries.android.HerbuyStateView;
import libraries.android.HerbuyViewGroup;

import libraries.android.MinOfScreenWidthAndHeight;
import resources.Dp;
import retrofit2.Call;


public class TripListUpdater extends AppListUpdater<Trip> {
    FrameLayoutBasedHerbuyStateView view;

    public TripListUpdater(Context context) {
        super(context);
    }



    @Override
    protected void init() {
        view = new FrameLayoutBasedHerbuyStateView(context);

        super.init();
    }

    Runnable refreshRunnable;
    private NotificationService.Callback notifCallback;

    @Override
    protected List<Trip> getCachedData() {
        return new TripListCache().selectAll();
    }

    @Override
    protected void clearCache() {
        new TripListCache().clear();
    }

    @Override
    protected void addToCache(Trip item) {
        new TripListCache().save(item.getTripId(), item);
    }

    @Override
    protected void updateView(List<Trip> data) {

        view.render(makeTripListView(data));
    }

    Comparator<Trip> comparator = new Comparator<Trip>() {
        @Override
        public int compare(Trip lhs, Trip rhs) {
            if(lhs.getTimestampLastUpdatedInMillis() > rhs.getTimestampLastUpdatedInMillis()){
                return -1;
            }
            else if(lhs.getTimestampLastUpdatedInMillis() < rhs.getTimestampLastUpdatedInMillis()){
                return 1;
            }
            else{
                return 0;
            }
        }
    };

    private View makeTripListView(List<Trip> data) {

        HerbuyViewGroup<Trip> listView = new HerbuyViewGroup<Trip>(context) {
            @Override
            protected ViewGroup createAbsListView(Context context) {
                return MakeDummy.linearLayoutVertical(context);
            }

            @Override
            protected View createItemView(Context context, Trip item) {
                return new UpComingTrip(context,item).getView();
            }
        };
        listView.setComparator(comparator);
        listView.setContent(data);

        return MakeDummy.scrollView(listView.getView());
    }

    @Override
    protected void renderEmpty() {
        ImageView image = new SquaredImageVIew(context);
        image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        image.setImageResource(R.drawable.img_trips_empty2);
        image.setAlpha(0.5f);
        image.setLayoutParams(new ViewGroup.LayoutParams(
                MinOfScreenWidthAndHeight.oneThird(context),
                MinOfScreenWidthAndHeight.oneThird(context)
        ));

        TextView text = Atom.textView(context, "<b>Heading somewhere?</b><br/>Let us find you a lift or someone to travel with and share fuel costs");
        MakeDummy.wrapContent(text);
        text.setLayoutParams(new ViewGroup.LayoutParams(
                MinOfScreenWidthAndHeight.twoThirds(context),
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        text.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                context,
                MakeDummy.lineSeparator(context,MinOfScreenWidthAndHeight.oneSixth(context)),
                text,
                MakeDummy.lineSeparator(context, Dp.normal()),
                image,
                MakeDummy.lineSeparator(context,MinOfScreenWidthAndHeight.oneSixth(context))
        );
        wrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        wrapper.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        wrapper.setBackgroundResource(R.drawable.header_background);

        FrameLayout finalWrapper = new FrameLayout(context);
        finalWrapper.addView(wrapper);
        finalWrapper.setPadding(Dp.normal(),Dp.normal(),Dp.normal(),Dp.normal());

        view.render(finalWrapper);

    }

    @Override
    protected void renderLoading() {
        view.render(Atom.centeredText(context,"Loading..."));
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
        params.setIsForUserId(LocalSession.instance().getUserId());
        return params;
    }



    @Override
    public View getView() {
        return view.getView();
    }


    @Override
    public void listenForRefreshTriggers(final Runnable runnable) {
        super.listenForRefreshTriggers(runnable);
        this.refreshRunnable = runnable;

        TripScheduledEvent.instance().add(new ObserverList.Observer<Trip>() {

            @Override
            public void notifyEvent(Trip eventArgs) {
                refresh();
            }
        });

        ProposalSentEvent.instance().add(new ObserverList.Observer<ProposalSentEvent.TransactionData>() {
            @Override
            public void notifyEvent(ProposalSentEvent.TransactionData eventArgs) {
                refresh();
            }
        });

    }

    @Override
    protected String[] getNotifTypesInterestedIn() {
        return new String[]{
                NotificationMessage.Type.POTENTIAL_MATCH_FOUND
        };
    }

    NotificationService.Callback callback;
    @Override
    protected NotificationService.Callback getNotificationCallback() {
        if(callback == null){
            callback = new NotificationService.Callback() {
                @Override
                public void run(NotificationMessage notificationMessage) {
                    refresh();
                }
            };
        }
        return callback;
    }

    private void refresh() {
        if(refreshRunnable != null){
            refreshRunnable.run();
        }
    }


}
