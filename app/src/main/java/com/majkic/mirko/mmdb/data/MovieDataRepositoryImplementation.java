package com.majkic.mirko.mmdb.data;

import com.majkic.mirko.mmdb.model.Movie;
import com.majkic.mirko.mmdb.retrofit.Communicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 01.03.2019.
 */

public class MovieDataRepositoryImplementation implements MovieDataRepository {

    private static MovieDataRepositoryImplementation INSTANCE;
    private List<Movie> cachedMovies;
    private int page;

    public static MovieDataRepositoryImplementation getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovieDataRepositoryImplementation();
        }
        return INSTANCE;
    }

    private MovieDataRepositoryImplementation() {
        cachedMovies = new ArrayList<>();
        page = 1;
    }

    @Override
    public void getNextMovies(final GetMoviesCallback callback) {
        Communicator.getMovies(page, new Communicator.GetMoviesCallback() {
            @Override
            public void onMoviesGot(List<Movie> movies) {
                cachedMovies.addAll(movies);
                callback.onMoviesGot(movies);
                page++;
            }
        });
    }

    @Override
    public void getCachedMovies(GetMoviesCallback callback) {
        if (cachedMovies.size() > 0) {
            callback.onMoviesGot(cachedMovies);
        } else {
            getNextMovies(callback);
        }
    }

}
