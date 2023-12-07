package com.example.fitnessapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessapp.database.entities.GoalsLog;

import java.util.List;

@Dao
public interface GoalsLogDao {
    @Insert
    void insert(GoalsLog goal);

    @Update
    void update(GoalsLog goal);

    @Delete
    void delete(GoalsLog goal);

    @Query("SELECT * FROM goalsLog")
    List<GoalsLog> getAllGoals();

    @Query("SELECT * FROM goalsLog WHERE id = :id")
    GoalsLog getGoalById(int id);

    @Query("SELECT * FROM goalsLog WHERE goalText = :text")
    GoalsLog getGoalByText(String text);
}
