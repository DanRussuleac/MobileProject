package com.example.fitnessapp.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "goalsLog")
public class GoalsLog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String goalText;
    private boolean isCompleted;

    // Constructor
    public GoalsLog(String goalText, boolean isCompleted) {
        this.goalText = goalText;
        this.isCompleted = isCompleted;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoalText() {
        return goalText;
    }

    public void setGoalText(String goalText) {
        this.goalText = goalText;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
