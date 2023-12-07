package com.example.fitnessapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.fitnessapp.database.dao.GoalsLogDao;
import com.example.fitnessapp.database.dao.UserDao;
import com.example.fitnessapp.database.dao.WorkoutDao;
import com.example.fitnessapp.database.entities.GoalsLog;
import com.example.fitnessapp.database.entities.User;
import com.example.fitnessapp.database.entities.Workout;

@Database(entities = {User.class, Workout.class, GoalsLog.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract WorkoutDao workoutDao();
    public abstract GoalsLogDao goalsLogDao();
}
