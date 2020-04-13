package com.majkic.mirko.mmdb.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.majkic.mirko.mmdb.BackStack;
import com.majkic.mirko.mmdb.R;
import com.majkic.mirko.mmdb.Utilities;
import com.majkic.mirko.mmdb.adapters.MovieAdapter;
import com.majkic.mirko.mmdb.adapters.RecentSearchesAdapter;
import com.majkic.mirko.mmdb.model.Movie;
import com.majkic.mirko.mmdb.model.Search;
import com.majkic.mirko.mmdb.movie_details.MovieDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearchFragment extends Fragment implements SearchContract.View {

    private SearchContract.UserActionsListener mPresenter;
    private Unbinder unbinder;

    @BindView(R.id.search_parent)
    LinearLayout searchParent;
    @BindView(R.id.search_edit_text)
    EditText search;
    @BindView(R.id.search_button)
    FrameLayout searchButton;
    @BindView(R.id.recent_searches)
    RecyclerView recentSearchesList;
    @BindView(R.id.search_results)
    RecyclerView searchResultsList;
    @BindView(R.id.progress)
    ProgressBar progress;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, root);

        mPresenter = new SearchPresenter(getContext(), this);

        recentSearchesList.setLayoutManager(new LinearLayoutManager(getContext()));
        recentSearchesList.setAdapter(new RecentSearchesAdapter(getContext(), new ArrayList<Search>(), new RecentSearchesAdapter.ItemClickedListener() {
            @Override
            public void onSearchClicked(Search searchInRecent) {
                search.setText(searchInRecent.getSearchTerm());
                clearFocusAndHideKeyboard(search);
                mPresenter.startSearch(searchInRecent.getSearchTerm());
            }
        }));

        searchResultsList.setLayoutManager(new LinearLayoutManager(getContext()));
        searchResultsList.setAdapter(new MovieAdapter(getContext(), new ArrayList<Movie>(), new MovieAdapter.MovieClickListener() {
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

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    clearFocusAndHideKeyboard(search);
                    search();
                    return true;
                }
                return false;
            }
        });

        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (searchResultsList != null && recentSearchesList != null) {
                    if (b) {
                        searchResultsList.setVisibility(View.GONE);
                        recentSearchesList.setVisibility(View.VISIBLE);
                    } else {
                        recentSearchesList.setVisibility(View.GONE);
                        searchResultsList.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getRecentSearches();
    }

    @Override
    public void showProgress() {
        if (progress != null) {
            progress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (progress != null) {
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showRecentSearches(List<Search> searchList) {
        if (searchResultsList != null && recentSearchesList != null && recentSearchesList.getAdapter() != null) {
            searchResultsList.setVisibility(View.GONE);
            recentSearchesList.setVisibility(View.VISIBLE);
            ((RecentSearchesAdapter) recentSearchesList.getAdapter()).setSearchList(searchList);
        }
    }

    @Override
    public void showSearchResults(List<Movie> searchResults) {
        if (recentSearchesList != null && searchResultsList != null && searchResultsList.getAdapter() != null) {
            recentSearchesList.setVisibility(View.GONE);
            searchResultsList.setVisibility(View.VISIBLE);
            ((MovieAdapter) searchResultsList.getAdapter()).setMovieList(searchResults);
        }
    }

    @OnClick(R.id.search_button)
    public void search() {
        String termToSearch = search.getText().toString();
        if (termToSearch.isEmpty()) {
            Toast.makeText(getContext(), "Please input text to search", Toast.LENGTH_SHORT).show();
            return;
        }
        clearFocusAndHideKeyboard(search);
        mPresenter.startSearch(termToSearch);
    }

    private void clearFocusAndHideKeyboard(View view) {
        if (view != null) {
            view.clearFocus();
            searchParent.requestFocus();
            Utilities.hideKeyboard(view);
        }
    }

}
