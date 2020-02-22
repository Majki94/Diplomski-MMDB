package com.majkic.mirko.mmdb.people;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.majkic.mirko.mmdb.R;
import com.majkic.mirko.mmdb.adapters.PeopleAdapter;
import com.majkic.mirko.mmdb.model.Person;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PeopleFragment extends Fragment implements PeopleContract.View {

    private static final String TAG = PeopleFragment.class.getSimpleName();
    private Unbinder unbinder;
    private PeopleContract.UserActionsListener mPresenter;
    @BindView(R.id.people_list)
    RecyclerView peopleList;
    @BindView(R.id.progress)
    ProgressBar progress;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_people, container, false);
        unbinder = ButterKnife.bind(this, root);

        mPresenter = new PeoplePresenter(this);

        peopleList.setLayoutManager(new LinearLayoutManager(getContext()));
        peopleList.setAdapter(new PeopleAdapter(getContext(), new ArrayList()));
        peopleList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (peopleList != null && peopleList.getLayoutManager() != null) {
                    LinearLayoutManager layoutManager = ((LinearLayoutManager) peopleList.getLayoutManager());
                    if (layoutManager.findLastVisibleItemPosition() == layoutManager.getItemCount() - 1 && progress.getVisibility() == View.GONE) {
                        mPresenter.getNextPeople();
                    }
                }
            }
        });

        return root;
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
        unbinder.unbind();
    }

    @Override
    public void showProgress() {
        if (progress != null) {
            progress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (progress != null) {
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPeople(List<Person> people) {
        if (peopleList != null && peopleList.getAdapter() != null) {
            ((PeopleAdapter) peopleList.getAdapter()).setPeople(people);
        }
    }

    @Override
    public void appendPeople(List<Person> people) {
        if (peopleList != null && peopleList.getAdapter() != null) {
            ((PeopleAdapter) peopleList.getAdapter()).appendPeople(people);
        }
    }

}
