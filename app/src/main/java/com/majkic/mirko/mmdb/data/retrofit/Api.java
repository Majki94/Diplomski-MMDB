package com.majkic.mirko.mmdb.data.retrofit;

import com.majkic.mirko.mmdb.Constants;
import com.majkic.mirko.mmdb.data.model.SearchResponse;
import com.majkic.mirko.mmdb.data.model.responses.MovieResponse;
import com.majkic.mirko.mmdb.data.model.responses.PeopleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hp on 19.01.2019.
 */

public interface Api {

    @GET("discover/movie?sort_by=popularity.desc&api_key=" + Constants.BACKEND.TMDB_API_KEY)
    Call<MovieResponse> discover(@Query("page") int page);

    @GET("person/popular?sort_by=popularity.desc&api_key=" + Constants.BACKEND.TMDB_API_KEY)
    Call<PeopleResponse> popularPeople(@Query("page") int page);

    @GET("search/movie?api_key=" + Constants.BACKEND.TMDB_API_KEY)
    Call<SearchResponse> searchMoviesFor(@Query("page") int page, @Query("query") String movieTitle);

}
