package com.majkic.mirko.mmdb.ui.people;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majkic.mirko.mmdb.data.model.Person;
import com.majkic.mirko.mmdb.databinding.FragmentPeopleBinding;
import com.majkic.mirko.mmdb.ui.adapters.PeopleAdapter;

import java.util.ArrayList;
import java.util.List;

public class PeopleFragment extends Fragment implements PeopleContract.View {

    private static final String TAG = PeopleFragment.class.getSimpleName();
    private FragmentPeopleBinding binding;
    private PeopleContract.UserActionsListener mPresenter;

    public PeopleFragment() {
        // Required empty public constructor
    }

    public static PeopleFragment newInstance() {
        PeopleFragment fragment = new PeopleFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPeopleBinding.inflate(inflater, container, false);

        mPresenter = new PeoplePresenter(this);

        binding.peopleList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.peopleList.setAdapter(new PeopleAdapter(getContext(), new ArrayList()));
        binding.peopleList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (binding.peopleList.getLayoutManager() != null) {
                    LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.peopleList.getLayoutManager());
                    if (layoutManager.findLastVisibleItemPosition() == layoutManager.getItemCount() - 1 && binding.progress.getVisibility() == View.GONE) {
                        mPresenter.getNextPeople();
                    }
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getPeople();
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

    @Override
    public void showProgress() {
        if (binding != null) {
            binding.progress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (binding != null) {
            binding.progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPeople(List<Person> people) {
        if (binding != null && binding.peopleList.getAdapter() != null) {
            ((PeopleAdapter) binding.peopleList.getAdapter()).setPeople(people);
        }
    }

    @Override
    public void appendPeople(List<Person> people) {
        if (binding != null && binding.peopleList.getAdapter() != null) {
            ((PeopleAdapter) binding.peopleList.getAdapter()).appendPeople(people);
        }
    }

}
