package com.majkic.mirko.mmdb.watched_movies;

import android.content.Context;

import com.majkic.mirko.mmdb.data.MovieDataRepository;
import com.majkic.mirko.mmdb.data.MovieDataRepositoryImplementation;
import com.majkic.mirko.mmdb.model.Movie;

import java.util.List;

/**
 * Created by hp on 09.12.2019.
 */

public class WatchedMoviesPresenter implements WatchedMoviesContract.UserActionsListener {

    private Context context;
    private WatchedMoviesContract.View view;
    private MovieDataRepository repository;

    public WatchedMoviesPresenter(Context context, WatchedMoviesContract.View view) {
        this.context = context;
        this.view = view;
        this.repository = MovieDataRepositoryImplementation.getInstance(context);
    }

    @Override
    public void getWatchedMovies() {
        view.showProgress();
        repository.getWatchedMovies(new MovieDataRepository.GetMoviesCallback() {
            @Override
            public void onMoviesGot(List<Movie> movies) {
                view.setWatchedMovies(movies);
                view.hideProgress();
            }
        });
    }
}
