package com.majkic.mirko.mmdb.movie_details;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.majkic.mirko.mmdb.Constants;
import com.majkic.mirko.mmdb.R;
import com.majkic.mirko.mmdb.Utilities;
import com.majkic.mirko.mmdb.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieDetailsFragment extends Fragment implements MovieDetailsContract.View {

    private static final String ARG_MOVIE_ID = "movie_id";

    private int movieId;
    private Unbinder unbinder;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.release_date)
    TextView releaseDate;
    @BindView(R.id.poster)
    ImageView poster;
    @BindView(R.id.favourite)
    ImageView favourite;
    @BindView(R.id.watched)
    ImageView watched;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private MovieDetailsContract.UserActionsListener mPresenter;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public static MovieDetailsFragment newInstance(int movieId) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getInt(ARG_MOVIE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_movie_details, container, false);
        unbinder = ButterKnife.bind(this, root);

        mPresenter = new MovieDetailsPresenter(getContext(), this);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getMovieForID(movieId);
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
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMovie(Movie movie) {
        if (poster != null) {
            Glide.with(this).load(Constants.BACKEND.TMDB_POSTER_BASE_URL + movie.getPosterPath()).into(poster);
        }
        if (favourite != null) {
            Utilities.setFavoriteOrWatched(getContext(), favourite, movie.isFavorite());
        }
        if (watched != null) {
            Utilities.setFavoriteOrWatched(getContext(), watched, movie.isWatched());
        }
        if (title != null) {
            title.setText(movie.getTitle());
        }
        if (releaseDate != null) {
            releaseDate.setText(getString(R.string.release_date));
            releaseDate.append(" : ");
            releaseDate.append(movie.getReleaseDate());
        }
        if (overview != null) {
            overview.setText(movie.getOverview());
        }
    }
}
