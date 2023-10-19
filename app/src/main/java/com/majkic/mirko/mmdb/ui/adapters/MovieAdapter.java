package com.majkic.mirko.mmdb.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.majkic.mirko.mmdb.Constants;
import com.majkic.mirko.mmdb.Utilities;
import com.majkic.mirko.mmdb.data.model.Movie;
import com.majkic.mirko.mmdb.databinding.MovieListItemBinding;

import java.util.List;

/**
 * Created by hp on 07.04.2019.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    private Context context;
    private List<Movie> movies;
    private MovieClickListener listener;

    public MovieAdapter(Context context, List<Movie> movies, MovieClickListener listener) {
        this.context = context;
        this.movies = movies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        MovieListItemBinding contactView = MovieListItemBinding.inflate(inflater, viewGroup, false);
        return new MovieViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int i) {
        final Movie m = movies.get(i);
        holder.title.setText(m.getTitle());
        Glide.with(context).load(Constants.BACKEND.TMDB_POSTER_BASE_URL + m.getPosterPath()).into(holder.image);
        Utilities.setFavoriteOrWatched(context, holder.favourite, m.isFavorite());
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.setFavorite(!m.isFavorite());
                Utilities.setFavoriteOrWatched(context, ((ImageView) v), m.isFavorite());
                if (listener != null) {
                    listener.onFavouriteClicked(m);
                }
            }
        });
        Utilities.setFavoriteOrWatched(context, holder.watched, m.isWatched());
        holder.watched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.setWatched(!m.isWatched());
                Utilities.setFavoriteOrWatched(context, ((ImageView) v), m.isWatched());
                if (listener != null) {
                    listener.onWatchedClicked(m);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMovieClicked(m);
                }
            }
        });
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

        ImageView image;
        TextView title;
        ImageView favourite;
        ImageView watched;

        public MovieViewHolder(@NonNull MovieListItemBinding binding) {
            super(binding.getRoot());
            image = binding.movieImage;
            title = binding.movieTitle;
            favourite = binding.favourite;
            watched = binding.watched;
        }
    }

    public interface MovieClickListener {
        void onMovieClicked(Movie m);

        void onFavouriteClicked(Movie m);

        void onWatchedClicked(Movie m);
    }

}
