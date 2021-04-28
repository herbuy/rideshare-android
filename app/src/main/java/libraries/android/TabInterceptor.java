package libraries.android;

import com.google.android.material.tabs.TabLayout;

public class TabInterceptor {
    private static int normalTabTextColor;
    private static int selectedTabTextColor;
    private static int selectedTabIndicatorWidth = 8;
    private static int selectedTabIndicatorColor;

    public static void setUp(int normalTabTextColor,int selectedTabTextColor, int selectedTabIndicatorWidth, int selectedTabIndicatorColor){
        TabInterceptor.normalTabTextColor = normalTabTextColor;
        TabInterceptor.selectedTabTextColor = selectedTabTextColor;
        TabInterceptor.selectedTabIndicatorWidth = selectedTabIndicatorWidth;
        TabInterceptor.selectedTabIndicatorColor = selectedTabIndicatorColor;

    }

    public static void intercept(TabLayout tabLayout) {
       tabLayout.setTabTextColors(normalTabTextColor, selectedTabTextColor);

        tabLayout.setSelectedTabIndicatorColor(selectedTabIndicatorColor);
        tabLayout.setSelectedTabIndicatorHeight(selectedTabIndicatorWidth);

    }
}