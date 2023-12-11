package com.example.fitnessapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;

/**
 * StepCounterService: A foreground service that counts steps using the device's step counter sensor.
 */
public class StepCounterService extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private int totalStepsSinceReboot;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // Initialize step counter sensor
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
            startForegroundService();
        }
    }

    /**
     * Starts the foreground service with a persistent notification.
     */
    private void startForegroundService() {
        String channelId = "step_counter_service_channel";
        String channelName = "Step Counter Service";
        // Create a notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        // Create a notification for this service
        Intent notificationIntent = new Intent(this, DashboardActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(this, channelId)
                .setContentTitle("Step Counter Active")
                .setContentText("Counting your steps.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Make the service sticky so it continues running
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Not used, as this is not a bound service
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Handle step counter sensor changes
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            totalStepsSinceReboot = (int) event.values[0];
            saveSteps(totalStepsSinceReboot);
        }
    }

    /**
     * Saves the current step count to SharedPreferences.
     * steps The current step count.
     */
    private void saveSteps(int steps) {
        SharedPreferences prefs = getSharedPreferences("StepCounterPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("steps", steps);
        editor.apply();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Can handle changes in sensor accuracy if needed
    }

    @Override
    public void onDestroy() {
        // Unregister the sensor listener when the service is destroyed
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
}
