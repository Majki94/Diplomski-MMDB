package com.majkic.mirko.mmdb.ui.mvp.popular_movies;

import com.majkic.mirko.mmdb.data.model.Movie;

import java.util.List;

/**
 * Created by hp on 07.04.2019.
 */

public interface PopularMoviesContract {

    interface View {
        void showProgress();

        void hideProgress();

        void showMovies(List<Movie> movies);

        void appendMovies(List<Movie> nextMovies);
    }

    interface UserActionsListener {
        void getMovies();

        void getNextMovies();

        void favouriteChanged(Movie movie);

        void watchedChanged(Movie movie);

        void syncFavouriteAndWatched();
    }


}
