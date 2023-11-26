package com.example.fitnessapp.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.fitnessapp.database.dao.UserDao;
import com.example.fitnessapp.database.dao.WorkoutDao;
import com.example.fitnessapp.database.entities.User;
import com.example.fitnessapp.database.entities.Workout;

@Database(entities = {User.class, Workout.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract WorkoutDao workoutDao();

    // Define the migration from version 4 to 5
    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Drop the old 'workout' table
            database.execSQL("DROP TABLE IF EXISTS `workout`");

            // Create the new 'workout' table without the 'userId' column and foreign key
            database.execSQL("CREATE TABLE IF NOT EXISTS `workout` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`muscle` TEXT, " +
                    "`exercises` TEXT)");
        }
    };
}
