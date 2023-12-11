package com.example.fitnessapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * HealthScoreActivity: Activity for calculating and displaying the user's health score.
 */
public class HealthScoreActivity extends AppCompatActivity {

    private TextView tvHealthScore;
    private int steps; // Variable to store the number of steps
    private double distance; // Variable to store the distance covered

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_score);

        tvHealthScore = findViewById(R.id.tvHealthScore);

        // Example values for steps and distance
        steps = 10000; // Example step count
        distance = 5; // Example distance in kilometers

        calculateHealthScore(); // Calculate and display the health score
    }

    private void calculateHealthScore() {
        // Calculate the health score
        // Note: This is a simplified calculation and can be replaced with a more comprehensive algorithm
        int score = (int) ((steps / 10000.0 + distance / 8.0) / 2 * 100);

        // Display the calculated score
        tvHealthScore.setText(String.format("Health Score: %d%%", score));
    }
}
