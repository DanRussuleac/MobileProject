package com.example.fitnessapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.fitnessapp.database.entities.Workout;
import java.util.List;

/**
 * WorkoutAdapter: Custom ArrayAdapter to display workout items in a ListView.
 */
public class WorkoutAdapter extends ArrayAdapter<Workout> {

    private Context context;
    private List<Workout> workouts;

    // Constructor
    public WorkoutAdapter(Context context, List<Workout> workouts) {
        super(context, 0, workouts);
        this.context = context;
        this.workouts = workouts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate a new view or reuse an existing one
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        // Get the workout item at the current position
        Workout workout = getItem(position);

        // Find the TextView in the inflated layout
        TextView tvWorkout = convertView.findViewById(android.R.id.text1);

        // Set the text to display the workout details
        tvWorkout.setText(workout.getMuscle() + " - " + workout.getExercises());

        // Return the completed view to be displayed
        return convertView;
    }
}
