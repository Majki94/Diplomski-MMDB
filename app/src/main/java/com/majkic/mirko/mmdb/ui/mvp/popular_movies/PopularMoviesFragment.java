package com.majkic.mirko.mmdb.ui.mvp.popular_movies;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majkic.mirko.mmdb.BackStack;
import com.majkic.mirko.mmdb.Constants;
import com.majkic.mirko.mmdb.data.model.Movie;
import com.majkic.mirko.mmdb.databinding.FragmentPopularMoviesBinding;
import com.majkic.mirko.mmdb.ui.adapters.MovieAdapter;
import com.majkic.mirko.mmdb.ui.mvvm.movie_details.MovieDetailsFragment;

import java.util.ArrayList;
import java.util.List;


public class PopularMoviesFragment extends Fragment implements PopularMoviesContract.View {

    private static final String TAG = PopularMoviesFragment.class.getSimpleName();
    private PopularMoviesContract.UserActionsListener mPresenter;
    GridLayoutManager layoutManager;
    private FragmentPopularMoviesBinding binding;

    public PopularMoviesFragment() {
        // Required empty public constructor
    }

    public static PopularMoviesFragment newInstance() {
        PopularMoviesFragment fragment = new PopularMoviesFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPopularMoviesBinding.inflate(inflater, container, false);

        mPresenter = new PopularMoviesPresenter(this, getContext());

        layoutManager = new GridLayoutManager(getContext(), Constants.DEFAULTS.COLUMN_COUNT);
        binding.moviesList.setLayoutManager(layoutManager);
        binding.moviesList.setAdapter(new MovieAdapter(getContext(), new ArrayList<Movie>(), new MovieAdapter.MovieClickListener() {
            @Override
            public void onMovieClicked(Movie m) {
                BackStack.presentFragment(MovieDetailsFragment.newInstance(m.getId()));
            }

            @Override
            public void onFavouriteClicked(Movie m) {
                mPresenter.favouriteChanged(m);
            }

            @Override
            public void onWatchedClicked(Movie m) {
                mPresenter.watchedChanged(m);
            }
        }));
        binding.moviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findLastVisibleItemPosition() == layoutManager.getItemCount() - 1 && binding.progress.getVisibility() == View.GONE) {
                    mPresenter.getNextMovies();
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume: called");
        super.onResume();
        mPresenter.getMovies();
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
        binding = null;
    }

    @Override
    public void showProgress() {
        if (binding != null) {
            binding.progress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (binding != null) {
            binding.progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMovies(List<Movie> movies) {
        if (binding != null && binding.moviesList.getAdapter() != null) {
            if (movies.size() > 0) {
                binding.pleaseCheck.setVisibility(View.GONE);
                binding.moviesList.setVisibility(View.VISIBLE);
                ((MovieAdapter) binding.moviesList.getAdapter()).setMovieList(movies);
            } else {
                binding.moviesList.setVisibility(View.GONE);
                binding.pleaseCheck.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void appendMovies(List<Movie> nextMovies) {
        if (binding != null && binding.moviesList.getAdapter() != null) {
            ((MovieAdapter) binding.moviesList.getAdapter()).appendMovies(nextMovies);
        }
    }

    public void refresh() {
        mPresenter.syncFavouriteAndWatched();
    }

}
