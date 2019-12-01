package com.majkic.mirko.mmdb.movie_details;

import com.majkic.mirko.mmdb.model.Movie;

/**
 * Created by hp on 07.11.2019.
 */

public interface MovieDetailsContract {

    interface View {
        void showProgress();

        void hideProgress();

        void showMovie(Movie movie);
    }

    interface UserActionsListener {
        void getMovieForID(int id);
    }

}