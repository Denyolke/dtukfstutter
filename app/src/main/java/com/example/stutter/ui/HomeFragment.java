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
import com.example.stutter.ui.adapter.TopicsAdapter;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class HomeFragment extends Fragment {

    private AppViewModel vm;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        vm = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        // Setup RecyclerView
        RecyclerView rv = view.findViewById(R.id.rvTopics);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Observe topics and set adapter
        vm.topics.observe(getViewLifecycleOwner(), topics -> {
            if (topics != null && !topics.isEmpty()) {
                rv.setAdapter(new TopicsAdapter(topics, topicId -> {
                    vm.selectedTopicId.setValue(topicId);
                    ((MainActivity) requireActivity()).replace(new QuizFragment(), true);
                }));
            }
        });

        // Update header stats
        TextView tvStreak = view.findViewById(R.id.tvStreak);
        TextView tvXP = view.findViewById(R.id.tvXP);

        vm.streak.observe(getViewLifecycleOwner(), streak -> {
            if (streak != null) {
                tvStreak.setText("🔥 " + streak);
            }
        });

        vm.totalXP.observe(getViewLifecycleOwner(), xp -> {
            if (xp != null) {
                tvXP.setText("⭐ " + xp);
            }
        });

        // Wire bottom nav buttons - find them from parent activity
        View parentView = getActivity().getWindow().getDecorView();
        
        LinearLayout btnSettingsContainer = parentView.findViewById(R.id.btnSettingsContainer);
        LinearLayout btnHomeContainer = parentView.findViewById(R.id.btnHomeContainer);
        LinearLayout btnProfileContainer = parentView.findViewById(R.id.btnProfileContainer);

        if (btnSettingsContainer != null) {
            btnSettingsContainer.setOnClickListener(v -> {
                Toast.makeText(requireContext(), "Settings screen not implemented yet", Toast.LENGTH_SHORT).show();
            });
        }

        if (btnHomeContainer != null) {
            btnHomeContainer.setOnClickListener(v -> {
                // Already on home, do nothing
            });
        }

        if (btnProfileContainer != null) {
            btnProfileContainer.setOnClickListener(v -> {
                Toast.makeText(requireContext(), "Profile screen not implemented yet", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
