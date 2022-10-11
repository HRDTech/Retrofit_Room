package com.solucioneshr.soft.retrofit_room.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.solucioneshr.soft.retrofit_room.model.DataModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {
        DataModel.class
        },
        version = 1, exportSchema = false)
public abstract class DataDatabase extends RoomDatabase {

    public abstract DataDao dataDao();

    private static final String DATABASE_NAME = "dataDB";

    private static DataDatabase INSTANCE;

    private static final int THREADS = 4;

    public static final ExecutorService dbExecutor = Executors.newFixedThreadPool(THREADS);

    public static DataDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (DataDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(), DataDatabase.class,
                                    DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
