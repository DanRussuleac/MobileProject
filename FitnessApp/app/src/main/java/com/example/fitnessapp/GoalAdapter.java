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

public class GoalAdapter extends ArrayAdapter<GoalsLog> {
    private List<GoalsLog> goalsList;
    private Context mContext;
    private AppDatabase db;

    public GoalAdapter(Context context, List<GoalsLog> goals, AppDatabase db) {
        super(context, 0, goals);
        this.mContext = context;
        this.goalsList = goals;
        this.db = db;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_goal, parent, false);
        }

        TextView tvGoalText = convertView.findViewById(R.id.tvGoalText);
        CheckBox chkGoal = convertView.findViewById(R.id.chkGoal);
        ImageView imgDelete = convertView.findViewById(R.id.imgDelete);

        GoalsLog goal = getItem(position);

        if (goal != null) {
            tvGoalText.setText(goal.getGoalText());
            chkGoal.setChecked(goal.isCompleted());

            // Set the checkbox color when checked
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                chkGoal.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.dark_fitness_blue)));
            }

            // Update text color based on completion status
            if (goal.isCompleted()) {
                tvGoalText.setTextColor(Color.GREEN); // For completed goals
            } else {
                tvGoalText.setTextColor(Color.BLACK); // For incomplete goals
            }

            chkGoal.setOnCheckedChangeListener((buttonView, isChecked) -> {
                goal.setCompleted(isChecked);
                new Thread(() -> db.goalsLogDao().update(goal)).start(); // Update in database
                notifyDataSetChanged(); // Refresh the list view
            });

            imgDelete.setOnClickListener(v -> {
                deleteGoal(goal, position);
            });
        }

        return convertView;
    }

    private void deleteGoal(GoalsLog goal, int position) {
        new Thread(() -> {
            db.goalsLogDao().delete(goal);
            goalsList.remove(position);
            ((Activity) mContext).runOnUiThread(this::notifyDataSetChanged);
        }).start();
    }
}
