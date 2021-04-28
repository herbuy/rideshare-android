package client.ui.display.Trip;
import android.graphics.Color;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.skyvolt.jabber.OnHomeMenuItemSelected;
import com.skyvolt.jabber.R;

import client.data.DummyData;
import client.data.LocalSession;
import client.ui.GotoActivity;
import client.ui.display.family.FamilyListUpdater3;
import client.ui.display.files.LoadImage;
import client.ui.display.notifications.TotalDirtyEntitiesUpdater;
import client.ui.display.users.ProfilePhotoChangedEvent;
import core.businessobjects.FileDetails;
import libraries.android.CustomTabLayout;
import libraries.android.MakeDummy;
import libraries.android.NetStatus;
import libraries.ObserverList;
import libraries.android.AdapterForFragmentPager;
import libraries.android.HerbuyViewPager;
import libraries.android.TabInterceptor;
import resources.Dp;
import resources.ItemColor;
import shared.BaseActivity;

public class TripListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(Atom.centeredText(this,"LOADING..."));
        refreshActivity();
        //HideActionBar.run(this);

        //ImageView imageView = LoadImage.fromFileShortName(this,LocalSession.instance().getUserProfilePic());
        //setContentView(imageView);


    }


    FrameLayout avatarHolder;

    private void refreshActivity() {
        setContentView(R.layout.activity_tabbed_2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        avatarHolder = findViewById(R.id.avatar_holder);
        setSupportActionBar(toolbar);

        doSetTitle();
        handleNetStatusChange();
        doSetUserAvatar();


        HerbuyViewPager viewPager = (HerbuyViewPager) findViewById(R.id.container);
        viewPager.setPagingEnabled(true);

        viewPager.setAdapter(
                new AdapterForFragmentPager(this.getSupportFragmentManager())
                        /*.addTab(
                                "HOME",
                                homeTab()
                        )*/
                        .addTab(
                                "TRIPS",
                                upcomingTrips()
                        )
                        .addTab(
                                "RIDES",
                                matched()
                        )
                        .addTab(
                                "COMPLETED",
                                completedTrips()
                        )


        );

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        TabInterceptor.intercept(tabLayout);

        final CustomTabLayout customTabLayout = new CustomTabLayout(tabLayout, Dp.scaleBy(0.7f), Color.WHITE, ItemColor.primary());
        new TotalDirtyEntitiesUpdater(TripListActivity.this, customTabLayout);

    }

    private void doSetUserAvatar() {

        final ImageView imageView = DummyData.randomCircleImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        avatarHolder.removeAllViews();
        avatarHolder.addView(imageView);
        LoadImage.fromFileShortName(imageView,LocalSession.instance().getUserProfilePic());

        ProfilePhotoChangedEvent.instance().add(new ObserverList.Observer<FileDetails>() {
            @Override
            public void notifyEvent(FileDetails eventArgs) {
                //if doesnt work, try using the url in 'file details'
                LoadImage.fromFileShortName(imageView,LocalSession.instance().getUserProfilePic());
            }
        });


        //new GetImageFromUrl(avatar);
        //LoadImage.fromFileShortName(avatar, LocalSession.instance().getUserProfilePic());


        final AppCompatActivity context = this;
        avatarHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.changeProfilePic(TripListActivity.this);

            }
        });

        //handle my profile photo changes


        /*
        ImageView avatar = DummyData.randomCircleImageView(this);
        avatarHolder.removeAllViews();
        avatarHolder.addView(avatar);

        LoadImage.fromFileShortName(avatar,LocalSession.instance().getUserProfilePic());

        avatarHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.aboutMe(TripListActivity.this);


            }
        });*/


    }

    private void handleNetStatusChange() {

        NetStatus.instance().addListener(new NetStatus.Listener() {
            @Override
            public void onConnected() {
                doSetTitle();
            }

            @Override
            public void onDisconnected() {
                doSetTitle();
            }
        });
    }

    private View homeTab() {
        LinearLayout wrapper = MakeDummy.linearLayoutVertical(
                this,
                new HomeTab(this).getView()
        );
        wrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        wrapper.setPadding(Dp.normal(), Dp.normal(), Dp.normal(), Dp.normal());
        wrapper.setBackgroundColor(Color.parseColor("#f7f7f7"));
        return wrapper;
        //return new HomeTab((Context)this).getView();
    }


    private View upcomingTrips() {
        RelativeLayout layout = new RelativeLayout(this);

        layout.addView(new UpComingTripsTab(this).getView());
        layout.setBackgroundColor(Color.parseColor("#f7f7f7"));
        //layout.setPadding(Dp.normal(),Dp.normal(),Dp.normal(),Dp.normal());
        return layout;
    }

    private View matched() {
        RelativeLayout layout = new RelativeLayout(this);
        layout.addView(new FamilyListUpdater3(this).getView());
        layout.setBackgroundColor(Color.parseColor("#eeeeee"));
        return layout;
    }

    private View completedTrips() {
        return new CompletedTripsTab(this).getView();
    }

    private void doSetTitle() {


        String currentUserName = String.format(
                "%s%s",
                LocalSession.instance().getDetails().getUserName(),
                NetStatus.instance().isConnected() ? "" : " (Offline)"
        );
        setTitle(currentUserName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        OnHomeMenuItemSelected.run(item, this);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();

    }

}
