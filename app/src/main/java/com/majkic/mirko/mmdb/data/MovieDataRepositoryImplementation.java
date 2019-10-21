package com.majkic.mirko.mmdb.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.majkic.mirko.mmdb.database.MoviesDatabase;
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
    private Context context;
    private MoviesDatabase moviesDb;

    public static MovieDataRepositoryImplementation getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MovieDataRepositoryImplementation(context);
        }
        return INSTANCE;
    }

    private MovieDataRepositoryImplementation(Context context) {
        cachedMovies = new ArrayList<>();
        page = 1;
        this.context = context;
        moviesDb = MoviesDatabase.getInstance(this.context);
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
