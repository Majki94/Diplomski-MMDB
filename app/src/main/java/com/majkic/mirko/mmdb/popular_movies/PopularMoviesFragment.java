package com.majkic.mirko.mmdb.popular_movies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.majkic.mirko.mmdb.BackStack;
import com.majkic.mirko.mmdb.Constants;
import com.majkic.mirko.mmdb.R;
import com.majkic.mirko.mmdb.adapters.MovieAdapter;
import com.majkic.mirko.mmdb.model.Movie;
import com.majkic.mirko.mmdb.movie_details.MovieDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PopularMoviesFragment extends Fragment implements PopularMoviesContract.View {

    private static final String TAG = PopularMoviesFragment.class.getSimpleName();
    private PopularMoviesContract.UserActionsListener mPresenter;
    GridLayoutManager layoutManager;
    @BindView(R.id.movies_list)
    RecyclerView movieListView;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.please_check)
    TextView pleaseCheck;
    private Unbinder unbinder;

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
        View root = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        unbinder = ButterKnife.bind(this, root);

        mPresenter = new PopularMoviesPresenter(this, getContext());

        layoutManager = new GridLayoutManager(getContext(), Constants.DEFAULTS.COLUMN_COUNT);
        movieListView.setLayoutManager(layoutManager);
        movieListView.setAdapter(new MovieAdapter(getContext(), new ArrayList<Movie>(), new MovieAdapter.MovieClickListener() {
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
        movieListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findLastVisibleItemPosition() == layoutManager.getItemCount() - 1 && progress.getVisibility() == View.GONE) {
                    mPresenter.getNextMovies();
                }
            }
        });

        return root;
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
        unbinder.unbind();
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
    public void showMovies(List<Movie> movies) {
        if (movieListView != null && movieListView.getAdapter() != null && pleaseCheck != null) {
            if (movies.size() > 0) {
                pleaseCheck.setVisibility(View.GONE);
                movieListView.setVisibility(View.VISIBLE);
                ((MovieAdapter) movieListView.getAdapter()).setMovieList(movies);
            } else {
                movieListView.setVisibility(View.GONE);
                pleaseCheck.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void appendMovies(List<Movie> nextMovies) {
        if (movieListView != null && movieListView.getAdapter() != null) {
            ((MovieAdapter) movieListView.getAdapter()).appendMovies(nextMovies);
        }
    }

    public void refresh() {
        mPresenter.syncFavouriteAndWatched();
    }

}
