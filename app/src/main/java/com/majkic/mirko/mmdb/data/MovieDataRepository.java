package com.majkic.mirko.mmdb.data;

import com.majkic.mirko.mmdb.model.Movie;

import java.util.List;

/**
 * Created by hp on 01.03.2019.
 */

public interface MovieDataRepository {

    void getNextMovies(GetMoviesCallback callback);

    void getCachedMovies(GetMoviesCallback callback);

    interface GetMoviesCallback {
        void onMoviesGot(List<Movie> movies);
    }

}
