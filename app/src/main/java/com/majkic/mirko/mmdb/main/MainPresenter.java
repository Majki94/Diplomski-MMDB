package com.majkic.mirko.mmdb.main;

import com.majkic.mirko.mmdb.data.MovieDataRepository;
import com.majkic.mirko.mmdb.data.MovieDataRepositoryImplementation;
import com.majkic.mirko.mmdb.model.Movie;

import java.util.List;

/**
 * Created by hp on 07.04.2019.
 */

public class MainPresenter implements MainContract.UserActionsListener {

    private MainContract.View view;
    private MovieDataRepository repository;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        repository = MovieDataRepositoryImplementation.getInstance();
    }

    @Override
    public void getMovies() {
        view.showProgress();
        repository.getCachedMovies(new MovieDataRepository.GetMoviesCallback() {
            @Override
            public void onMoviesGot(List<Movie> movies) {
                view.hideProgress();
                view.showMovies(movies);
            }
        });
    }

    @Override
    public void getNextMovies() {
        view.showProgress();
        repository.getNextMovies(new MovieDataRepository.GetMoviesCallback() {
            @Override
            public void onMoviesGot(List<Movie> movies) {
                view.hideProgress();
                view.appendMovies(movies);
            }
        });
    }
}
