package com.majkic.mirko.mmdb.data;

import android.os.Handler;
import android.os.Looper;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                Communicator.getMovies(page, new Communicator.GetMoviesCallback() {
                    @Override
                    public void onMoviesGot(final List<Movie> movies) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                cachedMovies.addAll(movies);
                                callback.onMoviesGot(movies);
                                page++;
                            }
                        });
                    }
                });
            }
        }).start();
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
