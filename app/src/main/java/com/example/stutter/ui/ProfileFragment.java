package com.example.stutter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.stutter.AuthActivity;
import com.example.stutter.MainActivity;
import com.example.stutter.R;
import com.example.stutter.firebase.FirebaseAuthManager;
import com.example.stutter.model.UserProfile;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();
        FirebaseUser user = authManager.getCurrentUser();

        if (user == null) {
            // No user logged in, go back to home
            ((MainActivity) requireActivity()).replace(new HomeFragment(), false);
            return;
        }

        // Get UI elements
        TextView tvUsername = v.findViewById(R.id.tvUsername);
        TextView tvEmail = v.findViewById(R.id.tvEmail);
        TextView tvTotalXP = v.findViewById(R.id.tvTotalXP);
        TextView tvStreak = v.findViewById(R.id.tvStreak);
        TextView tvLessonsCompleted = v.findViewById(R.id.tvLessonsCompleted);
        Button btnLogout = v.findViewById(R.id.btnLogout);
        ProgressBar progressBar = v.findViewById(R.id.progressBar);

        // Show loading
        progressBar.setVisibility(View.VISIBLE);

        // Load user profile from Firestore
        String userId = user.getUid();
        authManager.getUserProfile(userId, new FirebaseAuthManager.OnUserProfileListener() {
            @Override
            public void onSuccess(UserProfile profile) {
                progressBar.setVisibility(View.GONE);
                
                // Display user info
                tvUsername.setText(profile.username);
                tvEmail.setText(profile.email);
                tvTotalXP.setText("Total XP: " + profile.totalXP);
                tvStreak.setText("Streak: " + profile.streak + " days");
                tvLessonsCompleted.setText("Lessons Completed: " + profile.completedLessons);
            }

            @Override
            public void onError(String errorMessage) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Error loading profile: " + errorMessage, Toast.LENGTH_SHORT).show();
                
                // Still show email if we can't load profile
                tvEmail.setText("Email: " + user.getEmail());
            }
        });

        // Logout button
        btnLogout.setOnClickListener(v1 -> {
            // Show confirmation dialog
            new AlertDialog.Builder(requireContext())
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes, Logout", (dialog, which) -> {
                        // Logout
                        authManager.logout();
                        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
                        
                        // Go to AuthActivity
                        Intent intent = new Intent(requireActivity(), AuthActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }
}
