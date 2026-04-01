package com.example.stutter.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stutter.MainActivity;
import com.example.stutter.R;
import com.example.stutter.firebase.FirebaseAuthManager;
import com.example.stutter.model.UserProfile;
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

        TextView tvStreak = view.findViewById(R.id.tvStreak);
        TextView tvXP     = view.findViewById(R.id.tvXP);
        TextView tvCoins  = view.findViewById(R.id.tvCoins);

        FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();
        FirebaseUser user = authManager.getCurrentUser();

        //Trackables
        if (user != null) {
            authManager.getUserProfile(user.getUid(), new FirebaseAuthManager.OnUserProfileListener() {
                @Override
                public void onSuccess(UserProfile profile) {
                    if (!isAdded()) return;
                    tvStreak.setText("🔥 " + profile.streak);
                    tvXP.setText("⭐ " + profile.totalXP);
                    tvCoins.setText("🪙 " + profile.coins);

                    vm.streak.setValue(profile.streak);
                    vm.totalXP.setValue(profile.totalXP);
                }

                @Override
                public void onError(String errorMessage) {
                    if (!isAdded()) return;
                    tvStreak.setText("🔥 0");
                    tvXP.setText("⭐ 0");
                    tvCoins.setText("🪙 0");
                }
            });
        } else {
            tvStreak.setText("🔥 0");
            tvXP.setText("⭐ 0");
            tvCoins.setText("🪙 0");
        }

        RecyclerView rv = view.findViewById(R.id.rvTopics);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        vm.topics.observe(getViewLifecycleOwner(), topics -> {
            if (topics != null && !topics.isEmpty()) {
                rv.setAdapter(new TopicsAdapter(topics, topicId -> {
                    vm.selectedTopicId.setValue(topicId);
                    ((MainActivity) requireActivity()).replace(new LevelSelectionFragment(), true);
                }));
            }
        });
    }
}
