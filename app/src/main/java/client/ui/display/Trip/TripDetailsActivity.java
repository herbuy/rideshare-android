package client.ui.display.Trip;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import client.ui.IntentExtras;
import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskBrowsePotentialMatches;
import client.ui.display.Trip.schedule_trip.p20210410.EventBusForTaskUploadTrip;
import core.businessobjects.Trip;
import libraries.JsonEncoder;
import layers.render.Atom;
import libraries.ObserverList;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.UnixTimestampInMillis;
import libraries.HerbuyCalendar;
import libraries.android.HideActionBar;
import libraries.android.Sdk;
import resources.Dp;
import resources.ItemColor;
import shared.BaseActivity;

public class TripDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView());
        EventBusForTaskBrowsePotentialMatches.browsingEnded.add(new ObserverList.Observer<Void>() {
            @Override
            public void notifyEvent(Void eventArgs) {
                finish();
            }
        });
    }

    private View contentView() {
        Trip trip = JsonEncoder.decode(getIntent().getStringExtra(IntentExtras.tripDetails),Trip.class);
        if(trip == null){
            MessageBox.show("Trip not specified",this);
            return new TextView(this);
        }
        else{
            HideActionBar.run(this);
            View view = MakeDummy.linearLayoutVertical(
                    this,
                    header(trip),
                    vspace(),
                    new BachelorListFooterLayout(this,trip).getView()
            );
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setBackgroundColor(Color.parseColor("#EEEEEE"));

            return view;
        }


    }


    private View vspace() {
        return MakeDummy.lineSeparator(this, Dp.normal());
    }

    private View header(Trip trip) {
        TextView titlePrefix = Atom.textViewPrimaryBold(this, trip.isDriver() ? "Passengers along route" : "Drivers taking route");

        String fromPrefix = trip.isDriver() ? "From %s" : "Via %s";

        TextView from = Atom.textViewPrimaryNormal(this, String.format(fromPrefix, trip.getOriginName()));
        TextView to = Atom.textViewPrimaryNormal(this, String.format("To %s", trip.getDestinationName()));

        long dateInMillis = UnixTimestampInMillis.fromDateTime(trip.getTripDate(),
                "yyyy-MM-dd", "yyyy-M-d", "yyyy-M-dd", "yyyy-MM-d"
        );

        HerbuyCalendar calendar = new HerbuyCalendar();
        calendar.setTimeInMillis(dateInMillis);

        titlePrefix.setTextColor(ItemColor.primary());
        from.setTextSize(Dp.scaleBy(0.7f));
        from.setAlpha(0.7f);
        to.setTextSize(Dp.scaleBy(0.9f));


        LinearLayout header = MakeDummy.linearLayoutVertical(
                this,
                titlePrefix,
                from,
                to
        );
        header.setPadding(Dp.two_em(), Dp.normal(), Dp.two_em(), Dp.normal());
        header.setBackgroundResource(R.drawable.header_background);
        Sdk.setElevation(15, header);
        return header;
    }

}
