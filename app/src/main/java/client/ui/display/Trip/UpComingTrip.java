package client.ui.display.Trip;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import client.ui.GotoActivity;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.display.family.NotificationBadge;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Trip;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MakeStateListDrawable;
import libraries.UnixTimestampInMillis;
import libraries.HerbuyCalendar;
import libraries.android.MinOfScreenWidthAndHeight;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;
import resources.ItemColor;

public class UpComingTrip implements HerbuyView {
    Context context;
    FrameLayoutBasedHerbuyStateView view;


    public UpComingTrip(Context context, Trip trip) {
        init(context);
        update(trip);
        listenForUpdates(trip);

    }

    private String getClassName(){
        return getClass().getSimpleName();
    }

    private void listenForUpdates(final Trip trip) {

    }

    public void update(Trip trip) {
        view.render(success(trip));
    }

    private void init(Context context) {
        this.context = context;
        view = new FrameLayoutBasedHerbuyStateView(context);
    }



    public View success(final Trip trip){



        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setColumnStretchable(2, true);
        tableLayout.addView(makeRow(trip));
        tableLayout.setPadding(Dp.one_point_5_em(), Dp.scaleBy(1f), Dp.one_point_5_em(), Dp.scaleBy(1f));
        tableLayout.setClickable(true);
        tableLayout.setBackground(MakeStateListDrawable.where(Color.WHITE,ItemColor.highlight()));
        tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.tripDetails(context, trip);
            }
        });

        View content = MakeDummy.linearLayoutVertical(
                context,
                tableLayout,
                MakeDummy.lineSeparator(context,Dp.scaleBy(0.5f),Color.parseColor("#eeeeee"))
        );


        return content;




    }

    private View makeRow(Trip trip) {
        TableRow row = new TableRow(context);

        View left = left(trip);
        row.addView(left);
        row.addView(MakeDummy.invisible(Atom.textViewPrimaryNormal(context, "he")));
        row.addView(right(trip));

        left.setLayoutParams(new TableRow.LayoutParams(
                MinOfScreenWidthAndHeight.oneTenth(context),
                MinOfScreenWidthAndHeight.oneTenth(context)
        ));
        return row;
    }

    private View left(final Trip trip) {
        int circleDiameter = Dp.scaleBy(1);
        int circleColor = trip.isDriver() ? Color.parseColor("#30AC4A") : Color.parseColor("#DA4A2F");

        int icon = trip.isDriver() ? R.drawable.ic_trip_type_driving : R.drawable.ic_menu_returning_black;
        ImageView imageView = MakeDummy.imageView(context,circleDiameter,circleDiameter,icon);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setAlpha(0.7f);

        return imageView;


        /*
        final ImageView view = DummyData.randomCircleImageView(context);
        LoadImage.fromFileShortName(view,trip.getForUserProfilePic());
        NotificationService.subscribeForProfilePhotoChanges(LocalSession.instance().getUserId(), new NotificationService.Listener(){
            @Override
            public void run(NotificationMessage notificationMessage) {
                LoadImage.fromFileShortName(view, trip.getForUserProfilePic());
                //MessageBox.show("Received notif for profile changed",TripListActivity.this);
            }
        });
        return view;*/
    }

    private View right(Trip trip) {
        TableLayout left = MakeDummy.table(
                context,
                //MakeDummy.row(context, tripType(trip)),
                MakeDummy.row(context, route(trip)),
                MakeDummy.row(context, tripDateTime(trip))
        );

        left.setPadding(0,0,Dp.normal(),0);

        return RelativeLayoutFactory.alignleftOfWidget(left,NotificationBadge.where(context,trip.getUnseenMessageCount()).getView());
        //return left;

    }

    private View tripDateTime(Trip trip) {
        TextView view = Atom.textView(context,
                new HerbuyCalendar(UnixTimestampInMillis.fromDate(trip.getTripDate())).getFriendlyDate()
        );
        //view.setTextSize(Dp.scaleBy(0.9f));
        return view;
    }

    private View route(Trip trip) {
        TextView route = Atom.textViewPrimaryBold(
                context,
                String.format("%s to %s", trip.getOriginShort(),trip.getDestinationShort())
        );
        route.setAlpha(0.8f);
        //route.setTextSize(Dp.scaleBy(0.9f));
        return route;
    }


    @Override
    public View getView() {
        return view.getView();
    }

    private View vspace() {
        return vspaceNormal();
    }

    private View vspaceNormal() {
        return MakeDummy.lineSeparator(context, Dp.normal());
    }

    private View vspaceHalf() {
        return MakeDummy.lineSeparator(context, Dp.scaleBy(0.5f));
    }

    private NotificationBadge notificationBadge;

    private View actions(Trip trip) {

        notificationBadge = NotificationBadge.where(context, trip.getUnseenMessageCount());
        View btn = trip.isDriver() ? driverActions(trip) : passengerActions(trip);
        if(trip.getUnseenMessageCount() > 0){
            btn.setBackgroundResource(R.drawable.button_accent);
        }

        btn.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return btn;

    }


    private View passengerActions(final Trip trip) {
        String text = String.format("Available<br/>Drivers%s",trip.getUnseenMessageCount() > 0 ? String.format("(%d)",trip.getUnseenMessageCount()):"");

        Button btn = Atom.button(context, text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.tripDetails(context, trip);
            }
        });
        MakeDummy.wrapContent(btn);
        btn.setTextSize(Dp.scaleBy(0.7f));
        return btn;
    }

    private View driverActions(final Trip trip) {
        String text = String.format("<small>Available</small><br/>Passengers%s",trip.getUnseenMessageCount() > 0 ? String.format("(%d)",trip.getUnseenMessageCount()):"");
        Button btn = Atom.button(context, text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.tripDetails(context, trip);
            }
        });
        MakeDummy.wrapContent(btn);
        btn.setTextSize(Dp.scaleBy(0.7f));
        btn.setGravity(Gravity.CENTER_HORIZONTAL);
        return btn;
    }

    private View tripType(Trip trip) {
        String text = trip.isDriver() ? "Passengers": "Drivers";
        TextView textView = Atom.textViewPrimaryBold(context, text);
        textView.setTextColor(ItemColor.primary());
        return textView;
    }


}
