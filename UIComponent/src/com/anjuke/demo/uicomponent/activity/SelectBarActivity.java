/**
 *
 * Copyright 2013 Anjuke. All rights reserved.
 * SelectBarActivity.java
 *
 */
package com.anjuke.demo.uicomponent.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.anjuke.library.uicomponent.select.SelectBar;
import com.anjuke.library.uicomponent.select.listener.OnGroupItemClickListener;
import com.anjuke.library.uicomponent.select.listener.OnItemClickListener;
import com.anjuke.uicomponent.R;

/**
 * @author qitongzhang (qitongzhang@anjuke.com)
 * @date 2013-5-28
 */
public class SelectBarActivity extends SherlockFragmentActivity implements OnItemClickListener,
        OnGroupItemClickListener {
    SelectBar selectBar;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectbar);
        int color=getIntent().getIntExtra("color", R.color.ui_blue_deep);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        selectBar = (SelectBar) findViewById(R.id.select_bar);
        selectBar.setIndicatorColor(color);
        selectBar.addTabDoubleList("区域1", getGroups(), getItems(), this);
        selectBar.addTabDoubleList("区域2", getGroups(), getItems(), this);
        selectBar.addTabSingleList("面积1", Arrays.asList(GENRES), this);
        selectBar.addTabSingleList("面积2", Arrays.asList(GENRES), this);
        selectBar.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            onBackPressed();
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
    private static final String[] GENRES = new String[] { "Action", "Adventure", "Animation", "Children", "Comedy",
            "Documentary", "Drama", "Foreign", "History", "Independent", "Romance", "Sci-Fi", "Television", "Thriller" };
    Random random = new Random();

    private String random() {
        return random.nextInt(100) + "";
    }

    private List<String> getGroups() {
        List<String> groups = new ArrayList<String>();
        for (int i = 0; i < 15; i++) {
            groups.add(i + "");
        }
        return groups;
    }

    private List<List<String>> getItems() {
        List<List<String>> groups = new ArrayList<List<String>>();
        for (int i = 0; i < 15; i++) {
            List<String> items = new ArrayList<String>();
            items.add(random());
            items.add(random());
            items.add(random());
            items.add(random());
            items.add(random());
            items.add(random());
            items.add(random());
            items.add(random());
            items.add(random());
            items.add(random());
            items.add(random());
            items.add(random());
            items.add(random());
            items.add(random());
            items.add(random());
            groups.add(items);
        }
        return groups;
    }

    @Override
    public void OnItemClick(ListView lv, String item, int position) {
        Log.d("zqt", "OnItemClick:" + position);
        Toast.makeText(this, "点击了：" + item, Toast.LENGTH_SHORT).show();
        selectBar.hidePopup();
    }

    @Override
    public void onBackPressed() {
        if (selectBar.isShowingPopup()) {
            selectBar.hidePopup();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void OnGroupItemClick(ListView lv, String item, int position) {
        Log.d("zqt", "OnGroupItemClick:" + position);
        Toast.makeText(this, "点击了：" + item, Toast.LENGTH_SHORT).show();
    }
}
