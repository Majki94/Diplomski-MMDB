package com.majkic.mirko.mmdb.main;

import android.content.Context;

import com.majkic.mirko.mmdb.data.MovieDataRepository;
import com.majkic.mirko.mmdb.data.MovieDataRepositoryImplementation;
import com.majkic.mirko.mmdb.model.Movie;

import java.util.List;

/**
 * Created by hp on 07.04.2019.
 */

public class MainPresenter implements MainContract.UserActionsListener {

    private MainContract.View view;
    private Context context;
    private MovieDataRepository repository;

    public MainPresenter(MainContract.View view, Context context) {
        this.view = view;
        this.context = context;
        repository = MovieDataRepositoryImplementation.getInstance(this.context);
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
