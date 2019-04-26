package com.majkic.mirko.mmdb.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.majkic.mirko.mmdb.R;
import com.majkic.mirko.mmdb.adapters.MovieAdapter;
import com.majkic.mirko.mmdb.model.Movie;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment implements MainContract.View {

    public static final int COLUMN_COUNT = 3;
    private MainContract.UserActionsListener mPresenter;
    private RecyclerView movieListView;
    private ProgressBar progress;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mPresenter = new MainPresenter(this);

        movieListView = root.findViewById(R.id.movies_list);
        movieListView.setLayoutManager(new GridLayoutManager(getContext(), COLUMN_COUNT));
        movieListView.setAdapter(new MovieAdapter(getContext(), new ArrayList<Movie>()));

        progress = root.findViewById(R.id.progress);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getMovies();
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
    public void showMovies(List<Movie> movies) {
        if (movieListView.getAdapter() != null) {
            ((MovieAdapter) movieListView.getAdapter()).setMovieList(movies);
        }
    }
}
