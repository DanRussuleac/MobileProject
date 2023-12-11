package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.fitnessapp.database.AppDatabase;
import com.example.fitnessapp.database.dao.WorkoutDao;
import com.example.fitnessapp.database.entities.Workout;
import java.util.concurrent.Executors;

/**
 * EditWorkoutActivity: Activity to edit or delete a workout entry.
 */
public class EditWorkoutActivity extends AppCompatActivity {

    // UI elements
    private EditText etMuscle;
    private EditText etExercises;
    private Button btnSave;
    private Button btnDelete;

    // DAO for accessing the database
    private WorkoutDao workoutDao;

    // Variable to store workout ID
    private int workoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        // Initialize UI components
        etMuscle = findViewById(R.id.etMuscle);
        etExercises = findViewById(R.id.etExercises);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        // Setup database and DAO
        workoutDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build().workoutDao();

        // Retrieve workout details passed from the previous activity
        Intent intent = getIntent();
        workoutId = intent.getIntExtra("WORKOUT_ID", -1);
        String muscle = intent.getStringExtra("MUSCLE");
        String exercises = intent.getStringExtra("EXERCISES");

        // Set retrieved values to EditTexts
        etMuscle.setText(muscle);
        etExercises.setText(exercises);

        // Save button listener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String muscle = etMuscle.getText().toString();
                final String exercises = etExercises.getText().toString();

                // Execute database operation in a background thread
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Workout workout = new Workout(muscle, exercises);
                        workout.setId(workoutId);
                        workoutDao.update(workout);

                        // Update UI on the main thread after operation
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setResult(RESULT_OK);
                                finish(); // Close the activity
                            }
                        });
                    }
                });
            }
        });

        // Delete button listener
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Execute database operation in a background thread
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Workout workout = new Workout("", "");
                        workout.setId(workoutId);
                        workoutDao.delete(workout);

                        // Update UI on the main thread after operation
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setResult(RESULT_OK);
                                finish(); // Close the activity
                            }
                        });
                    }
                });
            }
        });
    }
}
