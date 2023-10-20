package com.majkic.mirko.mmdb.ui.mvvm.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.majkic.mirko.mmdb.MMDBApplication;
import com.majkic.mirko.mmdb.data.model.Movie;
import com.majkic.mirko.mmdb.data.model.Search;
import com.majkic.mirko.mmdb.data.repository.SearchDataRepository;
import com.majkic.mirko.mmdb.data.repository.SearchDataRepositoryImplementation;

import java.util.Collections;
import java.util.List;

public class SearchViewModel extends ViewModel {

    private final SearchDataRepository repository = SearchDataRepositoryImplementation.getInstance(MMDBApplication.getContext());
    MutableLiveData<Boolean> showProgress = new MutableLiveData<>(false);
    MutableLiveData<List<Search>> recentSearches = new MutableLiveData<>(Collections.emptyList());
    MutableLiveData<List<Movie>> searchResults = new MutableLiveData<>(Collections.emptyList());

    public void getRecentSearches() {
        showProgress.setValue(true);
        repository.getRecentSearches(new SearchDataRepository.RecentSearchesCallback() {
            @Override
            public void onRecentSearchesGot(List<Search> searches) {
                recentSearches.setValue(searches);
                showProgress.setValue(false);
            }
        });
    }

    public void startSearch(final String term) {
        showProgress.setValue(true);
        repository.saveSearch(new Search(term, System.currentTimeMillis()), new SearchDataRepository.SaveSearchCallback() {
            @Override
            public void onSearchSaved() {
                repository.startSearch(term, new SearchDataRepository.SearchCallback() {
                    @Override
                    public void onSearchResultsGot(List<Movie> foundMovies) {
                        searchResults.setValue(foundMovies);
                        showProgress.setValue(false);
                    }
                });
            }
        });
    }
}
