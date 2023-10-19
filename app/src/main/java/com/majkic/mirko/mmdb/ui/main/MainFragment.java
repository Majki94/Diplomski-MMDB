package com.majkic.mirko.mmdb.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.majkic.mirko.mmdb.R;
import com.majkic.mirko.mmdb.databinding.FragmentMainBinding;
import com.majkic.mirko.mmdb.ui.adapters.MainPagerAdapter;

public class MainFragment extends Fragment implements MainContract.View {

    private static final String TAG = MainFragment.class.getSimpleName();

    private FragmentMainBinding binding;
    private MainPagerAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: called");
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e(TAG, "onCreateView: called");
        binding = FragmentMainBinding.inflate(inflater, container, false);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.popular));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.favourite));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.watched));
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
                if (binding.viewPager.getAdapter() != null && binding.viewPager.getAdapter() instanceof MainPagerAdapter) {
                    ((MainPagerAdapter) binding.viewPager.getAdapter()).refresh(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (adapter == null) {
            Log.e(TAG, "onCreateView: adapter null");
            adapter = new MainPagerAdapter(getChildFragmentManager());
        } else {
            Log.e(TAG, "onCreateView: adapter not null");
        }
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.viewPager.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: called");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
