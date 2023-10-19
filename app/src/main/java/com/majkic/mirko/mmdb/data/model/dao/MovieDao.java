package com.majkic.mirko.mmdb.data.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.majkic.mirko.mmdb.data.model.Movie;

import java.util.List;


/**
 * Created by hp on 21.10.2019.
 */

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Insert
    void insert(Movie... movies);

    @Update
    void update(Movie... movies);

    @Delete
    void delete(Movie movie);
}
