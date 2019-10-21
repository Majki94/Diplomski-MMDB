package com.majkic.mirko.mmdb.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.majkic.mirko.mmdb.model.Movie;

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
    void delete(Movie user);
}
