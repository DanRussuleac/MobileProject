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

/**
 * LoginActivity: Activity for handling user login and registration.
 */
public class LoginActivity extends AppCompatActivity {

    // UI elements for login and registration forms
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextRegisterUsername;
    private EditText editTextRegisterPassword;
    private EditText editTextRegisterPasswordConfirm;
    private LinearLayout loginForm, registerForm;

    // Room database instance
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements for login
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Initialize UI elements for registration
        editTextRegisterUsername = findViewById(R.id.editTextRegisterUsername);
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        editTextRegisterPasswordConfirm = findViewById(R.id.editTextRegisterPasswordConfirm);
        Button buttonRegister = findViewById(R.id.buttonGoToRegister);
        Button buttonCreateAccount = findViewById(R.id.buttonRegister);
        Button buttonGoToLogin = findViewById(R.id.buttonGoToLogin);

        // Initialize form containers
        loginForm = findViewById(R.id.loginForm);
        registerForm = findViewById(R.id.registerForm);

        // Setup database
        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        // Listener to switch to the login form
        buttonGoToLogin.setOnClickListener(v -> {
            loginForm.setVisibility(View.VISIBLE);
            registerForm.setVisibility(View.GONE);
        });

        // Listener for login button
        buttonLogin.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // Background thread for database operation
            new Thread(() -> {
                User user = db.userDao().getUser(username, password);
                if (user != null) {
                    // Successful login
                    runOnUiThread(() -> {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    });
                } else {
                    // Login failed
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show());
                }
            }).start();
        });

        // Listener to switch to the registration form
        buttonRegister.setOnClickListener(v -> {
            loginForm.setVisibility(View.GONE);
            registerForm.setVisibility(View.VISIBLE);
        });

        // Listener for account creation button
        buttonCreateAccount.setOnClickListener(v -> {
            String newUsername = editTextRegisterUsername.getText().toString();
            String newPassword = editTextRegisterPassword.getText().toString();
            String confirmPassword = editTextRegisterPasswordConfirm.getText().toString();

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(LoginActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Background thread for database operation
            new Thread(() -> {
                User existingUser = db.userDao().getUserByUsername(newUsername);
                if (existingUser == null) {
                    // No existing user, create new account
                    User newUser = new User(newUsername, newPassword);
                    db.userDao().insert(newUser);
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        // Reset form fields and switch back to login form
                        editTextRegisterUsername.setText("");
                        editTextRegisterPassword.setText("");
                        editTextRegisterPasswordConfirm.setText("");
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
