package com.majkic.mirko.mmdb.ui.movie_details;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.majkic.mirko.mmdb.BackStack;
import com.majkic.mirko.mmdb.Constants;
import com.majkic.mirko.mmdb.R;
import com.majkic.mirko.mmdb.Utilities;
import com.majkic.mirko.mmdb.data.model.Movie;
import com.majkic.mirko.mmdb.databinding.FragmentMovieDetailsBinding;

public class MovieDetailsFragment extends Fragment implements MovieDetailsContract.View {

    private static final String ARG_MOVIE_ID = "movie_id";
    private static final String ARG_LOAD_SEARCH = "load_search";

    private int movieId;
    private boolean loadSearch;
    private FragmentMovieDetailsBinding binding;
    private MovieDetailsContract.UserActionsListener mPresenter;
    private Movie movie;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public static MovieDetailsFragment newInstance(int movieId) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        args.putBoolean(ARG_LOAD_SEARCH, false);
        fragment.setArguments(args);
        return fragment;
    }

    public static MovieDetailsFragment newInstance(int movieId, boolean loadSearch) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        args.putBoolean(ARG_LOAD_SEARCH, loadSearch);
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
        if (getArguments() != null) {
            movieId = getArguments().getInt(ARG_MOVIE_ID);
            loadSearch = getArguments().getBoolean(ARG_LOAD_SEARCH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false);

        mPresenter = new MovieDetailsPresenter(getContext(), this);

        binding.back.setOnClickListener(view -> backClicked());
        binding.favourite.setOnClickListener(view -> favouriteClicked());
        binding.watched.setOnClickListener(view -> watchedClicked());

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getMovieForID(movieId, loadSearch);
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
    public void showMovie(Movie movie) {
        if (binding == null) {
            return;
        }
        this.movie = movie;
        Glide.with(this).load(Constants.BACKEND.TMDB_POSTER_BASE_URL + movie.getPosterPath()).into(binding.poster);
        if (getContext() != null) {
            Utilities.setFavoriteOrWatched(getContext(), binding.favourite, movie.isFavorite());
        }
        if (getContext() != null) {
            Utilities.setFavoriteOrWatched(getContext(), binding.watched, movie.isWatched());
        }
        binding.title.setText(movie.getTitle());

        binding.releaseDate.setText(getString(R.string.release_date));
        binding.releaseDate.append(" : ");
        binding.releaseDate.append(movie.getReleaseDate());

        binding.overview.setText(movie.getOverview());
    }

    @Override
    public void showUnableToRetrieveInfo() {
        if (getContext() != null) {
            Toast.makeText(getContext(), "Unable to retrieve movie info", Toast.LENGTH_SHORT).show();
        }
    }

    public void favouriteClicked() {
        if (binding != null && movie != null && getContext() != null) {
            movie.setFavorite(!movie.isFavorite());
            Utilities.setFavoriteOrWatched(getContext(), binding.favourite, movie.isFavorite());
            mPresenter.favouriteChanged(movie);
        }
    }

    public void watchedClicked() {
        if (binding != null && movie != null && getContext() != null) {
            movie.setWatched(!movie.isWatched());
            Utilities.setFavoriteOrWatched(getContext(), binding.watched, movie.isWatched());
            mPresenter.watchedChanged(movie);
        }
    }

    public void backClicked() {
        BackStack.onBackPressed();
    }
}
