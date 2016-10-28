package com.example.sfirstapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Moritz on 20.10.2016.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;
    FragmentManager fm;

    public HomePagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                CollectionFragment collectionTab = new CollectionFragment();
                return collectionTab;
            case 1:
                DbListFragment recentTab = new DbListFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(recentTab, "dbListFragment");
                //ft.commit();
                return recentTab;
            case 2:
                MyListFragment unsortedTab = new MyListFragment();
                return unsortedTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}

