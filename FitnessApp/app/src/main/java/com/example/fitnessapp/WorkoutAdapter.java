package com.example.fitnessapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fitnessapp.database.entities.Workout;

import java.util.List;

public class WorkoutAdapter extends ArrayAdapter<Workout> {

    private Context context;
    private List<Workout> workouts;

    public WorkoutAdapter(Context context, List<Workout> workouts) {
        super(context, 0, workouts);
        this.context = context;
        this.workouts = workouts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        // Get the data item for this position
        Workout workout = getItem(position);

        // Lookup view for data population
        TextView tvWorkout = convertView.findViewById(android.R.id.text1);

        // Populate the data into the template view using the data object
        tvWorkout.setText(workout.getMuscle() + " - " + workout.getExercises());

        // Return the completed view to render on screen
        return convertView;
    }
}

