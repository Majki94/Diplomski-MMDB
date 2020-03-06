package com.majkic.mirko.mmdb.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.majkic.mirko.mmdb.model.Search;

import java.util.List;

@Dao
public interface SearchDao {
    @Query("SELECT * FROM search ORDER BY search_time DESC")
    List<Search> getAll();

    @Insert
    void insert(Search... movies);

    @Update
    void update(Search... movies);

    @Delete
    void delete(Search movie);
}
