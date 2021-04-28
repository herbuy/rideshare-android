package com.skyvolt.jabber;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import client.data.Rest;
import client.ui.IntentExtras;
import client.ui.display.users.UserGridView;
import core.businessmessages.toServer.ParamsForGetUsers;
import core.businessobjects.Location;
import core.businessobjects.User;
import libraries.JsonEncoder;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.MessageBox;
import libraries.android.AdapterForFragmentPager;
import libraries.android.TabInterceptor;
import resources.Dp;
import retrofit2.Call;
import shared.BaseActivity;

public class FriendsWhoBeenToLocationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Location selectedLocation = getSelectedLocation();

        if (selectedLocation == null) {
            MessageBox.show("Location not specified", this);
            return;
        }


        setTitle(selectedLocation.getName());
        //setContentView(contentView(selectedLocation));
        contentView(selectedLocation);

    }

    private View contentView(final Location selectedLocation) {


        setContentView(R.layout.activity_tabbed);

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        viewPager.setAdapter(
                new AdapterForFragmentPager(getSupportFragmentManager())
                        .addTab(
                                "Going There",
                                goingThere(selectedLocation)
                        )
                        .addTab(
                                "Been There",
                                beenThere(selectedLocation)
                        )
                        .addTab(
                                "Currently There",
                                currentlyThere(selectedLocation)
                        )


        );

        tabLayout.setupWithViewPager(viewPager);
        TabInterceptor.intercept(tabLayout);

        viewPager.setPadding(Dp.normal(),Dp.normal(),Dp.normal(),Dp.normal());
        return viewPager;


        //====================

        /*return MakeDummy.linearLayoutVertical(
                this,
                possibilities(),
                currentlyThere(selectedLocation),
                beenThere(selectedLocation),
                goingThere(selectedLocation)
        );*/
    }

    private View possibilities() {
        TextView ideas = Atom.textViewPrimaryBold(this, "Consult, Chat, Link Up");
        ideas.setTextSize(Dp.scaleBy(1.3f));
        return ideas;
    }

    private View currentlyThere(Location selectedLocation) {
        return MakeDummy.linearLayoutVertical(
                this,
                Atom.textViewPrimaryBold(this, "Friends currently in " + selectedLocation.getName()),
                friendsBeenToLocation(selectedLocation)
        );

    }

    private View beenThere(Location selectedLocation) {
        return MakeDummy.linearLayoutVertical(
                this,
                Atom.textViewPrimaryBold(this, "Friends who have been to " + selectedLocation.getName()),
                friendsBeenToLocation(selectedLocation)
        );
    }

    private View goingThere(Location selectedLocation) {
        return MakeDummy.linearLayoutVertical(
                this,
                Atom.textViewPrimaryBold(this, "Friends going to " + selectedLocation.getName()),
                friendsBeenToLocation(selectedLocation)
        );
    }

    private View friendsBeenToLocation(final Location selectedLocation) {
        return new UserGridView(this) {
            @Override
            public Call<List<User>> getApiCall() {
                Location location = selectedLocation;
                return Rest.api().getUsers(new ParamsForGetUsers());
            }

        }.getView();
    }


    private Location getSelectedLocation() {
        try {
            String strLocation = getIntent().getStringExtra(IntentExtras.location);
            return JsonEncoder.decode(strLocation, Location.class);
        } catch (Exception ex) {
            return null;
        }
    }
}
