package com.neomatrix.marvelcharacters.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.neomatrix.marvelcharacters.models.Result;

//@Database(entities = Result.class, version = 1)
public abstract class ComicsDb extends RoomDatabase {

    private static ComicsDb comicsDb;


    public abstract ComicsDao comicsDao();

    public static ComicsDb getInstance(Context context) {
        if (comicsDb == null) {
            comicsDb = Room.databaseBuilder(context.getApplicationContext(), ComicsDb.class, "Comics_Db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return comicsDb;
    }


}