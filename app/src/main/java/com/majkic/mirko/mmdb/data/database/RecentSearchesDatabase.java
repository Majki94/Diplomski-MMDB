package com.majkic.mirko.mmdb.data.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.majkic.mirko.mmdb.data.model.Search;
import com.majkic.mirko.mmdb.data.model.dao.SearchDao;

@Database(entities = {Search.class}, version = 1, exportSchema = false)
public abstract class RecentSearchesDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "recent_searches_database";
    private static volatile RecentSearchesDatabase instance;

    public static synchronized RecentSearchesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, RecentSearchesDatabase.class, DATABASE_NAME).build();
        }
        return instance;
    }

    public abstract SearchDao searchDao();

}
