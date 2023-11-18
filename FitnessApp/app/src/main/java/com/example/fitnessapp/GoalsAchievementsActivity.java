package com.example.fitnessapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class GoalsAchievementsActivity extends AppCompatActivity {

    private ListView lvGoals;
    private ArrayAdapter<String> goalsAdapter;
    private ArrayList<String> goalsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_achievements);

        lvGoals = findViewById(R.id.lvGoals);
        goalsList = new ArrayList<>();
        goalsList.add("Run 5K");
        goalsList.add("15 Minutes Stretching");
        goalsList.add("Drink 2L of Water");

        goalsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, goalsList);
        lvGoals.setAdapter(goalsAdapter);
    }

    // I will have methods here to add new goals, mark goals as achieved, etc.
}
