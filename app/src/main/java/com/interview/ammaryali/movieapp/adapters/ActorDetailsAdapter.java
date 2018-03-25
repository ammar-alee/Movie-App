package com.interview.ammaryali.movieapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ActorDetailsAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> actorDetailsArrayList = new ArrayList<>();

    public ActorDetailsAdapter(FragmentManager fm, ArrayList<Fragment> arrayList) {
        super(fm);
        actorDetailsArrayList = arrayList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment=actorDetailsArrayList.get(0);
                return fragment;
            case 1:
                fragment=actorDetailsArrayList.get(1);
                return fragment;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return actorDetailsArrayList.size();
    }
}
