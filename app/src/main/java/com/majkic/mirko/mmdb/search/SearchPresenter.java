package com.majkic.mirko.mmdb.search;

import android.content.Context;

import com.majkic.mirko.mmdb.data.SearchDataRepository;
import com.majkic.mirko.mmdb.data.SearchDataRepositoryImplementation;
import com.majkic.mirko.mmdb.model.Movie;
import com.majkic.mirko.mmdb.model.Search;

import java.util.List;

public class SearchPresenter implements SearchContract.UserActionsListener {

    private Context context;
    private SearchContract.View view;
    private SearchDataRepository repository;

    public SearchPresenter(Context context, SearchContract.View view) {
        this.context = context;
        this.view = view;
        repository = SearchDataRepositoryImplementation.getInstance(context);
    }

    @Override
    public void getRecentSearches() {
        view.showProgress();
        repository.getRecentSearches(new SearchDataRepository.RecentSearchesCallback() {
            @Override
            public void onRecentSearchesGot(List<Search> searches) {
                view.showRecentSearches(searches);
                view.hideProgress();
            }
        });
    }

    @Override
    public void startSearch(final String term) {
        view.showProgress();
        repository.saveSearch(new Search(term, System.currentTimeMillis()), new SearchDataRepository.SaveSearchCallback() {
            @Override
            public void onSearchSaved() {
                repository.startSearch(term, new SearchDataRepository.SearchCallback() {
                    @Override
                    public void onSearchResultsGot(List<Movie> foundMovies) {
                        view.showSearchResults(foundMovies);
                        view.hideProgress();
                    }
                });
            }
        });
    }
}
