package com.majkic.mirko.mmdb.ui.mvp.watched_movies;

import android.content.Context;

import com.majkic.mirko.mmdb.data.repository.MovieDataRepository;
import com.majkic.mirko.mmdb.data.repository.MovieDataRepositoryImplementation;
import com.majkic.mirko.mmdb.data.model.Movie;

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

    @Override
    public void favouriteChanged(Movie movie) {
        view.showProgress();
        repository.saveMovie(movie, new MovieDataRepository.SaveMovieCallback() {
            @Override
            public void onMovieSaved() {
                view.hideProgress();
            }
        });
    }

    @Override
    public void watchedChanged(Movie movie) {
        view.showProgress();
        repository.saveMovie(movie, new MovieDataRepository.SaveMovieCallback() {
            @Override
            public void onMovieSaved() {
                view.hideProgress();
            }
        });
    }
}
