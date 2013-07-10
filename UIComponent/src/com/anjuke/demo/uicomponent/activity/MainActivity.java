package com.anjuke.demo.uicomponent.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.androidquery.util.AQUtility;
import com.anjuke.demo.uicomponent.fragment.tab.ColorSelectFragment;
import com.anjuke.demo.uicomponent.fragment.tab.FontFragment;
import com.anjuke.demo.uicomponent.fragment.tab.PhotoFragment;
import com.anjuke.demo.uicomponent.fragment.tab.ProgressFragment;
import com.anjuke.demo.uicomponent.fragment.tab.RefreshListFragment;
import com.anjuke.library.uicomponent.slidingtab.PagerSlidingTabStrip;
import com.anjuke.uicomponent.R;

public class MainActivity extends SherlockFragmentActivity implements OnPageChangeListener {
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private TabPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.ui_orange_deep));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new TabPagerAdapter(getSupportFragmentManager());
        tabs.setIndicatorColorResource(R.color.ui_orange_deep);
        tabs.setTextColorResource(R.color.ui_text_select_grey_light);
        tabs.setSelectedTextColorResource(R.color.ui_text_select_grey_deep);
        tabs.setOnPageChangeListener(this);
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onClick(View v) {
        startActivity(new Intent(this, SelectBarActivity.class).putExtra("color",
                Color.parseColor(v.getTag().toString())));
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {

    }

    private class TabPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<Fragment>();
        private final String[] titles = new String[] { "字体", "颜色及筛选", "列表", "UI状态", "滑动" };

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
            case 0:
                fragment = new FontFragment();
                break;
            case 1:
                fragment = new ColorSelectFragment();
                break;
            case 2:
                fragment = new RefreshListFragment();
                break;
            case 3:
                fragment = new ProgressFragment();
                break;
            case 4:
                fragment = new PhotoFragment();
                break;
            default:
                break;
            }
            fragments.add(fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

    }
}
