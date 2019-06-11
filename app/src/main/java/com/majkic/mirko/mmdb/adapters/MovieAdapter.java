package com.majkic.mirko.mmdb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.majkic.mirko.mmdb.Constants;
import com.majkic.mirko.mmdb.R;
import com.majkic.mirko.mmdb.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 07.04.2019.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    private Context context;
    private List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.movie_list_item, viewGroup, false);
        return new MovieViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movie m = movies.get(i);
        movieViewHolder.title.setText(m.getTitle());
        Glide.with(context).load(Constants.BACKEND.TMDB_POSTER_BASE_URL + m.getPosterPath()).into(movieViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovieList(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void appendMovies(List<Movie> nextMovies) {
        this.movies.addAll(nextMovies);
        notifyDataSetChanged();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_image)
        ImageView image;
        @BindView(R.id.movie_title)
        TextView title;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
