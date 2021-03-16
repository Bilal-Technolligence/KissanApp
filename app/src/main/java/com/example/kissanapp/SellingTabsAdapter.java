package com.example.kissanapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SellingTabsAdapter extends FragmentStatePagerAdapter {
    int countTab;
    public SellingTabsAdapter(@NonNull FragmentManager fm, int countTab) {
        super(fm);
        this.countTab=countTab;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                MyAdFragment myAdFragment=new MyAdFragment();
                return myAdFragment;
            case 1:
                FavoriteFragment favoriteFragment=new FavoriteFragment();
                return favoriteFragment;

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return countTab;
    }
}
