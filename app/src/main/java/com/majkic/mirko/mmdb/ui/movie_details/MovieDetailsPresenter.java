package com.majkic.mirko.mmdb.ui.movie_details;

import android.content.Context;

import com.majkic.mirko.mmdb.data.repository.MovieDataRepository;
import com.majkic.mirko.mmdb.data.repository.MovieDataRepositoryImplementation;
import com.majkic.mirko.mmdb.data.repository.SearchDataRepository;
import com.majkic.mirko.mmdb.data.repository.SearchDataRepositoryImplementation;
import com.majkic.mirko.mmdb.data.model.Movie;

import java.util.List;

/**
 * Created by hp on 07.11.2019.
 */

public class MovieDetailsPresenter implements MovieDetailsContract.UserActionsListener {

    private Context context;
    private MovieDetailsContract.View view;
    private MovieDataRepository movieDataRepository;
    private SearchDataRepository searchDataRepository;

    public MovieDetailsPresenter(Context context, MovieDetailsContract.View view) {
        this.context = context;
        this.view = view;
        this.movieDataRepository = MovieDataRepositoryImplementation.getInstance(context);
        this.searchDataRepository = SearchDataRepositoryImplementation.getInstance(context);
    }

    @Override
    public void getMovieForID(final int id, boolean loadSearch) {
        view.showProgress();
        if (!loadSearch) {
            movieDataRepository.getCachedMovies(new MovieDataRepository.GetMoviesCallback() {
                @Override
                public void onMoviesGot(List<Movie> movies) {
                    Movie retMovie = null;
                    for (Movie m : movies) {
                        if (m.getId() == id) {
                            retMovie = m;
                            break;
                        }
                    }
                    if (retMovie == null) {
                        movieDataRepository.getMovieFromDatabaseForID(id, new MovieDataRepository.MovieFromDatabaseCallback() {
                            @Override
                            public void onMovieGot(Movie m) {
                                if (m != null) {
                                    view.hideProgress();
                                    view.showMovie(m);
                                } else {

                                }
                            }
                        });
                    } else {
                        view.hideProgress();
                        view.showMovie(retMovie);
                    }
                }
            });
        } else {
            List<Movie> search = searchDataRepository.getCache();
            Movie retMovie = null;
            for (Movie m : search) {
                if (m.getId() == id) {
                    retMovie = m;
                    break;
                }
            }
            view.hideProgress();
            view.showMovie(retMovie);
        }
    }

    @Override
    public void favouriteChanged(Movie movie) {
        view.showProgress();
        movieDataRepository.saveMovie(movie, new MovieDataRepository.SaveMovieCallback() {
            @Override
            public void onMovieSaved() {
                view.hideProgress();
            }
        });
    }

    @Override
    public void watchedChanged(Movie movie) {
        view.showProgress();
        movieDataRepository.saveMovie(movie, new MovieDataRepository.SaveMovieCallback() {
            @Override
            public void onMovieSaved() {
                view.hideProgress();
            }
        });
    }
}
