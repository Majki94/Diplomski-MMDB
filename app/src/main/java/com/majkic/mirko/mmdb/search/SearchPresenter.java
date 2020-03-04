package com.majkic.mirko.mmdb.search;

public class SearchPresenter implements SearchContract.UserActionsListener {

    private SearchContract.View view;

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void getRecentSearches() {

    }

    @Override
    public void startSearch(String term) {

    }
}
