package com.majkic.mirko.mmdb.data.repository;

import com.majkic.mirko.mmdb.data.model.Movie;
import com.majkic.mirko.mmdb.data.model.Search;

import java.util.List;

public interface SearchDataRepository {

    void startSearch(String term, SearchCallback callback);

    void saveSearch(Search search, SaveSearchCallback callback);

    void getRecentSearches(RecentSearchesCallback callback);

    List<Movie> getCache();

    interface SearchCallback {
        void onSearchResultsGot(List<Movie> foundMovies);
    }

    interface SaveSearchCallback {
        void onSearchSaved();
    }

    interface RecentSearchesCallback {
        void onRecentSearchesGot(List<Search> searches);
    }
}
