package com.example.stutter.ui;

import android.os.Bundle;
import android.view.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stutter.MainActivity;
import com.example.stutter.R;
import com.example.stutter.firebase.FirebaseAuthManager;
import com.example.stutter.ui.adapter.TopicsAdapter;
import com.google.firebase.auth.FirebaseUser;

import android.widget.TextView;


public class HomeFragment extends Fragment {

    private AppViewModel vm;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vm = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        // Get streak and XP TextViews
        TextView tvStreak = view.findViewById(R.id.tvStreak);
        TextView tvXP = view.findViewById(R.id.tvXP);

        // Load user profile from Firestore to get real XP and streak
        FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();
        FirebaseUser user = authManager.getCurrentUser();

        if (user != null) {
            authManager.getUserProfile(user.getUid(), new FirebaseAuthManager.OnUserProfileListener() {
                @Override
                public void onSuccess(com.example.stutter.model.UserProfile profile) {
                    // Update UI with real data from Firestore
                    tvStreak.setText("🔥 " + profile.streak);
                    tvXP.setText("⭐ " + profile.totalXP);
                    
                    // Also update ViewModel
                    vm.streak.setValue(profile.streak);
                    vm.totalXP.setValue(profile.totalXP);
                }

                @Override
                public void onError(String errorMessage) {
                    // If error, show 0
                    tvStreak.setText("🔥 0");
                    tvXP.setText("⭐ 0");
                }
            });
        } else {
            // Not logged in, show 0
            tvStreak.setText("🔥 0");
            tvXP.setText("⭐ 0");
        }

        // Setup RecyclerView
        RecyclerView rv = view.findViewById(R.id.rvTopics);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Observe topics and set adapter
        vm.topics.observe(getViewLifecycleOwner(), topics -> {
            if (topics != null && !topics.isEmpty()) {
                // Navigate to LEVEL SELECTION instead of quiz
                rv.setAdapter(new TopicsAdapter(topics, topicId -> {
                    vm.selectedTopicId.setValue(topicId);
                    ((MainActivity) requireActivity()).replace(new LevelSelectionFragment(), true);
                }));
            }
        });
    }
}
