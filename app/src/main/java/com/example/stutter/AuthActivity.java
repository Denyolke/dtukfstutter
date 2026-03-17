package com.example.stutter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stutter.firebase.FirebaseAuthManager;
import com.example.stutter.util.ThemeManager;
import com.google.android.material.button.MaterialButton;

public class AuthActivity extends AppCompatActivity {

    private EditText       etEmail, etPassword, etConfirmPassword, etUsername;
    private MaterialButton btnAuth;
    private TextView       tvToggleMode;
    private ProgressBar    progressBar;
    private boolean        isLoginMode = true;

    private FirebaseAuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        super.onCreate(savedInstanceState);

        authManager = FirebaseAuthManager.getInstance();

        if (authManager.isUserLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_auth);

        etEmail           = findViewById(R.id.etEmail);
        etPassword        = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etUsername        = findViewById(R.id.etUsername);
        btnAuth           = findViewById(R.id.btnAuth);
        tvToggleMode      = findViewById(R.id.tvToggleMode);
        progressBar       = findViewById(R.id.progressBar);

        btnAuth.setOnClickListener(v -> authenticate());
        tvToggleMode.setOnClickListener(v -> toggleAuthMode());
    }

    // ── Authentication ────────────────────────────────────────────────────────

    private void authenticate() {
        String email    = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String confirm  = etConfirmPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isLoginMode) {
            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
                return;
            }
            if (confirm.isEmpty()) {
                Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirm)) {
                etConfirmPassword.setError("Passwords do not match");
                etConfirmPassword.requestFocus();
                return;
            }
            if (password.length() < 6) {
                etPassword.setError("Password must be at least 6 characters");
                etPassword.requestFocus();
                return;
            }
        }

        setLoading(true);

        if (isLoginMode) {
            authManager.loginUser(email, password, new FirebaseAuthManager.OnAuthListener() {
                @Override
                public void onSuccess(String message) {
                    setLoading(false);
                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onError(String errorMessage) {
                    setLoading(false);
                    Toast.makeText(AuthActivity.this,
                            "Login failed: " + errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            authManager.registerUser(email, password, username, new FirebaseAuthManager.OnAuthListener() {
                @Override
                public void onSuccess(String message) {
                    setLoading(false);
                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onError(String errorMessage) {
                    setLoading(false);
                    Toast.makeText(AuthActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    // ── Toggle login / register ───────────────────────────────────────────────

    private void toggleAuthMode() {
        isLoginMode = !isLoginMode;

        if (isLoginMode) {
            etUsername.setVisibility(View.GONE);
            etConfirmPassword.setVisibility(View.GONE);
            btnAuth.setText("Login");
            tvToggleMode.setText("Don't have an account? Register here");
        } else {
            etUsername.setVisibility(View.VISIBLE);
            etConfirmPassword.setVisibility(View.VISIBLE);
            btnAuth.setText("Register");
            tvToggleMode.setText("Already have an account? Login here");
        }
    }

    // ── Loading state ─────────────────────────────────────────────────────────

    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        btnAuth.setEnabled(!loading);
    }
}