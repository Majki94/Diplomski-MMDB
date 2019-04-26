package com.majkic.mirko.mmdb.retrofit;

import android.util.Log;

import com.majkic.mirko.mmdb.Constants;
import com.majkic.mirko.mmdb.model.Movie;
import com.majkic.mirko.mmdb.model.responses.MovieResponse;

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
                MovieResponse movieResponse = response.body();
                callback.onMoviesGot(movieResponse.getMovieList());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: getMovies failed");
                t.printStackTrace();
            }
        });
    }

    public interface GetMoviesCallback {
        void onMoviesGot(List<Movie> movies);
    }

}
