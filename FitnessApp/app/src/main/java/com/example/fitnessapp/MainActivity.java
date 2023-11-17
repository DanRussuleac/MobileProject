package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGoToDashboard = findViewById(R.id.btnGoToDashboard);
        Button btnGoToWorkoutLog = findViewById(R.id.btnGoToWorkoutLog);
        Button btnGoToGoals = findViewById(R.id.btnGoToGoals);
        Button btnGoToHealthScore = findViewById(R.id.btnGoToHealthScore);

        btnGoToDashboard.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
        });

        btnGoToWorkoutLog.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, WorkoutLogActivity.class));
        });

        btnGoToGoals.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, GoalsAchievementsActivity.class));
        });


        btnGoToHealthScore.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HealthScoreActivity.class);
            startActivity(intent);
        });
    }
}
