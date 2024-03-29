package com.majkic.mirko.mmdb.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.majkic.mirko.mmdb.data.database.RecentSearchesDatabase;
import com.majkic.mirko.mmdb.data.model.Movie;
import com.majkic.mirko.mmdb.data.model.Search;
import com.majkic.mirko.mmdb.data.retrofit.Communicator;

import java.util.ArrayList;
import java.util.List;

public class SearchDataRepositoryImplementation implements SearchDataRepository {

    private static final String TAG = SearchDataRepositoryImplementation.class.getSimpleName();
    private Context context;
    private int page;
    private List<Movie> cache;
    private static SearchDataRepository INSTANCE;
    private RecentSearchesDatabase searchesDb;

    public static SearchDataRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SearchDataRepositoryImplementation(context);
        }
        return INSTANCE;
    }

    private SearchDataRepositoryImplementation(Context context) {
        cache = new ArrayList<>();
        page = 1;
        this.context = context;
        searchesDb = RecentSearchesDatabase.getInstance(this.context);
    }

    @Override
    public void startSearch(final String term, final SearchCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Communicator.searchMoviesForTitle(page, term, new Communicator.SearchCallBack() {
                    @Override
                    public void onSearchFinished(final List<Movie> searchResults) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                cache = searchResults;
                                callback.onSearchResultsGot(searchResults);
                            }
                        });
                    }
                });
            }
        }).start();
    }

    @Override
    public void saveSearch(final Search search, final SaveSearchCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                searchesDb.searchDao().insert(search);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSearchSaved();
                    }
                });
            }
        }).start();
    }

    @Override
    public void getRecentSearches(final RecentSearchesCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Search> searchList = searchesDb.searchDao().getAll();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onRecentSearchesGot(searchList);
                    }
                });
            }
        }).start();
    }

    @Override
    public List<Movie> getCache() {
        return cache;
    }
}
