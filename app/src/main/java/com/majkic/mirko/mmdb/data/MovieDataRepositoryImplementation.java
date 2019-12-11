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
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                checkForFavouriteAndWatched(movies);
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        cachedMovies.addAll(movies);
                                        callback.onMoviesGot(movies);
                                        page++;
                                    }
                                });
                            }
                        }).start();
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

    @Override
    public void saveMovie(final Movie movie, final SaveMovieCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Movie> savedMovies = moviesDb.movieDao().getAll();
                if (isMovieInProvidedList(movie, savedMovies)) {
                    moviesDb.movieDao().update(movie);
                } else {
                    moviesDb.movieDao().insert(movie);
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onMovieSaved();
                    }
                });
            }
        }).start();
    }

    @Override
    public void getFavouriteMovies(final GetMoviesCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Movie> movies = moviesDb.movieDao().getAll();
                final List<Movie> retVal = new ArrayList<>();
                for (Movie m : movies) {
                    if (m.isFavorite()) {
                        retVal.add(m);
                    }
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onMoviesGot(retVal);
                    }
                });
            }
        }).start();
    }

    @Override
    public void getWatchedMovies(final GetMoviesCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Movie> movies = moviesDb.movieDao().getAll();
                final List<Movie> retVal = new ArrayList<>();
                for (Movie m : movies) {
                    if (m.isWatched()) {
                        retVal.add(m);
                    }
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onMoviesGot(retVal);
                    }
                });
            }
        }).start();
    }

    @Override
    public void syncCachedAndSaved(final SyncCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                checkForFavouriteAndWatched(cachedMovies);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSynced();
                    }
                });
            }
        }).start();
    }

    @Override
    public void getMovieFromDatabaseForID(final int id, final MovieFromDatabaseCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Movie> savedMovies = moviesDb.movieDao().getAll();
                Movie retM = null;
                for (Movie m : savedMovies){
                    if (id == m.getId()){
                        retM = m;
                        break;
                    }
                }
                final Movie finalRetM = retM;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onMovieGot(finalRetM);
                    }
                });
            }
        }).start();
    }

    private void checkForFavouriteAndWatched(List<Movie> movies) {
        List<Movie> savedMovies = moviesDb.movieDao().getAll();
        for (Movie movie : movies) {
            for (Movie saved : savedMovies) {
                if (saved.getId() == movie.getId()) {
                    movie.setFavorite(saved.isFavorite());
                    movie.setWatched(saved.isWatched());
                }
            }
        }
    }

    private boolean isMovieInProvidedList(Movie m, List<Movie> providedList) {
        for (Movie p : providedList) {
            if (p.getId() == m.getId()) {
                return true;
            }
        }
        return false;
    }

}
