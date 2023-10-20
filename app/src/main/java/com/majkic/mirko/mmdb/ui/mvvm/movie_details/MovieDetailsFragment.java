package com.majkic.mirko.mmdb.ui.mvvm.movie_details;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.majkic.mirko.mmdb.BackStack;
import com.majkic.mirko.mmdb.Constants;
import com.majkic.mirko.mmdb.R;
import com.majkic.mirko.mmdb.Utilities;
import com.majkic.mirko.mmdb.data.model.Movie;
import com.majkic.mirko.mmdb.databinding.FragmentMovieDetailsBinding;

public class MovieDetailsFragment extends Fragment {

    private static final String ARG_MOVIE_ID = "movie_id";
    private static final String ARG_LOAD_SEARCH = "load_search";

    private int movieId;
    private boolean loadSearch;
    private FragmentMovieDetailsBinding binding;
    private MovieDetailsViewModel viewModel;

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

        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        binding.back.setOnClickListener(view -> backClicked());
        binding.favourite.setOnClickListener(view -> favouriteClicked());
        binding.watched.setOnClickListener(view -> watchedClicked());

        viewModel.showProgress.observe(getViewLifecycleOwner(), shouldShowProgress -> {
            if (shouldShowProgress) {
                showProgress();
            } else {
                hideProgress();
            }
        });

        viewModel.movie.observe(getViewLifecycleOwner(), movie -> {
            if (movie != null) {
                showMovie(movie);
            }
        });

        viewModel.errorOccurred.observe(getViewLifecycleOwner(), errorOccurred -> {
            if (errorOccurred) {
                showUnableToRetrieveInfo();
            }
        });

        viewModel.getMovieForID(movieId, loadSearch);

        return binding.getRoot();
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

    public void showMovie(Movie movie) {
        if (binding == null) {
            return;
        }
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

    public void showUnableToRetrieveInfo() {
        if (getContext() != null) {
            Toast.makeText(getContext(), "Unable to retrieve movie info", Toast.LENGTH_SHORT).show();
        }
    }

    public void favouriteClicked() {
        Movie movie = viewModel.movie.getValue();
        if (binding != null && movie != null && getContext() != null) {
            movie.setFavorite(!movie.isFavorite());
            Utilities.setFavoriteOrWatched(getContext(), binding.favourite, movie.isFavorite());
            viewModel.favouriteChanged(movie);
        }
    }

    public void watchedClicked() {
        Movie movie = viewModel.movie.getValue();
        if (binding != null && movie != null && getContext() != null) {
            movie.setWatched(!movie.isWatched());
            Utilities.setFavoriteOrWatched(getContext(), binding.watched, movie.isWatched());
            viewModel.watchedChanged(movie);
        }
    }

    public void backClicked() {
        BackStack.onBackPressed();
    }
}
