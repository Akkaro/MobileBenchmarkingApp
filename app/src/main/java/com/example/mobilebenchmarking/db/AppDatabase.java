package com.example.mobilebenchmarking.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.mobilebenchmarking.dao.TestResultDao;
import com.example.mobilebenchmarking.models.TestResult;

@Database(entities = {TestResult.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract TestResultDao testResultDao();

    public static synchronized AppDatabase getInstance(Context context) {
        // Delete the database before creating a new instance
        context.deleteDatabase("test_results_database");
        
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "test_results_database")
                    .fallbackToDestructiveMigration() // This will delete the data on version change
                    .build();
        }
        return instance;
    }
}

