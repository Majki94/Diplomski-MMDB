package com.majkic.mirko.mmdb.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.majkic.mirko.mmdb.model.Movie;

/**
 * Created by hp on 21.10.2019.
 */
@Database(entities = {Movie.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "movies_database";
    private static volatile MoviesDatabase instance;

    public static synchronized MoviesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MoviesDatabase.class, DATABASE_NAME).build();
        }
        return instance;
    }

}
