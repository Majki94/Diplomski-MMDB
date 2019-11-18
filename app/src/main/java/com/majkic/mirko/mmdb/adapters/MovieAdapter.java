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
import com.majkic.mirko.mmdb.Utilities;
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
        View contactView = inflater.inflate(R.layout.movie_list_item, viewGroup, false);
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
                if (listener != null){
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

        @BindView(R.id.movie_image)
        ImageView image;
        @BindView(R.id.movie_title)
        TextView title;
        @BindView(R.id.favourite)
        ImageView favourite;
        @BindView(R.id.watched)
        ImageView watched;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface MovieClickListener {
        void onMovieClicked(Movie m);

        void onFavouriteClicked(Movie m);

        void onWatchedClicked(Movie m);
    }

}
