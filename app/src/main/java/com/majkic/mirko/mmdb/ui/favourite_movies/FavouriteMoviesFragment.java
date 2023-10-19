package com.majkic.mirko.mmdb.ui.favourite_movies;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.majkic.mirko.mmdb.BackStack;
import com.majkic.mirko.mmdb.Constants;
import com.majkic.mirko.mmdb.data.model.Movie;
import com.majkic.mirko.mmdb.databinding.FragmentFavouriteMoviesBinding;
import com.majkic.mirko.mmdb.ui.adapters.MovieAdapter;
import com.majkic.mirko.mmdb.ui.movie_details.MovieDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMoviesFragment extends Fragment implements FavouriteMoviesContract.View {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentFavouriteMoviesBinding binding;
    private FavouriteMoviesContract.UserActionsListener mPresenter;

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
        binding = FragmentFavouriteMoviesBinding.inflate(inflater, container, false);
        mPresenter = new FavouriteMoviesPresenter(getContext(), this);

        layoutManager = new GridLayoutManager(getContext(), Constants.DEFAULTS.COLUMN_COUNT);
        binding.moviesList.setLayoutManager(layoutManager);
        binding.moviesList.setAdapter(new MovieAdapter(getContext(), new ArrayList<Movie>(), new MovieAdapter.MovieClickListener() {
            @Override
            public void onMovieClicked(Movie m) {
                BackStack.presentFragment(MovieDetailsFragment.newInstance(m.getId()));
            }

            @Override
            public void onFavouriteClicked(Movie m) {
                mPresenter.favouriteChanged(m);
            }

            @Override
            public void onWatchedClicked(Movie m) {
                mPresenter.watchedChanged(m);
            }
        }));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
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
    public void setFavouriteMovies(List<Movie> movies) {
        if (binding != null && binding.moviesList.getAdapter() != null) {
            if (movies.size() > 0) {
                binding.noAdded.setVisibility(View.GONE);
                binding.moviesList.setVisibility(View.VISIBLE);
                ((MovieAdapter) binding.moviesList.getAdapter()).setMovieList(movies);
            } else {
                binding.moviesList.setVisibility(View.GONE);
                binding.noAdded.setVisibility(View.VISIBLE);
            }
        }
    }

    public void refresh() {
        mPresenter.getFavouriteMovies();
    }

}
