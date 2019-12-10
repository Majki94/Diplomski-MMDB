package com.majkic.mirko.mmdb.popular_movies;

import android.content.Context;

import com.majkic.mirko.mmdb.data.MovieDataRepository;
import com.majkic.mirko.mmdb.data.MovieDataRepositoryImplementation;
import com.majkic.mirko.mmdb.model.Movie;

import java.util.List;

/**
 * Created by hp on 07.04.2019.
 */

public class PopularMoviesPresenter implements PopularMoviesContract.UserActionsListener {

    private PopularMoviesContract.View view;
    private Context context;
    private MovieDataRepository repository;

    public PopularMoviesPresenter(PopularMoviesContract.View view, Context context) {
        this.view = view;
        this.context = context;
        repository = MovieDataRepositoryImplementation.getInstance(this.context);
    }

    @Override
    public void getMovies() {
        view.showProgress();
        repository.getCachedMovies(new MovieDataRepository.GetMoviesCallback() {
            @Override
            public void onMoviesGot(List<Movie> movies) {
                view.hideProgress();
                view.showMovies(movies);
            }
        });
    }

    @Override
    public void getNextMovies() {
        view.showProgress();
        repository.getNextMovies(new MovieDataRepository.GetMoviesCallback() {
            @Override
            public void onMoviesGot(List<Movie> movies) {
                view.hideProgress();
                view.appendMovies(movies);
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

    @Override
    public void syncFavouriteAndWatched() {
        view.hideProgress();
        repository.syncCachedAndSaved(new MovieDataRepository.SyncCallback() {
            @Override
            public void onSynced() {
                view.hideProgress();
                getMovies();
            }
        });
    }
}
