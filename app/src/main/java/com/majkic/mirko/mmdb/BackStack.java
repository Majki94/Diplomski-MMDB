package com.majkic.mirko.mmdb;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 07.04.2019.
 */

public class BackStack {

    private static WeakReference<MainActivity> activityReference;
    public static List<Fragment> fragmentList = new ArrayList<>();

    public static void setActivityReference(MainActivity a) {
        activityReference = new WeakReference<>(a);
    }

    public static void presentFragment(Fragment fragment) {
        FragmentManager fragmentManager = activityReference.get().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
        fragmentList.add(fragment);
    }

    public static void onBackPressed() {
        if (fragmentList.size() > 1) {
            //will be implemented
        } else {
            activityReference.get().onBackPressed();
        }
    }

}
