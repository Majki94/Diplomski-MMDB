package com.majkic.mirko.mmdb;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.majkic.mirko.mmdb.ui.mvp.main.MainFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 07.04.2019.
 */

public class BackStack {

    private static final String TAG = BackStack.class.getSimpleName();
    private static WeakReference<MainActivity> activityReference;
    public static List<Fragment> fragmentList = new ArrayList<>();

    public static void setActivityReference(MainActivity a) {
        activityReference = new WeakReference<>(a);
    }

    public static void presentFragment(Fragment fragment) {
        presentFragment(fragment, true);
    }

    public static void presentFragment(Fragment fragment, boolean add) {
        FragmentManager fragmentManager = activityReference.get().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in, R.anim.anim_slide_out);
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null); //jako bitno da ne pukne po povratku sa DetailsFragment na Main
        fragmentTransaction.commit();
        if (add) {
            fragmentList.add(fragment);
        }
    }

    public static void onBackPressed() {
        if (fragmentList == null) {
            return;
        }
        if (fragmentList.size() <= 1 || fragmentList.get(fragmentList.size() - 1) instanceof MainFragment) {
            fragmentList.clear();
            activityReference.get().finish();
            return;
        } else {
            fragmentList.remove(fragmentList.size() - 1);
            presentFragment(fragmentList.get(fragmentList.size() - 1), false);
        }
        if (fragmentList.get(fragmentList.size() - 1) instanceof MainFragment) {
            MainActivity activity = activityReference.get();
            if (activity != null) {
                activity.setSelection(MainActivity.MOVIES_SELECTED);
            }
        }
    }

    public static void clearToHome() {
        if (fragmentList != null && fragmentList.size() > 1) {
            Fragment home = fragmentList.get(0);
            fragmentList.clear();
            fragmentList.add(home);
        }
    }

    public static void clearToHomeAndPresent() {
        if (fragmentList != null && fragmentList.size() > 1) {
            Fragment home = fragmentList.get(0);
            fragmentList.clear();
            fragmentList.add(home);
            presentFragment(fragmentList.get(0));
        }
    }

}
