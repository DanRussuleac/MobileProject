package com.example.fitnessapp.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class for Workout. Represents a workout in the database.
 */
@Entity(tableName = "workout")
public class Workout {
    @PrimaryKey(autoGenerate = true)
    private int id; // Unique identifier for each workout.
    private String muscle; // The targeted muscle group of the workout.
    private String exercises; // The exercises included in the workout.

    /**
     * Constructor to create a new Workout object.
     * The targeted muscle group of the workout.
     * The exercises included in the workout.
     */
    public Workout(String muscle, String exercises) {
        this.muscle = muscle;
        this.exercises = exercises;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getExercises() {
        return exercises;
    }

    public void setExercises(String exercises) {
        this.exercises = exercises;
    }
}
