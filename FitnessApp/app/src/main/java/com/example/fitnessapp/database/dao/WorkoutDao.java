package com.example.fitnessapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.fitnessapp.database.entities.Workout;
import java.util.List;

/**
 * DAO interface for Workout operations. Defines methods for database operations related to the Workout entity.
 */
@Dao
public interface WorkoutDao {
    // Insert a workout into the database. In case of conflict, replace the existing entry.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Workout workout);

    // Update an existing workout in the database.
    @Update
    void update(Workout workout);

    // Delete a workout from the database.
    @Delete
    void delete(Workout workout);

    // Retrieve all workouts stored in the database.
    @Query("SELECT * FROM workout")
    List<Workout> getAllWorkouts();
}
