package com.example.fitnessapp.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.fitnessapp.database.entities.User;

/**
 * DAO interface for User operations. Defines methods for database operations related to User entity.
 */
@Dao
public interface UserDao {

    // Retrieve a user by username and password (for login verification).
    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    User getUser(String username, String password);

    // Insert multiple users into the database.
    @Insert
    void insertAll(User... users);

    // Retrieve a user by their username.
    @Query("SELECT * FROM user WHERE username = :username")
    User getUserByUsername(String username);

    // Insert a single user into the database.
    @Insert
    void insert(User user);

}
