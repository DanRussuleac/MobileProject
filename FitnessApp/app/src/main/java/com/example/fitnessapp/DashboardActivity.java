package com.example.fitnessapp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity implements SensorEventListener {

    private TextView tvStepCounter;
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private boolean isSensorPresent;
    private int stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent serviceIntent = new Intent(this, StepCounterService.class);
        startService(serviceIntent);

        tvStepCounter = findViewById(R.id.tvStepCounter);
        Button btnViewWorkoutLog = findViewById(R.id.btnViewWorkoutLog);
        Button btnSetGoals = findViewById(R.id.btnSetGoals);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorPresent = true;
        } else {
            tvStepCounter.setText("Step Counter Sensor not present!");
            isSensorPresent = false;
        }

        btnViewWorkoutLog.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, WorkoutLogActivity.class);
            startActivity(intent);
        });

        btnSetGoals.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, GoalsAchievementsActivity.class);
            startActivity(intent);
        });
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
            tvStepCounter.setText(String.format("Steps: %d", stepCount));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Can be used to respond to changes in sensor accuracy
    }
}
