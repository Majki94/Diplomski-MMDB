package com.majkic.mirko.mmdb.data.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.majkic.mirko.mmdb.data.model.Search;

import java.util.List;

@Dao
public interface SearchDao {
    @Query("SELECT * FROM search ORDER BY search_time DESC")
    List<Search> getAll();

    @Insert
    void insert(Search... searches);

    @Update
    void update(Search... searches);

    @Delete
    void delete(Search search);
}
