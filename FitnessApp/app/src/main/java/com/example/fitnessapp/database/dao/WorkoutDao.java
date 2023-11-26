package com.example.fitnessapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fitnessapp.database.entities.Workout;

import java.util.List;

@Dao
public interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Workout workout);

    @Update
    void update(Workout workout);

    @Delete
    void delete(Workout workout);

    @Query("SELECT * FROM workout")
    List<Workout> getAllWorkouts();
}
