package com.majkic.mirko.mmdb.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.majkic.mirko.mmdb.R;
import com.majkic.mirko.mmdb.model.Movie;
import com.majkic.mirko.mmdb.model.Search;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchFragment extends Fragment implements SearchContract.View {

    private SearchContract.UserActionsListener mPresenter;
    private Unbinder unbinder;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, root);

        mPresenter = new SearchPresenter(this);

        return root;
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
    public void showRecentSearches(List<Search> searchList) {

    }

    @Override
    public void showSearchResults(List<Movie> searchResults) {

    }
}
