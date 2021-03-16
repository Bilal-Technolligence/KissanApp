package com.example.kissanapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class SellingActivity extends BaseClass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_selling);

        TabLayout tabLayout=(TabLayout) findViewById(R.id.productTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("MyAd"));
        tabLayout.addTab(tabLayout.newTab().setText("Favorite"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#2D9331"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#2D9331"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPagers= (ViewPager) findViewById(R.id.productSummaryPager);
        SellingTabsAdapter sellingTabsAdapter =new SellingTabsAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPagers.setAdapter(sellingTabsAdapter);
        viewPagers.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagers.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_selling;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_sell;
    }
}