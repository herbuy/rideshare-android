package libraries.android;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class AdapterForFragmentPager2 extends FragmentPagerAdapter {


    private List<Fragment> tabs = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public AdapterForFragmentPager2(FragmentManager fm) {
        super(fm);
    }

    public AdapterForFragmentPager2 add(String title, Fragment content){
        tabs.add(content);
        titles.add(title);
        return this;
    }

    @Override
    public Fragment getItem(final int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}

