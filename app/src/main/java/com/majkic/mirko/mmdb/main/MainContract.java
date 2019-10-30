package com.majkic.mirko.mmdb.main;

import com.majkic.mirko.mmdb.model.Movie;

import java.util.List;

/**
 * Created by hp on 07.04.2019.
 */

public interface MainContract {

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
    }
}
