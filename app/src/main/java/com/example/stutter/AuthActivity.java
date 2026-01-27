package com.example.stutter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stutter.firebase.FirebaseAuthManager;

public class AuthActivity extends AppCompatActivity {

    private EditText etEmail, etPassword, etUsername;
    private Button btnAuth;
    private TextView tvToggleMode;
    private ProgressBar progressBar;
    private boolean isLoginMode = true;

    private FirebaseAuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authManager = FirebaseAuthManager.getInstance();

        // Check if user is already logged in
        if (authManager.isUserLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        btnAuth = findViewById(R.id.btnAuth);
        tvToggleMode = findViewById(R.id.tvToggleMode);
        progressBar = findViewById(R.id.progressBar);

        btnAuth.setOnClickListener(v -> authenticate());

        tvToggleMode.setOnClickListener(v -> toggleAuthMode());
    }

    private void authenticate() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String username = etUsername.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isLoginMode && username.isEmpty()) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(android.view.View.VISIBLE);
        btnAuth.setEnabled(false);

        if (isLoginMode) {
            authManager.loginUser(email, password, new FirebaseAuthManager.OnAuthListener() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(AuthActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(android.view.View.GONE);
                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onError(String errorMessage) {
                    Toast.makeText(AuthActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(android.view.View.GONE);
                    btnAuth.setEnabled(true);
                }
            });
        } else {
            authManager.registerUser(email, password, username, new FirebaseAuthManager.OnAuthListener() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(AuthActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(android.view.View.GONE);
                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onError(String errorMessage) {
                    Toast.makeText(AuthActivity.this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(android.view.View.GONE);
                    btnAuth.setEnabled(true);
                }
            });
        }
    }

    private void toggleAuthMode() {
        isLoginMode = !isLoginMode;

        if (isLoginMode) {
            etUsername.setVisibility(android.view.View.GONE);
            btnAuth.setText("Login");
            tvToggleMode.setText("Don't have an account? Register here");
        } else {
            etUsername.setVisibility(android.view.View.VISIBLE);
            btnAuth.setText("Register");
            tvToggleMode.setText("Already have an account? Login here");
        }
    }
}