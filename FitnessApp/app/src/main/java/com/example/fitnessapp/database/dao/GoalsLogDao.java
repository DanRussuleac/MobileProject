package com.example.fitnessapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.fitnessapp.database.entities.GoalsLog;
import java.util.List;

/**
 * DAO interface for GoalsLog operations. Defines methods for database operations.
 */
@Dao
public interface GoalsLogDao {
    @Insert
    void insert(GoalsLog goal);  // Insert a new goal into the database.

    @Update
    void update(GoalsLog goal);  // Update an existing goal.

    @Delete
    void delete(GoalsLog goal);  // Delete a goal from the database.

    @Query("SELECT * FROM goalsLog")
    List<GoalsLog> getAllGoals();  // Retrieve all goals from the database.

    @Query("SELECT * FROM goalsLog WHERE id = :id")
    GoalsLog getGoalById(int id);  // Find a goal by its ID.

    @Query("SELECT * FROM goalsLog WHERE goalText = :text")
    GoalsLog getGoalByText(String text);  // Find a goal by its text.
}
