package com.example.fitnessapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.fitnessapp.database.AppDatabase;
import com.example.fitnessapp.database.entities.GoalsLog;
import java.util.List;

/**
 * GoalAdapter: Custom ArrayAdapter for displaying and managing goals in a ListView.
 */
public class GoalAdapter extends ArrayAdapter<GoalsLog> {
    private List<GoalsLog> goalsList;
    private Context mContext;
    private AppDatabase db;

    // Constructor
    public GoalAdapter(Context context, List<GoalsLog> goals, AppDatabase db) {
        super(context, 0, goals);
        this.mContext = context;
        this.goalsList = goals;
        this.db = db;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout for each item in the list
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_goal, parent, false);
        }

        // Bind the views from the custom layout
        TextView tvGoalText = convertView.findViewById(R.id.tvGoalText);
        CheckBox chkGoal = convertView.findViewById(R.id.chkGoal);
        ImageView imgDelete = convertView.findViewById(R.id.imgDelete);

        // Get the current goal item
        GoalsLog goal = getItem(position);

        if (goal != null) {
            // Set goal text and completion status
            tvGoalText.setText(goal.getGoalText());
            chkGoal.setChecked(goal.isCompleted());

            // Customize checkbox color on modern Android versions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                chkGoal.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.dark_fitness_blue)));
            }

            // Change text color based on completion status
            tvGoalText.setTextColor(goal.isCompleted() ? Color.GREEN : Color.BLACK);

            // Set listener for checkbox changes
            chkGoal.setOnCheckedChangeListener((buttonView, isChecked) -> {
                goal.setCompleted(isChecked);
                new Thread(() -> db.goalsLogDao().update(goal)).start(); // Update goal in the database
                notifyDataSetChanged(); // Refresh the adapter
            });

            // Set listener for delete button
            imgDelete.setOnClickListener(v -> deleteGoal(goal, position));
        }

        return convertView;
    }

    // Method to delete a goal
    private void deleteGoal(GoalsLog goal, int position) {
        new Thread(() -> {
            db.goalsLogDao().delete(goal); // Delete goal from the database
            goalsList.remove(position); // Remove goal from the list
            ((Activity) mContext).runOnUiThread(this::notifyDataSetChanged); // Notify the UI thread
        }).start();
    }
}
