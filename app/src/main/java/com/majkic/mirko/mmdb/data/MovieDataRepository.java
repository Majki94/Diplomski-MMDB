package com.majkic.mirko.mmdb.data;

import com.majkic.mirko.mmdb.model.Movie;

import java.util.List;

/**
 * Created by hp on 01.03.2019.
 */

public interface MovieDataRepository {

    void getNextMovies(GetMoviesCallback callback);

    void getCachedMovies(GetMoviesCallback callback);

    void saveMovie(Movie movie, SaveMovieCallback callback);

    void getFavouriteMovies(GetMoviesCallback callback);

    void getWatchedMovies(GetMoviesCallback callback);

    void syncCachedAndSaved(SyncCallback callback);

    void getMovieFromDatabaseForID(int id, MovieFromDatabaseCallback callback);

    interface GetMoviesCallback {
        void onMoviesGot(List<Movie> movies);
    }

    interface SaveMovieCallback {
        void onMovieSaved();
    }

    interface SyncCallback{
        void onSynced();
    }

    interface MovieFromDatabaseCallback{
        void onMovieGot(Movie m);
    }

}
