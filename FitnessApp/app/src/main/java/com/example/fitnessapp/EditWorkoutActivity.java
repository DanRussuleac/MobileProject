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

public class EditWorkoutActivity extends AppCompatActivity {

    private EditText etMuscle;
    private EditText etExercises;
    private Button btnSave;
    private Button btnDelete;
    private WorkoutDao workoutDao;
    private int workoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        etMuscle = findViewById(R.id.etMuscle);
        etExercises = findViewById(R.id.etExercises);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        workoutDao = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build().workoutDao();

        Intent intent = getIntent();
        workoutId = intent.getIntExtra("WORKOUT_ID", -1);
        String muscle = intent.getStringExtra("MUSCLE");
        String exercises = intent.getStringExtra("EXERCISES");

        etMuscle.setText(muscle);
        etExercises.setText(exercises);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String muscle = etMuscle.getText().toString();
                final String exercises = etExercises.getText().toString();

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Workout workout = new Workout(muscle, exercises);
                        workout.setId(workoutId);

                        workoutDao.update(workout);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setResult(RESULT_OK);
                                finish();
                            }
                        });
                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Workout workout = new Workout("", "");
                        workout.setId(workoutId);

                        workoutDao.delete(workout);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setResult(RESULT_OK);
                                finish();
                            }
                        });
                    }
                });
            }
        });
    }
}
