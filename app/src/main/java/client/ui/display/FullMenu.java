package client.ui.display;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import client.ui.GotoActivity;
import client.ui.libraries.HerbuyView;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;

public class FullMenu implements HerbuyView {
    Context context;

    @Override
    public View getView() {
        LinearLayout layout = MakeDummy.linearLayoutVertical(
                context,
                mostRecent(),
                requests(),
                notifications(),
                reactions(),
                statusUpdates(),
                reviews(),
                photos(),
                comments(),
                friends(),
                locations()
        );

        //layout.setPadding(Dp.two_em(),Dp.two_em(),Dp.two_em(),Dp.two_em());
        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(layout);
        return scrollView;
    }

    private View mostRecent() {
        TextView textView = Atom.textViewPrimaryNormal(context, "Most Recent");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textView.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return textView;
    }

    private View requests() {
        TextView textView = Atom.textViewPrimaryNormal(context,"Requests");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textView.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return textView;
    }

    private View notifications() {
        TextView textView = Atom.textViewPrimaryNormal(context,"Notifications");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textView.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return textView;
    }

    private View reactions() {
        TextView textView = Atom.textViewPrimaryNormal(context,"Reactions");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textView.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return textView;
    }

    private View statusUpdates() {
        TextView textView = Atom.textViewPrimaryNormal(context,"Status Updates");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textView.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return textView;
    }

    private View reviews() {
        TextView textView = Atom.textViewPrimaryNormal(context,"Reviews");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textView.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return textView;
    }

    private View photos() {
        TextView textView = Atom.textViewPrimaryNormal(context,"Photos");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textView.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return textView;
    }

    private View comments() {
        TextView textView = Atom.textViewPrimaryNormal(context,"Comments");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textView.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return textView;
    }

    private View friends() {
        TextView textView = Atom.textViewPrimaryNormal(context,"Friends");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.users(context);
            }
        });
        textView.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return textView;
    }

    private View locations() {
        TextView textView = Atom.textViewPrimaryNormal(context,"Locations");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GotoActivity.locationList(context);
            }
        });
        textView.setPadding(Dp.two_em(),Dp.normal(),Dp.two_em(),Dp.normal());
        return textView;
    }


}
