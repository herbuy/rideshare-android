package libraries.android;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AdapterForFragmentPager extends FragmentPagerAdapter {

    public static class Tab{
        private String title;
        private View content;

        public Tab(String title, View content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public View getContent() {
            return content;
        }

    }

    private List<Tab> tabs = new ArrayList<>();

    public AdapterForFragmentPager(FragmentManager fm) {
        super(fm);
    }

    public AdapterForFragmentPager addTab(String title, View content){
        tabs.add(new Tab(title,content));
        return this;
    }

    public static class AppFragment extends Fragment{
        View rootView;
        private OnCreateView onCreateView;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //setRetainInstance(true);
        }

        public AppFragment setOnCreateView(OnCreateView onCreateView) {
            this.onCreateView = onCreateView;
            return this;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //just returning the view helps us not lose data
            return onCreateView.run(inflater,container,savedInstanceState).get();
            /*if(rootView == null){
                WeakReference<View> ref = onCreateView.run(inflater,container,savedInstanceState);
                rootView = ref.get();
            }
            else{
                //ViewGroup parent = ((ViewGroup)(rootView.getParent()));
                //if(parent != null) parent.removeView(rootView);
            }
            return rootView;*/

        }

        private interface OnCreateView{
            WeakReference<View> run(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

        }

    }
    @Override
    public Fragment getItem(final int position) {

        return new AppFragment().setOnCreateView(new AppFragment.OnCreateView() {
            @Override
            public WeakReference<View> run(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                return new WeakReference<>(tabs.get(position).getContent());
            }

        });
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }
}

