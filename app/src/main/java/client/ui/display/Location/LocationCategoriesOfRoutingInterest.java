package client.ui.display.Location;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import client.data.Rest;
import client.ui.GotoActivity;
import client.ui.display.TabLayoutForApp;
import client.ui.libraries.HerbuyListView;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Location;
import layers.render.Atom;
import libraries.android.AdapterForFragmentPager;

public class LocationCategoriesOfRoutingInterest implements HerbuyView {
    private AppCompatActivity context;

    public LocationCategoriesOfRoutingInterest(AppCompatActivity context) {
        this.context = context;
    }

    @Override
    public View getView() {
        return new TabLayoutForApp(context){
            @Override
            protected void onAddTabs(AdapterForFragmentPager adapter) {
                adapter.addTab(
                        "All",
                        allLocations()
                );

                adapter.addTab(
                        "+ New Location",
                        addLocationForm()
                );

                adapter.addTab(
                        "Discover",
                        discover()
                );

                adapter.addTab(
                        "Connect",
                        connect()
                );

                adapter.addTab(
                        "Learn",
                        learn()
                );

                adapter.addTab(
                        "Share",
                        share()
                );

                adapter.addTab(
                        "Settings",
                        settings()
                );


            }
        }.getView();
    }

    private View addLocationForm() {
        return new FormForAddLocation(context){
            @Override
            protected void onLocationAdded(Location location) {
                GotoActivity.locationProfileGeneric(context, location);
            }
        }.getView();
    }

    private View settings() {
        return Atom.textView(context,"Adjust your location settings as desired, accept or reject travel requests from certain locations");
    }

    private View share() {
        return Atom.textView(context,"Share your thoughts on a location, leave a comment or review, rate");
    }

    private View connect() {
        return Atom.textView(context,"Connect with friends who have been there, are going to, or returning from");
    }

    private View learn() {
        return Atom.textView(context,"Most Popular, Stats");
    }

    private View discover() {
        return Atom.textView(context,"Trending, Popular with friends, Most Visited, You Visited, Friends Location, Where your friends are");
    }

    private View allLocations() {
        HerbuyListView<Location> listView = new HerbuyListView<Location>(context) {
            @Override
            protected View createItemView(final Location item) {
                LocationView locationView = new LocationView(context,item);
                return locationView.getView();

            }
        };
        Rest.api().getLocations().enqueue(listView);

        listView.getView().setBackgroundColor(Color.WHITE);
        return listView.getView();
    }
}
