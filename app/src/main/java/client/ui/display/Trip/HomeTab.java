package client.ui.display.Trip;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyvolt.jabber.R;

import client.ui.GotoActivity;
import client.ui.display.Trip.schedule_trip.trip_form_display_params.TripFormDisplayParameters;
import client.ui.libraries.HerbuyView;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;
import resources.ItemColor;

public class HomeTab implements HerbuyView {
    private final Context context;

    public HomeTab(Context context) {
        this.context = context;
    }

    public View getView() {
        View  menu = menu();
        menu.setBackgroundResource(R.drawable.header_background);

        View sections = sections();
        sections.setBackgroundResource(R.drawable.header_background);

        return MakeDummy.linearLayoutVertical(
                context,
                menu,
                MakeDummy.lineSeparator(context,Dp.normal()),
                sections

        );

    }

    private View sections() {
        LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                context,
                //MakeDummy.padding(Atom.textViewPrimaryBold(context, "Ranking"), Dp.normal()),
                new RideListForHomeDisplay(context).getView()
        );
        return wrapper;
    }

    private View menu() {
        LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                context,
                vspace(),
                vspace(),
                thePromise(),
                getStartedForm(),
                vspace(),
                findPassengers(),
                vspace(),
                vspace()
        );
        wrapper.setGravity(Gravity.CENTER_HORIZONTAL);
        return wrapper;
    }

    private View thePromise() {
        TextView textView = Atom.textViewPrimaryNormal(context, "Get affordable rides to your destination");
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextSize(Dp.scaleBy(0.9f));

        View thePromise = MakeDummy.wrapContent(textView);
        /*thePromise.setLayoutParams(new ViewGroup.LayoutParams(
                MinOfScreenWidthAndHeight.twoThirds(context),
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));*/
        return thePromise;
    }

    private View vspace() {
        return MakeDummy.lineSeparator(context,Dp.scaleBy(0.5f));
    }

    private View findPassengers() {
        Button btn = Atom.button(context, "Find Passengers", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.scheduleTrip(context, TripFormDisplayParameters.DRIVER);
            }
        });
        btn.setBackgroundColor(Color.TRANSPARENT);
        btn.setTextColor(ItemColor.primary());
        btn.setBackgroundResource(R.drawable.chat_edit_text);
        btn.setTextSize(Dp.scaleBy(0.7f));
        MakeDummy.wrapContent(btn);
        return btn;

    }


    private View getStartedForm() {
        TextView question = Atom.textViewPrimaryNormal(context,"Where are you going?");
        question.setTextSize(Dp.scaleBy(1.5f));
        question.setY(-Dp.scaleBy(0.5f));

        Button chooseAnswer = Atom.button(context, "Choose Destination", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.scheduleTrip(context,TripFormDisplayParameters.PASSENGER);
            }
        });
        chooseAnswer.setY(-Dp.scaleBy(0.75f));

        LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                context,
                question,
                MakeDummy.lineSeparator(context,Dp.scaleBy(0.5f)),
                chooseAnswer
        );
        MakeDummy.wrapContent(wrapper);
        return wrapper;
    }
}
