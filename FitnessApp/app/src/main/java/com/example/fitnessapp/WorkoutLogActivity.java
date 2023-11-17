package com.example.fitnessapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class WorkoutLogActivity extends AppCompatActivity {

    private ListView lvWorkoutLog;
    private Button btnAddWorkout;
    private ArrayList<String> workoutList;
    private ArrayAdapter<String> workoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_log);

        lvWorkoutLog = findViewById(R.id.lvWorkoutLog);
        btnAddWorkout = findViewById(R.id.btnAddWorkout);
        workoutList = new ArrayList<>();
        workoutAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, workoutList);
        lvWorkoutLog.setAdapter(workoutAdapter);

        btnAddWorkout.setOnClickListener(view -> {
            // Intent to start an activity to add a new workout
            // For now, we'll just add a placeholder string to the list
            workoutList.add("Workout " + (workoutList.size() + 1));
            workoutAdapter.notifyDataSetChanged();
        });

        // Load existing workouts from the database or storage
        loadWorkouts();
    }

    private void loadWorkouts() {
        // TODO: Implement loading of workouts from the database or local storage
        // This is where you would query your database and add the items to your 'workoutList', then notify the adapter
    }
}
