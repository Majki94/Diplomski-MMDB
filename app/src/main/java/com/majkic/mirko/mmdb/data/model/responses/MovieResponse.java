package com.majkic.mirko.mmdb.data.model.responses;

import com.google.gson.annotations.SerializedName;
import com.majkic.mirko.mmdb.data.model.Movie;

import java.util.List;

/**
 * Created by hp on 28.01.2019.
 */

public class MovieResponse {
    @SerializedName("page")
    int page;
    @SerializedName("results")
    List<Movie> movieList;

    public MovieResponse() {
    }

    public MovieResponse(int page, List<Movie> movieList) {
        this.page = page;
        this.movieList = movieList;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
