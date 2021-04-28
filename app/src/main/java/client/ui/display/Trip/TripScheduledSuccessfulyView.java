package client.ui.display.Trip;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import client.ui.Molecule;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Trip;
import client.data.DummyData;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;
import resources.ItemColor;

public abstract class TripScheduledSuccessfulyView implements HerbuyView {
    Context context;
    Trip trip;

    String dummyDestinationName = DummyData.randomLocationName();
    String dummyOrigin = DummyData.randomLocationName();
    String dummyDate = DummyData.randomDate();

    public TripScheduledSuccessfulyView(Context context, Trip trip) {
        this.context = context;
        this.trip = trip;
    }

    @Override
    public View getView() {
        LinearLayout linearLayout = MakeDummy.linearLayoutVertical(
                context,
                feedback(),
                MakeDummy.lineSeparator(context, Dp.two_em()),

                whatDoYouWantToDoNext(),
                MakeDummy.lineSeparator(context, Dp.two_em()),

                scheduleAnotherTrip(),
                MakeDummy.lineSeparator(context, Dp.two_em()),

                seeOtherPeopleGoingToDestination(),
                MakeDummy.lineSeparator(context, Dp.two_em()),

                readAboutDestination(),
                MakeDummy.lineSeparator(context, Dp.normal()),
                MakeDummy.lineSeparator(context,1,Color.parseColor("#eeeeee")),
                MakeDummy.lineSeparator(context, Dp.normal()),

                seeFriendsInDestination(),
                MakeDummy.lineSeparator(context, Dp.two_em()),

                seeFriendsWhoHaveBeenInDestination(),
                MakeDummy.lineSeparator(context, Dp.two_em()),

                notifyFriendsOfYourTrip()
                );
        return linearLayout;
    }


    private View notifyFriendsOfYourTrip() {
        View item = molecule(
                "Notify friends of your journey",
                "They may want to wish you a safe journey or send you for something on your return",
                true,
                MakeDummy.imageView(context, Dp.scaleBy(3f), Dp.scaleBy(3f), R.drawable.ic_menu_bell_black, 0.5f)

        );
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNotifyFriendsOfTrip(new Trip());
            }
        });
        return item;
    }

    protected abstract void onNotifyFriendsOfTrip(Trip trip);

    private View seeFriendsInDestination() {
        View item = molecule(
                "See friends in " + dummyDestinationName,
                "You may want to link up with them when you getOrientation there, or ask them something about the destination",
                true,
                MakeDummy.imageView(context, Dp.scaleBy(3f), Dp.scaleBy(3f), R.drawable.ic_menu_location_black,0.5f)
        );
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSeeFriendsInDestination(new Trip());
            }
        });
        return item;

    }

    protected abstract void onSeeFriendsInDestination(Trip trip);

    private View seeFriendsWhoHaveBeenInDestination() {

        View item = molecule(
                "See friends who have been to " + dummyDestinationName,
                "You may want to ask them something about the destination",
                true,
                MakeDummy.imageView(context, Dp.scaleBy(3f), Dp.scaleBy(3f), R.drawable.ic_menu_returning_black,0.5f)

        );
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSeeFriendsBeenToDestination(new Trip());
            }
        });
        return item;

    }

    protected abstract void onSeeFriendsBeenToDestination(Trip trip);

    private View seeOtherPeopleGoingToDestination() {

        View item = molecule(
                "See other people going to " + dummyDestinationName,
                "you may want to travel with them",
                MakeDummy.imageView(context, Dp.scaleBy(3f), Dp.scaleBy(3f), R.drawable.ic_menu_traveller_white)
        );
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSeeOtherPeopleGoingToDestination(new Trip());
            }
        });
        return item;

    }

    protected abstract void onSeeOtherPeopleGoingToDestination(Trip trip);

    private View scheduleAnotherTrip() {

        View item = molecule(
                "Schedule Another Trip",
                "Helpful if you have multiple trips to schedule",
                MakeDummy.imageView(context, Dp.scaleBy(3f), Dp.scaleBy(3f), R.drawable.ic_menu_add_white)
        );
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onScheduleAnotherTrip();
            }
        });
        return item;

    }

    protected abstract void onScheduleAnotherTrip();


    private View readAboutDestination() {
        //link for reading about the destination
        View item = molecule(
                "Read about " + dummyDestinationName,
                "Get to know a bit about the place you are going to, such as distance, culture",
                true,
                MakeDummy.imageView(context, Dp.scaleBy(3f), Dp.scaleBy(3f), R.drawable.ic_menu_reading_black, 0.5f)

        );
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReadAboutDestination(new Trip());
            }
        });
        return item;

    }

    protected abstract void onReadAboutDestination(Trip trip);


    private View whatDoYouWantToDoNext() {
        return Atom.textViewPrimaryBold(context, "What do you want to do next?");
    }


    private View feedback() {
        TextView feedbackText = Atom.textView(
                context,
                String.format(
                        "Your trip from %s to %s on %s has been scheduled Successfully",
                        dummyOrigin,
                        dummyDestinationName,
                        dummyDate
                )
                );


        LinearLayout feedback = MakeDummy.linearLayoutHorizontal(
                context,
                MakeDummy.imageView(context,Dp.scaleBy(3f),Dp.scaleBy(3),R.drawable.ic_menu_tick),
                MakeDummy.verticalSeparator(context,Dp.normal()),
                feedbackText

        );
        feedback.setGravity(Gravity.CENTER_VERTICAL);
        feedback.setBackgroundColor(ItemColor.success());
        feedback.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return feedback;
    }

    private View molecule(String primaryText, String secondaryText, View child){
        return molecule(primaryText,secondaryText,false,child);
    }

    private View molecule(String primaryText, String secondaryText, boolean transparent, View iconChild) {
        return Molecule.actionPanelItem(context,primaryText,secondaryText,transparent,iconChild);
    }
}
