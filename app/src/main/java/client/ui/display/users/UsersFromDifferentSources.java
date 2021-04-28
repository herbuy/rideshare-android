package client.ui.display.users;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.skyvolt.jabber.R;

import client.ui.GotoActivity;
import client.ui.display.login_details.view_list.LoginDetailsListController;
import client.ui.display.sessions.LoginController;
import client.ui.libraries.HerbuyView;
import core.businessobjects.User;
import libraries.android.MakeDummy;
import libraries.android.AdapterForFragmentPager;
import libraries.android.TabInterceptor;
import resources.Dp;

public class UsersFromDifferentSources implements HerbuyView {
    private AppCompatActivity context;

    public UsersFromDifferentSources(AppCompatActivity context) {
        this.context = context;
    }

    @Override
    public View getView() {
        View root = LayoutInflater.from(context).inflate(R.layout.activity_tabbed_2,null);


        ViewPager viewPager = (ViewPager) root.findViewById(R.id.container);
        viewPager.setAdapter(
                new AdapterForFragmentPager(context.getSupportFragmentManager())

                        .addTab(
                                "+ New Account",
                                newUser()
                        )
                        .addTab(
                                "Login Details",
                                viewLoginDetails()
                        )
                        .addTab(
                                "Login",
                                login()
                        )
        );


        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        TabInterceptor.intercept(tabLayout);

        return root;
    }

    private View viewLoginDetails() {
        return new LoginDetailsListController(context).getView();

    }

    private View login() {
        return new LoginController(context).getView();
    }

    TabLayout tabLayout;

    private View newUser() {
        View form = new FormForCreateUser(context){
            @Override
            protected void onAccountCreated(User user) {

                context.finish();
                GotoActivity.login(context);


            }
        }.getView();

        MakeDummy.padding(context,form,Dp.two_em());
        return form;
    }
}
