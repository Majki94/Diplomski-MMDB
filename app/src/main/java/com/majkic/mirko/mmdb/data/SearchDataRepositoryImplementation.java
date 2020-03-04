package com.majkic.mirko.mmdb.data;

import android.content.Context;

import com.majkic.mirko.mmdb.model.Search;

public class SearchDataRepositoryImplementation implements SearchDataRepository {

    private static SearchDataRepository INSTANCE;

    public static SearchDataRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SearchDataRepositoryImplementation();
        }
        return INSTANCE;
    }

    private SearchDataRepositoryImplementation() {
    }

    @Override
    public void startSearch(String term, SearchCallback callback) {

    }

    @Override
    public void saveSearch(Search search) {

    }

    @Override
    public void getRecentSearches(RecentSearchesCallback callback) {

    }
}
