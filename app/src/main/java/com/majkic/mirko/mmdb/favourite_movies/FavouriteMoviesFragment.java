package com.majkic.mirko.mmdb.favourite_movies;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.majkic.mirko.mmdb.BackStack;
import com.majkic.mirko.mmdb.Constants;
import com.majkic.mirko.mmdb.R;
import com.majkic.mirko.mmdb.adapters.MovieAdapter;
import com.majkic.mirko.mmdb.model.Movie;
import com.majkic.mirko.mmdb.movie_details.MovieDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavouriteMoviesFragment extends Fragment implements FavouriteMoviesContract.View {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Unbinder unbinder;
    private FavouriteMoviesContract.UserActionsListener mPresenter;

    @BindView(R.id.movies_list)
    RecyclerView movieListView;
    @BindView(R.id.progress)
    ProgressBar progress;
    private GridLayoutManager layoutManager;

    public FavouriteMoviesFragment() {
        // Required empty public constructor
    }

    public static FavouriteMoviesFragment newInstance() {
        FavouriteMoviesFragment fragment = new FavouriteMoviesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_favourite_movies, container, false);
        unbinder = ButterKnife.bind(this, root);
        mPresenter = new FavouriteMoviesPresenter(getContext(), this);

        layoutManager = new GridLayoutManager(getContext(), Constants.DEFAULTS.COLUMN_COUNT);
        movieListView.setLayoutManager(layoutManager);
        movieListView.setAdapter(new MovieAdapter(getContext(), new ArrayList<Movie>(), new MovieAdapter.MovieClickListener() {
            @Override
            public void onMovieClicked(Movie m) {
                BackStack.presentFragment(MovieDetailsFragment.newInstance(m.getId()));
            }

            @Override
            public void onFavouriteClicked(Movie m) {

            }

            @Override
            public void onWatchedClicked(Movie m) {

            }
        }));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getFavouriteMovies();
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
    public void setFavouriteMovies(List<Movie> movies) {
        if (movieListView != null && movieListView.getAdapter() != null) {
            ((MovieAdapter) movieListView.getAdapter()).setMovieList(movies);
        }
    }
}
