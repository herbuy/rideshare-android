package client.ui.display.Trip.schedule_trip.p20210410;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Random;
import java.util.regex.Matcher;

import client.ui.display.Trip.ScheduleTripActivity;
import core.businessobjects.Trip;
import libraries.android.AdapterForFragmentPager;
import libraries.android.TabInterceptor;
import libraries.android.ViewPagerWithoutFragments;
import libraries.underscore.Singleton;
import resources.ItemColor;

public abstract class TripUploadForm {
    AppCompatActivity context;
    public TripUploadForm(AppCompatActivity context) {
        this.context = context;
    }

    public View getView() {

        ViewPager viewPager = new ViewPager(context);
        viewPager.setId(Math.abs(new Random().nextInt()));
        viewPager.setAdapter(adapter.instance());

        TabLayout tabLayout = new TabLayout(context);
        tabLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tabLayout.setBackgroundColor(ItemColor.primary());
        tabLayout.setupWithViewPager(viewPager);
        TabInterceptor.intercept(tabLayout);
        return tabLayout;


    }

    Singleton<AdapterForFragmentPager> adapter = new Singleton<AdapterForFragmentPager>() {
        @Override
        protected AdapterForFragmentPager onCreate() {
            return new AdapterForFragmentPager(context.getSupportFragmentManager())
                    .addTab("Find a Lift",new FindARideForm(context).getView())
                    .addTab("Give a lift",new FindPassengerForm(context).getView());
        }
    };

    public abstract void tripScheduled(Trip trip);
}
