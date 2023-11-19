package com.example.fitnessapp.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fitnessapp.database.entities.User;

@Dao
public interface UserDao {
    //Method to query username and password
    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    User getUser(String username, String password);

    @Insert
    void insertAll(User... users);

    // Method to check if a user exists by username
    @Query("SELECT * FROM user WHERE username = :username")
    User getUserByUsername(String username);

    // Method to insert a new user
    @Insert
    void insert(User user);

    // You may also want to add a method to get a user by username and password for login
    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    User getUserByUsernameAndPassword(String username, String password);
}

