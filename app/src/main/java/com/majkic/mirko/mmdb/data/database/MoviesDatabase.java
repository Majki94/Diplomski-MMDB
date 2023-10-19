package com.majkic.mirko.mmdb.data.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.majkic.mirko.mmdb.data.model.Movie;
import com.majkic.mirko.mmdb.data.model.dao.MovieDao;

/**
 * Created by hp on 21.10.2019.
 */
@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "movies_database";
    private static volatile MoviesDatabase instance;

    public static synchronized MoviesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MoviesDatabase.class, DATABASE_NAME).build();
        }
        return instance;
    }

    public abstract MovieDao movieDao();

}
