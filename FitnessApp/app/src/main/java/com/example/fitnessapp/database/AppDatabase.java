package com.example.fitnessapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.fitnessapp.database.dao.GoalsLogDao;
import com.example.fitnessapp.database.dao.UserDao;
import com.example.fitnessapp.database.dao.WorkoutDao;
import com.example.fitnessapp.database.entities.GoalsLog;
import com.example.fitnessapp.database.entities.User;
import com.example.fitnessapp.database.entities.Workout;

/**
 * Defines the database configuration and serves as the app's main access point to the persisted data.
 * */
@Database(entities = {User.class, Workout.class, GoalsLog.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    // DAOs to handle database operations for each entity
    public abstract UserDao userDao();
    public abstract WorkoutDao workoutDao();
    public abstract GoalsLogDao goalsLogDao();
}
