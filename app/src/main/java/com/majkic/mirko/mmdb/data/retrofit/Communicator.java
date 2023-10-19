package com.majkic.mirko.mmdb.data.retrofit;

import android.util.Log;

import com.majkic.mirko.mmdb.Constants;
import com.majkic.mirko.mmdb.data.model.Movie;
import com.majkic.mirko.mmdb.data.model.Person;
import com.majkic.mirko.mmdb.data.model.SearchResponse;
import com.majkic.mirko.mmdb.data.model.responses.MovieResponse;
import com.majkic.mirko.mmdb.data.model.responses.PeopleResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by hp on 19.01.2019.
 */

public class Communicator {

    public static final String TAG = Communicator.class.getSimpleName();

    public static void getMovies(final int page, final GetMoviesCallback callback) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BACKEND.TMDB_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<MovieResponse> call = api.discover(page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.body() != null) {
                    MovieResponse movieResponse = response.body();
                    callback.onMoviesGot(movieResponse.getMovieList());
                } else {
                    callback.onMoviesGot(new ArrayList<Movie>());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: getMovies failed");
                t.printStackTrace();
            }
        });
    }

    public static void getPopularPeople(int page, final GetPeopleCallback callback) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BACKEND.TMDB_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<PeopleResponse> call = api.popularPeople(page);
        call.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                if (response.body() != null && response.body().getPeople() != null) {
                    callback.onPeopleGot(response.body().getPeople());
                } else {
                    callback.onPeopleGot(new ArrayList<Person>());
                }

            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: getPopularPeople failed");
                t.printStackTrace();
            }
        });
    }

    public static void searchMoviesForTitle(int page, String title, final SearchCallBack callBack) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BACKEND.TMDB_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<SearchResponse> call = api.searchMoviesFor(page, title);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.body() != null && response.body().getResults() != null) {
                    callBack.onSearchFinished(response.body().getResults());
                } else {
                    callBack.onSearchFinished(new ArrayList<Movie>());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }

    public interface GetMoviesCallback {
        void onMoviesGot(List<Movie> movies);
    }

    public interface GetPeopleCallback {
        void onPeopleGot(List<Person> people);
    }

    public interface SearchCallBack {
        void onSearchFinished(List<Movie> searchResults);
    }

}
