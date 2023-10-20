package com.majkic.mirko.mmdb.ui.mvvm.people;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majkic.mirko.mmdb.data.model.Person;
import com.majkic.mirko.mmdb.databinding.FragmentPeopleBinding;
import com.majkic.mirko.mmdb.ui.adapters.PeopleAdapter;

import java.util.ArrayList;
import java.util.List;

public class PeopleFragment extends Fragment {

    private static final String TAG = PeopleFragment.class.getSimpleName();
    private FragmentPeopleBinding binding;
    private PeopleViewModel viewModel;

    public PeopleFragment() {
        // Required empty public constructor
    }

    public static PeopleFragment newInstance() {
        PeopleFragment fragment = new PeopleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPeopleBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(PeopleViewModel.class);

        binding.peopleList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.peopleList.setAdapter(new PeopleAdapter(getContext(), new ArrayList()));
        binding.peopleList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (binding.peopleList.getLayoutManager() != null) {
                    LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.peopleList.getLayoutManager());
                    if (layoutManager.findLastVisibleItemPosition() == layoutManager.getItemCount() - 1 && binding.progress.getVisibility() == View.GONE) {
                        viewModel.getNextPeople();
                    }
                }
            }
        });

        viewModel.showProgress.observe(getViewLifecycleOwner(), shouldShowProgress -> {
            if (shouldShowProgress) {
                showProgress();
            } else {
                hideProgress();
            }
        });

        viewModel.people.observe(getViewLifecycleOwner(), people ->{
            hideProgress();
            showPeople(people);
        });

        viewModel.getPeople();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void showProgress() {
        if (binding != null) {
            binding.progress.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
        if (binding != null) {
            binding.progress.setVisibility(View.GONE);
        }
    }

    public void showPeople(List<Person> people) {
        if (binding != null && binding.peopleList.getAdapter() != null) {
            ((PeopleAdapter) binding.peopleList.getAdapter()).setPeople(people);
        }
    }

}
