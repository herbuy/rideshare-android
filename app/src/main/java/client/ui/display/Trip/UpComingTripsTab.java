package client.ui.display.Trip;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import client.ui.GotoActivity;
import client.ui.display.Trip.schedule_trip.trip_form_display_params.TripFormDisplayParameters;
import client.ui.libraries.HerbuyView;
import layers.render.Atom;
import libraries.android.HideKeyboard;
import libraries.android.MakeDummy;
import libraries.android.FabLayout;
import libraries.android.FooterLayout;
import libraries.android.RelativeLayoutFactory;
import resources.Dp;

public class UpComingTripsTab implements HerbuyView {
    private final Activity context;

    View view;

    public UpComingTripsTab(Activity context) {
        this.context = context;
        view = createView();
    }

    private View createView() {
        final FooterLayout footerLayout = new FooterLayout(context);

        FabLayout fabLayout = new FabLayout(context)
                .setContent(createTripList())
                .setFabImageResource(android.R.drawable.ic_menu_search)
                .setFabClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //v.setTransitionName(TransitionName.scheduleTripActivity);
                        //GotoActivity.scheduleTrip(context, TripFormDisplayParameters.PASSENGER);

                        footerLayout.setFooterLocationToBottom();
                        footerLayout.setTransitionToSlideInFromBottom();
                        footerLayout.showFooter();
                        footerLayout.hideFooterAfterMillis(3000);

                    }
                });


        footerLayout.setMainContent(fabLayout.getView());
        footerLayout.setFooterContent(searchOptions());

        footerLayout.getView().setBackgroundColor(Color.parseColor("#eeeeee"));
        return footerLayout.getView();

    }

    private View searchOptions() {
        LinearLayout layout = MakeDummy.linearLayoutHorizontal(context);
        layout.addView(findDriverButton());
        layout.addView(MakeDummy.invisible(Atom.textView(context,"h")));
        layout.addView(findPassengers());

        layout.setPadding(Dp.one_point_5_em(), Dp.one_point_5_em(), Dp.one_point_5_em(), Dp.one_point_5_em());
        layout.setBackgroundColor(Color.parseColor("#444444"));
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        return layout;
    }

    private View findDriverButton() {
        Button btn = Atom.button(context, "Find A Ride", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.scheduleTrip(context,TripFormDisplayParameters.PASSENGER);
            }
        });
        btn.setTextSize(Dp.scaleBy(0.7f));
        return btn;
    }

    private View findPassengers() {
        Button btn = Atom.button(context, "Find passengers", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.scheduleTrip(context,TripFormDisplayParameters.DRIVER);
            }
        });
        btn.setTextSize(Dp.scaleBy(0.7f));
        return btn;
    }

    private View createTripList() {
        return MakeDummy.linearLayoutVertical(
                context,
                overview(),
                MakeDummy.lineSeparator(context,Dp.normal()),
                listTitle(),
                new TripListUpdater(context).getView()
        );

    }

    private View listTitle() {
        TextView title = Atom.textViewPrimaryNormal(context,"YOUR TRIPS");
        title.setPadding(Dp.one_point_5_em(),0,Dp.one_point_5_em(),Dp.normal());
        title.setAlpha(0.6f);
        return title;
    }

    private View overview() {
        LinearLayout overview = MakeDummy.linearLayoutVertical(
                context,
                valueProposition(),
                prompt()
        );
        overview.setBackgroundResource(R.drawable.header_background);
        overview.setPadding(Dp.one_point_5_em(),Dp.normal(),Dp.one_point_5_em(),Dp.normal());
        return overview;
    }

    private View valueProposition() {
        TextView textView = Atom.textView(context,"Travel with someone, share fuel costs");
        textView.setTextSize(Dp.scaleBy(0.8f));
        return textView;
    }

    private View prompt() {
        EditText editText = new EditText(context);
        editText.setHint("Where To?");
        editText.setTextSize(Dp.scaleBy(1.5f));
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setY(-Dp.scaleBy(0.5f));
        editText.setFocusable(false);



        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HideKeyboard.run(view);
                GotoActivity.scheduleTrip(context,TripFormDisplayParameters.DRIVER);
            }
        });

        TextView chooseDestination = Atom.textViewPrimaryNormal(context,"ENTER DESTINATION");
        chooseDestination.setBackgroundResource(R.drawable.button_accent);
        chooseDestination.setTextColor(Color.WHITE);
        chooseDestination.setClickable(true);
        chooseDestination.setTextSize(Dp.scaleBy(0.7f));
        chooseDestination.setPadding(Dp.one_point_5_em(),Dp.normal(),Dp.one_point_5_em(),Dp.normal());
        chooseDestination.setTypeface(null, Typeface.BOLD);
        chooseDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GotoActivity.scheduleTrip(context,TripFormDisplayParameters.DRIVER);
            }
        });

        return RelativeLayoutFactory.alignleftOfWidget(editText,chooseDestination);
    }

    @Override
    public View getView() {
        return view;

    }

}
