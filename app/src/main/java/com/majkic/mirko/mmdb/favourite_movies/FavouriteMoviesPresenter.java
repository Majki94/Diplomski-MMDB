package com.majkic.mirko.mmdb.favourite_movies;

import android.content.Context;

import com.majkic.mirko.mmdb.data.MovieDataRepository;
import com.majkic.mirko.mmdb.data.MovieDataRepositoryImplementation;
import com.majkic.mirko.mmdb.model.Movie;

import java.util.List;

/**
 * Created by hp on 09.12.2019.
 */

public class FavouriteMoviesPresenter implements FavouriteMoviesContract.UserActionsListener {

    private Context context;
    private FavouriteMoviesContract.View view;
    private MovieDataRepository repository;

    public FavouriteMoviesPresenter(Context context, FavouriteMoviesContract.View view) {
        this.context = context;
        this.view = view;
        this.repository = MovieDataRepositoryImplementation.getInstance(context);
    }

    @Override
    public void getFavouriteMovies() {
        view.showProgress();
        repository.getFavouriteMovies(new MovieDataRepository.GetMoviesCallback() {
            @Override
            public void onMoviesGot(List<Movie> movies) {
                view.setFavouriteMovies(movies);
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
