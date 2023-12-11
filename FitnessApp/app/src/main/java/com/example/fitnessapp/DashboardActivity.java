package com.example.fitnessapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.Locale;

/**
 * DashboardActivity: Displays the user's step count and other fitness metrics.
 * Implements SensorEventListener to receive step count updates.
 */
public class DashboardActivity extends AppCompatActivity implements SensorEventListener {

    private static final int REQUEST_ACTIVITY_RECOGNITION_PERMISSION = 1;

    // UI elements and sensor variables
    private TextView tvStepCounter, tvStepProgressPercentage;
    private ProgressBar pbStepProgress;
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private boolean isSensorPresent;
    private int stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize UI components
        tvStepCounter = findViewById(R.id.tvStepCounter);
        tvStepProgressPercentage = findViewById(R.id.tvStepProgressPercentage);
        pbStepProgress = findViewById(R.id.pbStepProgress);
        Button btnViewWorkoutLog = findViewById(R.id.btnViewWorkoutLog);
        Button btnSetGoals = findViewById(R.id.btnSetGoals);

        // Request permissions and start step counter service
        checkAndRequestPermissions();
        setupButtonListeners(btnViewWorkoutLog, btnSetGoals);
    }

    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Request permissions for step counter for Android 10 and above
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, REQUEST_ACTIVITY_RECOGNITION_PERMISSION);
            } else {
                startStepCounterService();
            }
        } else {
            // For older Android versions, start step counter service directly
            startStepCounterService();
        }
    }

    private void setupButtonListeners(Button btnViewWorkoutLog, Button btnSetGoals) {
        // Set up listeners for buttons to navigate to other activities
        btnViewWorkoutLog.setOnClickListener(view -> startActivity(new Intent(DashboardActivity.this, WorkoutLogActivity.class)));
        btnSetGoals.setOnClickListener(view -> startActivity(new Intent(DashboardActivity.this, GoalsAchievementsActivity.class)));
    }

    private void startStepCounterService() {
        // Initialize step counter sensor and start the service
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorPresent = true;
            startService(new Intent(this, StepCounterService.class));
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            tvStepCounter.setText("Step Counter Sensor not present!");
            isSensorPresent = false;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Handle the result of the permission request
        if (requestCode == REQUEST_ACTIVITY_RECOGNITION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startStepCounterService(); // Start step counter if permission granted
            } else {
                tvStepCounter.setText("Step Counter Permission not granted!");
                isSensorPresent = false;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isSensorPresent) {
            // Re-register listener when the activity resumes
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isSensorPresent) {
            // Unregister listener to save resources when the activity is paused
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Respond to sensor events
        if (event.sensor == stepSensor) {
            stepCount = (int) event.values[0];
            updateStepCountDisplay(stepCount); // Update the display with the new step count
        }
    }

    private void updateStepCountDisplay(int steps) {
        // Update the UI to show the current step count and progress
        tvStepCounter.setText(String.format(Locale.US, "Steps: %d/10,000", steps));
        int stepGoal = 10000;
        pbStepProgress.setProgress(steps); // Update progress bar
        float percentage = (steps / (float) stepGoal) * 100;
        tvStepProgressPercentage.setText(String.format(Locale.US, "%.2f%%", percentage)); // Show progress percentage
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Can be used to respond to changes in sensor accuracy, if needed
    }
}
