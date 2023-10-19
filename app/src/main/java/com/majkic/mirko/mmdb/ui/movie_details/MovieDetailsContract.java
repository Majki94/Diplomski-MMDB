package com.majkic.mirko.mmdb.ui.movie_details;

import com.majkic.mirko.mmdb.data.model.Movie;

/**
 * Created by hp on 07.11.2019.
 */

public interface MovieDetailsContract {

    interface View {
        void showProgress();

        void hideProgress();

        void showMovie(Movie movie);

        void showUnableToRetrieveInfo();
    }

    interface UserActionsListener {
        void getMovieForID(int id, boolean loadSearch);

        void favouriteChanged(Movie movie);

        void watchedChanged(Movie movie);
    }

}
