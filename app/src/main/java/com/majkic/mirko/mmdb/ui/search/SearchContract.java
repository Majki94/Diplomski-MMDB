package com.majkic.mirko.mmdb.ui.search;

import com.majkic.mirko.mmdb.data.model.Movie;
import com.majkic.mirko.mmdb.data.model.Search;

import java.util.List;

public interface SearchContract {

    interface View {
        void showProgress();

        void hideProgress();

        void showRecentSearches(List<Search> searchList);

        void showSearchResults(List<Movie> searchResults);
    }

    interface UserActionsListener {
        void getRecentSearches();

        void startSearch(String term);
    }

}
