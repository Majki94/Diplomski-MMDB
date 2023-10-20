package com.majkic.mirko.mmdb.ui.mvvm.movie_details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.majkic.mirko.mmdb.MMDBApplication;
import com.majkic.mirko.mmdb.data.model.Movie;
import com.majkic.mirko.mmdb.data.repository.MovieDataRepository;
import com.majkic.mirko.mmdb.data.repository.MovieDataRepositoryImplementation;
import com.majkic.mirko.mmdb.data.repository.SearchDataRepository;
import com.majkic.mirko.mmdb.data.repository.SearchDataRepositoryImplementation;

import java.util.List;

/**
 * Created by hp on 07.11.2019.
 */

public class MovieDetailsViewModel extends ViewModel {

    private MovieDataRepository movieDataRepository = MovieDataRepositoryImplementation.getInstance(MMDBApplication.getContext());
    private SearchDataRepository searchDataRepository = SearchDataRepositoryImplementation.getInstance(MMDBApplication.getContext());
    MutableLiveData<Boolean> showProgress = new MutableLiveData<>(false);
    MutableLiveData<Boolean> errorOccurred = new MutableLiveData<>(false);
    MutableLiveData<Movie> movie = new MutableLiveData<>(null);

    public void getMovieForID(final int id, boolean loadSearch) {
        showProgress.setValue(true);
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
                                    showProgress.setValue(false);
                                    movie.setValue(m);
                                } else {
                                    errorOccurred.setValue(true);
                                }
                            }
                        });
                    } else {
                        showProgress.setValue(false);
                        movie.setValue(retMovie);
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
            showProgress.setValue(false);
            movie.setValue(retMovie);
        }
    }

    public void favouriteChanged(Movie movie) {
        showProgress.setValue(true);
        movieDataRepository.saveMovie(movie, new MovieDataRepository.SaveMovieCallback() {
            @Override
            public void onMovieSaved() {
                showProgress.setValue(false);
            }
        });
    }

    public void watchedChanged(Movie movie) {
        showProgress.setValue(true);
        movieDataRepository.saveMovie(movie, new MovieDataRepository.SaveMovieCallback() {
            @Override
            public void onMovieSaved() {
                showProgress.setValue(false);
            }
        });
    }

}
