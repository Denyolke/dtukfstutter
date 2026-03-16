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
import com.example.stutter.model.Topic;
import com.example.stutter.model.UserProfile;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private ImageView ivProfilePicture;
    private FirebaseAuthManager authManager;
    private FirebaseUser user;
    private int currentPfp = 1;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        authManager = FirebaseAuthManager.getInstance();
        user = authManager.getCurrentUser();
        AppViewModel vm = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        if (user == null) {
            ((MainActivity) requireActivity()).replace(new HomeFragment(), false);
            return;
        }

        ivProfilePicture = v.findViewById(R.id.ivProfilePicture);
        TextView tvUsername = v.findViewById(R.id.tvUsername);
        TextView tvEmail = v.findViewById(R.id.tvEmail);
        TextView tvTotalXP = v.findViewById(R.id.tvTotalXP);
        TextView tvStreak = v.findViewById(R.id.tvStreak);
        TextView tvLessonsCompleted = v.findViewById(R.id.tvLessonsCompleted);
        TextView tvCoursesCompleted = v.findViewById(R.id.tvCoursesCompleted);
        Button btnLogout = v.findViewById(R.id.btnLogout);
        ProgressBar progressBar = v.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

        String userId = user.getUid();
        authManager.getUserProfile(userId, new FirebaseAuthManager.OnUserProfileListener() {
            @Override
            public void onSuccess(UserProfile profile) {
                progressBar.setVisibility(View.GONE);

                tvUsername.setText(profile.username);
                tvEmail.setText(profile.email);
                tvTotalXP.setText("Total XP: " + profile.totalXP);
                tvStreak.setText("Streak: " + profile.streak + " days");
                tvLessonsCompleted.setText("Lessons Completed: " + profile.completedLessons);

                currentPfp = profile.pfp <= 0 ? 1 : profile.pfp;
                ivProfilePicture.setImageResource(getPfpDrawable(currentPfp));
            }

            @Override
            public void onError(String errorMessage) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Error loading profile: " + errorMessage, Toast.LENGTH_SHORT).show();
                tvEmail.setText("Email: " + user.getEmail());
                ivProfilePicture.setImageResource(getPfpDrawable(1));
            }
        });

        ivProfilePicture.setOnClickListener(view -> showProfilePictureDialog());

        vm.topics.observe(getViewLifecycleOwner(), topics -> {
            int completedCourses = 0;

            if (topics != null) {
                for (Topic topic : topics) {
                    if (topic.completed || topic.completedLessons >= 10) {
                        completedCourses++;
                    }
                }
            }

            tvCoursesCompleted.setText("Courses Completed: " + completedCourses);
        });

        btnLogout.setOnClickListener(v1 -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes, Logout", (dialog, which) -> {
                        authManager.logout();
                        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(requireActivity(), AuthActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    private void showProfilePictureDialog() {
        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_profile_picture, null);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle("Choose Profile Picture")
                .setView(dialogView)
                .setNegativeButton("Cancel", (d, which) -> d.dismiss())
                .create();

        dialogView.findViewById(R.id.ivPfp1).setOnClickListener(v -> savePfp(1, dialog));
        dialogView.findViewById(R.id.ivPfp2).setOnClickListener(v -> savePfp(2, dialog));
        dialogView.findViewById(R.id.ivPfp3).setOnClickListener(v -> savePfp(3, dialog));
        dialogView.findViewById(R.id.ivPfp4).setOnClickListener(v -> savePfp(4, dialog));
        dialogView.findViewById(R.id.ivPfp5).setOnClickListener(v -> savePfp(5, dialog));
        dialogView.findViewById(R.id.ivPfp6).setOnClickListener(v -> savePfp(6, dialog));

        dialog.show();
    }

    private void savePfp(int pfp, AlertDialog dialog) {
        if (user == null) return;

        authManager.updateUserProfilePicture(user.getUid(), pfp, new FirebaseAuthManager.OnAuthListener() {
            @Override
            public void onSuccess(String message) {
                currentPfp = pfp;
                ivProfilePicture.setImageResource(getPfpDrawable(pfp));
                Toast.makeText(requireContext(), "Profile picture updated", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(requireContext(), "Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getPfpDrawable(int pfp) {
        switch (pfp) {
            case 1: return R.drawable.pfp_m1;
            case 2: return R.drawable.pfp_m2;
            case 3: return R.drawable.pfp_m3;
            case 4: return R.drawable.pfp_w1;
            case 5: return R.drawable.pfp_w2;
            case 6: return R.drawable.pfp_w3;
            default: return R.drawable.pfp_m1;
        }
    }
}