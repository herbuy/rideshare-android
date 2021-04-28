package client.ui.display;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import com.skyvolt.jabber.R;
import client.ui.libraries.HerbuyView;
import libraries.android.AdapterForFragmentPager;
import libraries.android.TabInterceptor;


public abstract class TabLayoutForApp implements HerbuyView {
    private AppCompatActivity context;

    public TabLayoutForApp(AppCompatActivity context) {
        this.context = context;
    }

    @Override
    public View getView() {
        View root = LayoutInflater.from(context).inflate(R.layout.activity_tabbed,null);


        ViewPager viewPager = (ViewPager) root.findViewById(R.id.container);
        AdapterForFragmentPager adapter = new AdapterForFragmentPager(context.getSupportFragmentManager());
        onAddTabs(adapter);
        viewPager.setAdapter(adapter);


        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        TabInterceptor.intercept(tabLayout);

        return root;
    }

    protected abstract void onAddTabs(AdapterForFragmentPager adapter);

    TabLayout tabLayout;


}

