package com.example.fitnessapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.fitnessapp.database.dao.UserDao;
import com.example.fitnessapp.database.entities.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}

