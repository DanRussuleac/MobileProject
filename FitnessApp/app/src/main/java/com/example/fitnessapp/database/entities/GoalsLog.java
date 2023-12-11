package com.example.fitnessapp.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing the goals log. Each instance corresponds to a row in the 'goalsLog' table.
 */
@Entity(tableName = "goalsLog")
public class GoalsLog {
    @PrimaryKey(autoGenerate = true)
    private int id; // Unique identifier for each goal.
    private String goalText; // Text description of the goal.
    private boolean isCompleted; // Flag indicating whether the goal is completed.

    /**
     * Constructor to create a new GoalsLog object.
     * goalText The text description of the goal.
     * isCompleted Flag indicating if the goal is completed.
     */
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
