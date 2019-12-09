package com.majkic.mirko.mmdb.favourite_movies;

import com.majkic.mirko.mmdb.model.Movie;

import java.util.List;

/**
 * Created by hp on 09.12.2019.
 */

public interface FavouriteMoviesContract {

    interface View {
        void showProgress();

        void hideProgress();

        void setFavouriteMovies(List<Movie> movies);
    }

    interface UserActionsListener {
        void getFavouriteMovies();
    }

}
