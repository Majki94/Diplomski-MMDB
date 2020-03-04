package com.majkic.mirko.mmdb.search;

import com.majkic.mirko.mmdb.model.Movie;
import com.majkic.mirko.mmdb.model.Search;

import java.util.List;

public interface SearchContract {

    interface View {
        void showRecentSearches(List<Search> searchList);

        void showSearchResults(List<Movie> searchResults);
    }

    interface UserActionsListener {
        void getRecentSearches();

        void startSearch(String term);
    }

}
