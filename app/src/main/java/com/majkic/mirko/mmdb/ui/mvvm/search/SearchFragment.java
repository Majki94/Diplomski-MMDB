package com.majkic.mirko.mmdb.ui.mvvm.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.majkic.mirko.mmdb.BackStack;
import com.majkic.mirko.mmdb.Utilities;
import com.majkic.mirko.mmdb.data.model.Movie;
import com.majkic.mirko.mmdb.data.model.Search;
import com.majkic.mirko.mmdb.databinding.FragmentSearchBinding;
import com.majkic.mirko.mmdb.ui.adapters.MovieAdapter;
import com.majkic.mirko.mmdb.ui.adapters.RecentSearchesAdapter;
import com.majkic.mirko.mmdb.ui.mvvm.movie_details.MovieDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        binding.recentSearches.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recentSearches.setAdapter(new RecentSearchesAdapter(getContext(), new ArrayList<>(), searchInRecent -> {
            binding.searchEditText.setText(searchInRecent.getSearchTerm());
            clearFocusAndHideKeyboard(binding.searchEditText);
            viewModel.startSearch(searchInRecent.getSearchTerm());
        }));

        binding.searchResults.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searchResults.setAdapter(new MovieAdapter(getContext(), new ArrayList<>(), new MovieAdapter.MovieClickListener() {
            @Override
            public void onMovieClicked(Movie m) {
                BackStack.presentFragment(MovieDetailsFragment.newInstance(m.getId(), true));
            }

            @Override
            public void onFavouriteClicked(Movie m) {

            }

            @Override
            public void onWatchedClicked(Movie m) {

            }
        }));

        binding.searchEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                clearFocusAndHideKeyboard(binding.searchEditText);
                search();
                return true;
            }
            return false;
        });

        binding.searchEditText.setOnFocusChangeListener((view, b) -> {
            if (binding == null) {
                return;
            }
            if (b) {
                binding.searchResults.setVisibility(View.GONE);
                binding.recentSearches.setVisibility(View.VISIBLE);
                viewModel.getRecentSearches();
            } else {
                binding.recentSearches.setVisibility(View.GONE);
                binding.searchResults.setVisibility(View.VISIBLE);
            }
        });

        binding.searchButton.setOnClickListener(view -> search());

        viewModel.showProgress.observe(getViewLifecycleOwner(), shouldShowProgress -> {
            if (shouldShowProgress) {
                showProgress();
            } else {
                hideProgress();
            }
        });

        viewModel.recentSearches.observe(getViewLifecycleOwner(), this::showRecentSearches);

        viewModel.searchResults.observe(getViewLifecycleOwner(), this::showSearchResults);

        if (viewModel.searchResults.getValue() != null && viewModel.searchResults.getValue().isEmpty()) {
            viewModel.getRecentSearches();
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void showProgress() {
        if (binding != null) {
            binding.progress.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
        if (binding != null) {
            binding.progress.setVisibility(View.GONE);
        }
    }

    public void showRecentSearches(List<Search> searchList) {
        if (binding != null && binding.recentSearches.getAdapter() != null) {
            binding.searchResults.setVisibility(View.GONE);
            binding.recentSearches.setVisibility(View.VISIBLE);
            ((RecentSearchesAdapter) binding.recentSearches.getAdapter()).setSearchList(searchList);
        }
    }

    public void showSearchResults(List<Movie> searchResults) {
        if (binding != null && binding.searchResults.getAdapter() != null) {
            binding.recentSearches.setVisibility(View.GONE);
            binding.searchResults.setVisibility(View.VISIBLE);
            ((MovieAdapter) binding.searchResults.getAdapter()).setMovieList(searchResults);
        }
    }

    public void search() {
        if (binding == null) {
            return;
        }
        String termToSearch = binding.searchEditText.getText().toString();
        if (termToSearch.isEmpty()) {
            Toast.makeText(getContext(), "Please input text to search", Toast.LENGTH_SHORT).show();
            return;
        }
        clearFocusAndHideKeyboard(binding.searchEditText);
        viewModel.startSearch(termToSearch);
    }

    private void clearFocusAndHideKeyboard(View view) {
        if (view != null) {
            view.clearFocus();
            binding.searchParent.requestFocus();
            Utilities.hideKeyboard(view);
        }
    }

}
