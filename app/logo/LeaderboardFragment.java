package com.example.stutter.ui;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stutter.R;
import com.example.stutter.firebase.FirebaseAuthManager;
import com.example.stutter.model.UserProfile;
import com.example.stutter.ui.adapter.LeaderboardAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardFragment extends Fragment {

    public LeaderboardFragment() {
        super(R.layout.fragment_leaderboard);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        RecyclerView rvLeaderboard = v.findViewById(R.id.rvLeaderboard);
        ProgressBar pbLoading = v.findViewById(R.id.pbLoading);
        TextView tvCurrentUser = v.findViewById(R.id.tvCurrentUser);
        TextView tvTitle = v.findViewById(R.id.tvTitle);

        rvLeaderboard.setLayoutManager(new LinearLayoutManager(requireContext()));
        pbLoading.setVisibility(View.VISIBLE);

        tvTitle.setText("🏆 Leaderboard");
        tvCurrentUser.setText("Loading your rank...");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();
        FirebaseUser user = authManager.getCurrentUser();

        if (user == null) {
            pbLoading.setVisibility(View.GONE);
            tvCurrentUser.setText("Error: Not logged in");
            return;
        }

        // Load top 10 users by XP
        db.collection("users")
                .orderBy("totalXP", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    pbLoading.setVisibility(View.GONE);
                    
                    if (querySnapshot.isEmpty()) {
                        tvCurrentUser.setText("No players yet - be the first!");
                        Toast.makeText(requireContext(), "No users found yet", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    
                    List<UserProfile> topUsers = new ArrayList<>();
                    int rank = 1;
                    String currentUserId = user.getUid();
                    int currentUserRank = -1;
                    int currentUserXP = 0;
                    
                    for (var doc : querySnapshot.getDocuments()) {
                        try {
                            UserProfile profile = doc.toObject(UserProfile.class);
                            if (profile != null) {
                                topUsers.add(profile);
                                
                                // Track current user
                                if (currentUserId.equals(profile.userId)) {
                                    currentUserRank = rank;
                                    currentUserXP = profile.totalXP;
                                }
                                rank++;
                            }
                        } catch (Exception e) {
                            System.err.println("Error converting user: " + e.getMessage());
                        }
                    }
                    
                    if (topUsers.isEmpty()) {
                        tvCurrentUser.setText("Error loading leaderboard");
                        return;
                    }
                    
                    // Display current user rank
                    if (currentUserRank > 0) {
                        tvCurrentUser.setText("Your Rank: #" + currentUserRank + " with " + currentUserXP + " XP");
                    } else {
                        // Current user not in top 10
                        tvCurrentUser.setText("You're outside top 10 - keep playing!");
                    }
                    
                    rvLeaderboard.setAdapter(new LeaderboardAdapter(topUsers));
                })
                .addOnFailureListener(e -> {
                    pbLoading.setVisibility(View.GONE);
                    String error = e.getMessage() != null ? e.getMessage() : "Unknown error";
                    System.err.println("Leaderboard error: " + error);
                    tvCurrentUser.setText("Error: " + error);
                    Toast.makeText(requireContext(), "Error loading leaderboard: " + error, Toast.LENGTH_LONG).show();
                });
    }
}
