package com.majkic.mirko.mmdb.movie_details;

import android.content.Context;

import com.majkic.mirko.mmdb.data.MovieDataRepository;
import com.majkic.mirko.mmdb.data.MovieDataRepositoryImplementation;
import com.majkic.mirko.mmdb.model.Movie;

import java.util.List;

/**
 * Created by hp on 07.11.2019.
 */

public class MovieDetailsPresenter implements MovieDetailsContract.UserActionsListener {

    private Context context;
    private MovieDetailsContract.View view;
    private MovieDataRepository repository;

    public MovieDetailsPresenter(Context context, MovieDetailsContract.View view) {
        this.context = context;
        this.view = view;
        this.repository = MovieDataRepositoryImplementation.getInstance(context);
    }

    @Override
    public void getMovieForID(final int id) {
        view.showProgress();
        repository.getCachedMovies(new MovieDataRepository.GetMoviesCallback() {
            @Override
            public void onMoviesGot(List<Movie> movies) {
                for (Movie m : movies) {
                    if (m.getId() == id) {
                        view.hideProgress();
                        view.showMovie(m);
                        return;
                    }
                }
            }
        });
    }
}
