package com.example.fitnessapp.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class for User. Represents a user in the database.
 */
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid; // Unique identifier for the user.

    @ColumnInfo(name = "username")
    public String username; // Username of the user.

    @ColumnInfo(name = "password")
    public String password; // Password of the user.

    /**
     * Constructor to create a new User object.
     * username The username of the user.
     * password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
