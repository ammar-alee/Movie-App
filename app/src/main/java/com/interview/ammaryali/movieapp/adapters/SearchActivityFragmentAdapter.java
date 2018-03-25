package com.interview.ammaryali.movieapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SearchActivityFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentArrayList;


    public SearchActivityFragmentAdapter(FragmentManager fm, ArrayList<Fragment> arrayList) {
        super(fm);
        fragmentArrayList = arrayList;

    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}
