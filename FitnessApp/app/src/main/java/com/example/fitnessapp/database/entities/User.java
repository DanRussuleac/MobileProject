package com.example.fitnessapp.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // No-arg constructor incase it's needed
    public User() {
    }
}


