package com.example.fitnessapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HealthScoreActivity extends AppCompatActivity {

    private TextView tvHealthScore;
    private int steps;
    private double distance; // Distance in kilometers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_score);

        tvHealthScore = findViewById(R.id.tvHealthScore);

        // Retrieve steps and distance from intent or shared preferences
        // For demonstration, we'll use static values
        steps = 10000; // Example step count
        distance = 5; // Example distance in kilometers

        calculateHealthScore();
    }

    private void calculateHealthScore() {
        // Placeholder for your health score calculation logic
        // For instance, you might decide that 10,000 steps and 8 km is a 100% score
        int score = (int) ((steps / 10000.0 + distance / 8.0) / 2 * 100);

        tvHealthScore.setText(String.format("Health Score: %d%%", score));
    }
}
