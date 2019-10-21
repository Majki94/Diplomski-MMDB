package com.majkic.mirko.mmdb.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


/**
 * Created by hp on 19.01.2019.
 */

@Entity
public class Movie {
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;
    @ColumnInfo(name = "watched")
    private boolean watched;
    @ColumnInfo(name = "favorite")
    private boolean favorite;

    public Movie() {
    }

    @Ignore
    public Movie(int id, String title, String releaseDate, String overview, String posterPath, boolean watched, boolean favorite) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterPath = posterPath;
        this.watched = watched;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
