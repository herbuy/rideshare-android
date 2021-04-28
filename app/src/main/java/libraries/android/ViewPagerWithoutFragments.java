package libraries.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ViewPagerWithoutFragments extends ViewPager {
    ViewPagerAdapter adapter = new ViewPagerAdapter();
    public ViewPagerWithoutFragments(@NonNull Context context) {
        super(context);
        setAdapter(adapter);
    }

    public ViewPagerWithoutFragments(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setId(Math.abs(new Random().nextInt()));
        setAdapter(adapter);
    }

    public void add(View view){
        setId(Math.abs(new Random().nextInt()));
        this.adapter.add(view);
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private static class ViewPagerAdapter extends PagerAdapter {

        private List<View> mListData = new ArrayList<>();

        public void add(View view){
            this.mListData.add(view);
            notifyDataSetChanged();

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View child = mListData.get(position);
            container.addView(child);
            return child;
        }
    }

}
