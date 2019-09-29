package com.example.naamkaran;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    Fragment fragment = null;

    public ViewPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {

        Log.d("mehul", position + " i am postiion");

        switch (position) {
            case 0:
                fragment = new TrendingFragment();
                break;
            //return tFragment;

            case 1:
                fragment = new HinduFragment();
                break;
            //return hFragment;

            case 2:
                fragment = new ChristianFragment();
                break;
            //return mFragment;
            case 3:
                fragment = new MuslimFragment();
                break;
            // return cFragment;

        }
        return fragment;
    }


    @Override
    public CharSequence getPageTitle(int position) {

        String[] tabTitles = new String[]{"Trending", "Hindu", "Christian", "Muslim"};
        return tabTitles[position];
    }


    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }

}