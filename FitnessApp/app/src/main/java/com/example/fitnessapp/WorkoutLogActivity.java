package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.fitnessapp.database.AppDatabase;
import com.example.fitnessapp.database.dao.WorkoutDao;
import com.example.fitnessapp.database.entities.Workout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutLogActivity extends AppCompatActivity {

    private static final int REQUEST_UPDATE_WORKOUT = 1;

    private ListView lvWorkoutLog;
    private Button btnAddWorkout;
    private EditText etMuscle;
    private EditText etExercises;
    private WorkoutAdapter workoutAdapter;
    private WorkoutDao workoutDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_log);

        lvWorkoutLog = findViewById(R.id.lvWorkoutLog);
        btnAddWorkout = findViewById(R.id.btnAddWorkout);
        etMuscle = findViewById(R.id.etMuscle);
        etExercises = findViewById(R.id.etExercises);

        workoutAdapter = new WorkoutAdapter(this, new ArrayList<Workout>());
        lvWorkoutLog.setAdapter(workoutAdapter);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        workoutDao = db.workoutDao();

        lvWorkoutLog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Workout selectedWorkout = workoutAdapter.getItem(position);

                Intent intent = new Intent(WorkoutLogActivity.this, EditWorkoutActivity.class);
                intent.putExtra("WORKOUT_ID", selectedWorkout.getId());
                intent.putExtra("MUSCLE", selectedWorkout.getMuscle());
                intent.putExtra("EXERCISES", selectedWorkout.getExercises());
                startActivityForResult(intent, REQUEST_UPDATE_WORKOUT);
            }
        });

        btnAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String muscle = etMuscle.getText().toString();
                String exercises = etExercises.getText().toString();

                Workout workout = new Workout(muscle, exercises);
                workoutDao.insert(workout);

                loadWorkouts();

                etMuscle.setText("");
                etExercises.setText("");
            }
        });

        loadWorkouts();
    }

    private void loadWorkouts() {
        List<Workout> workouts = workoutDao.getAllWorkouts();
        workoutAdapter.clear();
        workoutAdapter.addAll(workouts);
        workoutAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UPDATE_WORKOUT && resultCode == RESULT_OK) {
            loadWorkouts();
        }
    }
}
