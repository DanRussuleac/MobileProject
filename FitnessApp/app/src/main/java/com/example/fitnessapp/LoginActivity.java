package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.fitnessapp.database.AppDatabase;
import com.example.fitnessapp.database.entities.User;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextRegisterUsername;
    private EditText editTextRegisterPassword;
    private EditText editTextRegisterPasswordConfirm;
    private LinearLayout loginForm, registerForm;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize login form views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Initialize register form views
        editTextRegisterUsername = findViewById(R.id.editTextRegisterUsername);
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        editTextRegisterPasswordConfirm = findViewById(R.id.editTextRegisterPasswordConfirm);
        Button buttonRegister = findViewById(R.id.buttonGoToRegister);
        Button buttonCreateAccount = findViewById(R.id.buttonRegister);
        Button buttonGoToLogin = findViewById(R.id.buttonGoToLogin);

        // Initialize form containers
        loginForm = findViewById(R.id.loginForm);
        registerForm = findViewById(R.id.registerForm);

        // Initialize Room database with migration
        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();


        // Set up register button register button listener
        buttonGoToLogin.setOnClickListener(v -> {
            loginForm.setVisibility(View.VISIBLE);
            registerForm.setVisibility(View.GONE);
        });

        // Set up login button listener
        buttonLogin.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // DB operation for login
            new Thread(() -> {
                User user = db.userDao().getUser(username, password);
                if (user != null) {
                    // User exists, proceed to login
                    runOnUiThread(() -> {
                        // Navigate to the MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Close the LoginActivity
                    });
                } else {
                    // User doesn't exist
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show());
                }
            }).start();
        });

        // Set up register button listener to switch to the registration form
        buttonRegister.setOnClickListener(v -> {
            loginForm.setVisibility(View.GONE);
            registerForm.setVisibility(View.VISIBLE);
        });

        // Set up create account button listener
        buttonCreateAccount.setOnClickListener(v -> {
            // Get values from registration form
            String newUsername = editTextRegisterUsername.getText().toString();
            String newPassword = editTextRegisterPassword.getText().toString();
            String confirmPassword = editTextRegisterPasswordConfirm.getText().toString();

            // Check if the passwords match
            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(LoginActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // DB operation for registration
            new Thread(() -> {
                // Check if user already exists
                User existingUser = db.userDao().getUserByUsername(newUsername);
                if (existingUser == null) {
                    // No existing user, create new account
                    User newUser = new User(newUsername, newPassword);
                    db.userDao().insert(newUser);
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        // Reset form fields
                        editTextRegisterUsername.setText("");
                        editTextRegisterPassword.setText("");
                        editTextRegisterPasswordConfirm.setText("");
                        // Switch back to login form
                        registerForm.setVisibility(View.GONE);
                        loginForm.setVisibility(View.VISIBLE);
                    });
                } else {
                    // User already exists
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "User already exists", Toast.LENGTH_SHORT).show());
                }
            }).start();
        });
    }
}
