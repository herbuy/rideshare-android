package client.ui.display.users;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.skyvolt.jabber.R;

import client.ui.GotoActivity;
import client.ui.libraries.HerbuyView;
import core.businessobjects.User;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.AdapterForFragmentPager;
import libraries.android.MultiStateNetworkListView;
import libraries.android.TabInterceptor;

public class UserTTProfile implements HerbuyView {
    private AppCompatActivity context;
    private User user;

    public UserTTProfile(AppCompatActivity context, User user) {
        this.context = context;
        this.user = user;
    }

    @Override
    public View getView() {
        View root = LayoutInflater.from(context).inflate(R.layout.activity_tabbed,null);


        ViewPager viewPager = (ViewPager) root.findViewById(R.id.container);
        viewPager.setAdapter(
                new AdapterForFragmentPager(context.getSupportFragmentManager())
                        .addTab(
                                "Basic Info",
                                basicInfo()
                        )
                        .addTab(
                                "Coming Trips",
                                comingTrips()
                        )
                        .addTab(
                                "Update Info",
                                updateInfo(user)
                        )
        );


        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        TabInterceptor.intercept(tabLayout);

        return root;
    }
    TabLayout tabLayout;

    MultiStateNetworkListView allUsersGrid;

    private View basicInfo() {
      return Atom.textView(context, "Basic Info");
    }
    private View comingTrips() {
        return MakeDummy.textView(context,"Coming trips");
    }
    private View updateInfo(User user) {
        return new FormForUpdateUser(context,user){
            @Override
            protected void onAccountUpdated(User user) {
                allUsersGrid.refresh();
                tabLayout.getTabAt(0).select();
                GotoActivity.userProfile(context,user);
            }
        }.getView();
    }
}
