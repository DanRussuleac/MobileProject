package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity: The primary activity of the app, serving as a navigation hub to other activities.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons for navigating to different activities
        Button btnGoToDashboard = findViewById(R.id.btnGoToDashboard);
        Button btnGoToWorkoutLog = findViewById(R.id.btnGoToWorkoutLog);
        Button btnGoToGoals = findViewById(R.id.btnGoToGoals);
        Button btnGoToHealthScore = findViewById(R.id.btnGoToHealthScore);

        // Set up listeners for each button to start the corresponding activity
        btnGoToDashboard.setOnClickListener(view -> {
            // Navigate to the DashboardActivity
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
        });

        btnGoToWorkoutLog.setOnClickListener(view -> {
            // Navigate to the WorkoutLogActivity
            startActivity(new Intent(MainActivity.this, WorkoutLogActivity.class));
        });

        btnGoToGoals.setOnClickListener(view -> {
            // Navigate to the GoalsAchievementsActivity
            startActivity(new Intent(MainActivity.this, GoalsAchievementsActivity.class));
        });

        btnGoToHealthScore.setOnClickListener(view -> {
            // Navigate to the HealthScoreActivity
            startActivity(new Intent(MainActivity.this, HealthScoreActivity.class));
        });
    }
}
