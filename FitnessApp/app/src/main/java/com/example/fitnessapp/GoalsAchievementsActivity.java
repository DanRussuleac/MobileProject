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

/**
 * GoalsAchievementsActivity: Activity for managing and displaying user goals.
 */
public class GoalsAchievementsActivity extends AppCompatActivity {

    private ListView lvGoals;
    private GoalAdapter goalsAdapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_achievements);

        lvGoals = findViewById(R.id.lvGoals);

        // Initialize the Room database
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "fitnessapp-db")
                .allowMainThreadQueries() // For simplicity, allowing main thread queries
                .fallbackToDestructiveMigration() // Resets database on version change
                .build();

        // Retrieve goals from the database and set up the ListView with an adapter
        List<GoalsLog> goals = db.goalsLogDao().getAllGoals();
        goalsAdapter = new GoalAdapter(this, goals, db);
        lvGoals.setAdapter(goalsAdapter);

        // Button to add new goals
        Button btnAddGoal = findViewById(R.id.btnAddGoal);
        btnAddGoal.setOnClickListener(view -> showAddGoalDialog());
    }

    /**
     * Displays a dialog to input and add a new goal.
     */
    private void showAddGoalDialog() {
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a new goal")
                .setMessage("What is your new goal?")
                .setView(editText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String goalText = editText.getText().toString().trim();
                        if (!goalText.isEmpty()) {
                            addGoal(goalText);
                        }
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Adds a new goal to the database and updates the ListView.
     * @param goalText The text of the new goal.
     */
    private void addGoal(String goalText) {
        GoalsLog newGoal = new GoalsLog(goalText, false);
        db.goalsLogDao().insert(newGoal);
        goalsAdapter.add(newGoal);
        goalsAdapter.notifyDataSetChanged();
    }
}
