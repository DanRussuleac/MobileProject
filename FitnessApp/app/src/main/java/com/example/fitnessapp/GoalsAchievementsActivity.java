package com.example.fitnessapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.fitnessapp.database.AppDatabase;
import com.example.fitnessapp.database.entities.GoalsLog;

import java.util.List;

public class GoalsAchievementsActivity extends AppCompatActivity {

    private ListView lvGoals;
    private GoalAdapter goalsAdapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_achievements);

        lvGoals = findViewById(R.id.lvGoals);

        // Initialize the database
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "fitnessapp-db")
                .allowMainThreadQueries() // Using main thread queries for simplicity
                .fallbackToDestructiveMigration() // This will reset the database instead of migrating if the version number changes
                .build();

        List<GoalsLog> goals = db.goalsLogDao().getAllGoals();
        goalsAdapter = new GoalAdapter(this, goals, db);
        lvGoals.setAdapter(goalsAdapter);

        Button btnAddGoal = findViewById(R.id.btnAddGoal);
        btnAddGoal.setOnClickListener(view -> showAddGoalDialog());
    }

    private void showAddGoalDialog() {
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a new goal");
        builder.setMessage("What is your new goal?");
        builder.setView(editText);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String goalText = editText.getText().toString().trim();
                if (!goalText.isEmpty()) {
                    addGoal(goalText);
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addGoal(String goalText) {
        GoalsLog newGoal = new GoalsLog(goalText, false);
        db.goalsLogDao().insert(newGoal);
        goalsAdapter.add(newGoal);
        goalsAdapter.notifyDataSetChanged();
    }
}
