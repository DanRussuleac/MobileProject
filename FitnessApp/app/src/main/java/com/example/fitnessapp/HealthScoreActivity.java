package com.example.fitnessapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HealthScoreActivity extends AppCompatActivity {

    private TextView tvHealthScore;
    private int steps;
    private double distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_score);

        tvHealthScore = findViewById(R.id.tvHealthScore);

        steps = 10000; // Example step count
        distance = 5; // Example distance in kilometers

        calculateHealthScore();
    }

    private void calculateHealthScore() {
        // Will try use gps and database to calculate healthscore, for no static based on distance etc
        int score = (int) ((steps / 10000.0 + distance / 8.0) / 2 * 100);

        tvHealthScore.setText(String.format("Health Score: %d%%", score));
    }
}
