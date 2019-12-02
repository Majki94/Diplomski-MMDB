package com.majkic.mirko.mmdb.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.majkic.mirko.mmdb.popular_movies.PopularMoviesFragment;

/**
 * Created by hp on 01.12.2019.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private static final int TABS_COUNT = 3;
    private static final String TAG = MainPagerAdapter.class.getSimpleName();
    private PopularMoviesFragment popular;
    private PopularMoviesFragment favourite;
    private PopularMoviesFragment watched;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        popular = PopularMoviesFragment.newInstance();
        favourite = PopularMoviesFragment.newInstance();
        watched = PopularMoviesFragment.newInstance();
    }

    @Override
    public Fragment getItem(int i) {
        Log.e(TAG, "getItem: called, position : " + i);
        switch (i) {
            case 0:
                return popular;
            case 1:
                return favourite;
            case 2:
                return watched;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TABS_COUNT;
    }

    public PopularMoviesFragment getPopular() {
        return popular;
    }

    public PopularMoviesFragment getFavourite() {
        return favourite;
    }

    public PopularMoviesFragment getWatched() {
        return watched;
    }
}
