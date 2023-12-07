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

public class DashboardActivity extends AppCompatActivity implements SensorEventListener {

    private static final int REQUEST_ACTIVITY_RECOGNITION_PERMISSION = 1;

    private TextView tvStepCounter;
    private TextView tvStepProgressPercentage;
    private ProgressBar pbStepProgress;
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private boolean isSensorPresent;
    private int stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvStepCounter = findViewById(R.id.tvStepCounter);
        tvStepProgressPercentage = findViewById(R.id.tvStepProgressPercentage);
        pbStepProgress = findViewById(R.id.pbStepProgress);
        Button btnViewWorkoutLog = findViewById(R.id.btnViewWorkoutLog);
        Button btnSetGoals = findViewById(R.id.btnSetGoals);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For Android 10 (API level 29) and above
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, REQUEST_ACTIVITY_RECOGNITION_PERMISSION);
            } else {
                startStepCounterService();
            }
        } else {
            // For Android 9 (Pie, API level 28) and below
            startStepCounterService();
        }

        btnViewWorkoutLog.setOnClickListener(view -> startActivity(new Intent(DashboardActivity.this, WorkoutLogActivity.class)));
        btnSetGoals.setOnClickListener(view -> startActivity(new Intent(DashboardActivity.this, GoalsAchievementsActivity.class)));
    }

    private void startStepCounterService() {
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
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ACTIVITY_RECOGNITION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startStepCounterService();
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
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isSensorPresent) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == stepSensor) {
            stepCount = (int) event.values[0];
            updateStepCountDisplay(stepCount);
        }
    }

    private void updateStepCountDisplay(int steps) {
        tvStepCounter.setText(String.format(Locale.US, "Steps: %d/10,000", steps));
        int stepGoal = 10000;
        pbStepProgress.setProgress(steps);
        float percentage = (steps / (float) stepGoal) * 100;
        tvStepProgressPercentage.setText(String.format(Locale.US, "%.2f%%", percentage));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
