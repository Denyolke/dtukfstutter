package com.example.stutter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
        user        = authManager.getCurrentUser();
        AppViewModel vm = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        if (user == null) {
            ((MainActivity) requireActivity()).replace(new HomeFragment(), false);
            return;
        }

        ivProfilePicture = v.findViewById(R.id.ivProfilePicture);
        TextView    tvUsername         = v.findViewById(R.id.tvUsername);
        TextView    tvEmail            = v.findViewById(R.id.tvEmail);
        TextView    tvTotalXP          = v.findViewById(R.id.tvTotalXP);
        TextView    tvStreak           = v.findViewById(R.id.tvStreak);
        TextView    tvLessonsCompleted = v.findViewById(R.id.tvLessonsCompleted);
        TextView    tvCoursesCompleted = v.findViewById(R.id.tvCoursesCompleted);
        ProgressBar progressBar        = v.findViewById(R.id.progressBar);
        View        btnLogout          = v.findViewById(R.id.btnLogout);
        View        btnOpenSettings    = v.findViewById(R.id.btnOpenSettings);

        // ── Settings button ───────────────────────────────────────────────────
        btnOpenSettings.setOnClickListener(x ->
                ((MainActivity) requireActivity()).replace(new SettingsFragment(), true));

        // ── Load profile ──────────────────────────────────────────────────────
        progressBar.setVisibility(View.VISIBLE);

        authManager.getUserProfile(user.getUid(), new FirebaseAuthManager.OnUserProfileListener() {
            @Override
            public void onSuccess(UserProfile profile) {
                if (!isAdded()) return;
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
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireContext(),
                        "Error loading profile: " + errorMessage, Toast.LENGTH_SHORT).show();
                tvEmail.setText(user.getEmail());
                ivProfilePicture.setImageResource(getPfpDrawable(1));
            }
        });

        // ── Courses completed (from topics LiveData) ──────────────────────────
        vm.topics.observe(getViewLifecycleOwner(), topics -> {
            int completed = 0;
            if (topics != null) {
                for (Topic t : topics) {
                    if (t.completed || t.completedLessons >= 10) completed++;
                }
            }
            tvCoursesCompleted.setText("Courses Completed: " + completed);
        });

        // ── Logout ────────────────────────────────────────────────────────────
        btnLogout.setOnClickListener(x ->
                new AlertDialog.Builder(requireContext())
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes, Logout", (d, w) -> {
                            authManager.logout();
                            Toast.makeText(requireContext(),
                                    "Logged out successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(requireActivity(), AuthActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            requireActivity().finish();
                        })
                        .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                        .show());
    }

    private int getPfpDrawable(int pfp) {
        switch (pfp) {
            case 1:  return R.drawable.pfp_m1;
            case 2:  return R.drawable.pfp_m2;
            case 3:  return R.drawable.pfp_m3;
            case 4:  return R.drawable.pfp_w1;
            case 5:  return R.drawable.pfp_w2;
            case 6:  return R.drawable.pfp_w3;
            default: return R.drawable.pfp_m1;
        }
    }
}
