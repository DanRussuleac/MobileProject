package com.example.fitnessapp.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout")
public class Workout {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String muscle;
    private String exercises;

    public Workout(String muscle, String exercises) {
        this.muscle = muscle;
        this.exercises = exercises;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getMuscle() {
        return muscle;
    }

    public String getExercises() {
        return exercises;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public void setExercises(String exercises) {
        this.exercises = exercises;
    }
}
