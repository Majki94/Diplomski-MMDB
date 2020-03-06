package com.majkic.mirko.mmdb.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.majkic.mirko.mmdb.model.Search;
import com.majkic.mirko.mmdb.model.dao.SearchDao;

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
