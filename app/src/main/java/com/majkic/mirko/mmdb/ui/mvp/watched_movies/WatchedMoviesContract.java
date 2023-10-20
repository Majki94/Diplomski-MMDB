package com.majkic.mirko.mmdb.ui.mvp.watched_movies;

import com.majkic.mirko.mmdb.data.model.Movie;

import java.util.List;

/**
 * Created by hp on 09.12.2019.
 */

public interface WatchedMoviesContract {

    interface View {
        void showProgress();

        void hideProgress();

        void setWatchedMovies(List<Movie> movies);
    }

    interface UserActionsListener {
        void getWatchedMovies();

        void favouriteChanged(Movie movie);

        void watchedChanged(Movie movie);
    }

}
