package com.skyvolt.jabber;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import client.data.Rest;
import client.ui.GotoActivity;
import client.ui.display.Trip.TripGridView;
import core.businessmessages.toServer.ParamsForGetTrips;
import core.businessobjects.Trip;
import layers.render.Atom;
import libraries.android.MakeDummy;
import resources.Dp;
import retrofit2.Call;
import shared.BaseActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(contentView());
    }

    private View contentView() {

        //return peoples trips, with option for my trip
        TripGridView herbuyGridView = new TripGridView(this) {
            @Override
            protected Call<List<Trip>> getApiCall() {
                return Rest.api().getTrips(new ParamsForGetTrips());
            }
        };

        LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                this,

                Atom.button(this, "My Trips", onClickMyTrips()),
                MakeDummy.lineSeparator(this,Dp.normal()),
                herbuyGridView.getView()
        );

        wrapper.setPadding(Dp.scaleBy(1.5f),Dp.scaleBy(1f),Dp.scaleBy(1.5f),Dp.scaleBy(1f));
        wrapper.setBackgroundColor(Color.WHITE);
        return wrapper;

    }

    private View.OnClickListener onClickMyTrips() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.myTrips(HomeActivity.this);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        OnHomeMenuItemSelected.run(item,this);
        return super.onOptionsItemSelected(item);
    }
}
