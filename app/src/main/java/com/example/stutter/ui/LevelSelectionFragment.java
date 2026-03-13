package com.example.stutter.ui;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stutter.MainActivity;
import com.example.stutter.R;
import com.example.stutter.model.Level;
import com.example.stutter.model.Topic;
import com.example.stutter.ui.adapter.LevelAdapter;

import java.util.ArrayList;
import java.util.List;

public class LevelSelectionFragment extends Fragment {

    private AppViewModel vm;
    private String selectedTopicId;
    private String selectedTopicTitle;

    public LevelSelectionFragment() {
        super(R.layout.fragment_level_selection);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vm = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        selectedTopicId = vm.selectedTopicId.getValue();

        if (selectedTopicId == null) {
            ((MainActivity) requireActivity()).replace(new HomeFragment(), false);
            return;
        }

        TextView tvTopicName = view.findViewById(R.id.tvTopicName);
        RecyclerView rvLevels = view.findViewById(R.id.rvLevels);
        ProgressBar pbProgress = view.findViewById(R.id.pbProgress);
        Button btnBack = view.findViewById(R.id.btnBack);

        rvLevels.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Get the topic name
        vm.topics.observe(getViewLifecycleOwner(), topics -> {
            if (topics != null) {
                for (Topic topic : topics) {
                    if (topic.id.equals(selectedTopicId)) {
                        selectedTopicTitle = topic.title;
                        tvTopicName.setText(topic.title);
                        break;
                    }
                }
            }
        });

        // Generate 10 levels for this topic
        List<Level> levels = generateLevelsForTopic(selectedTopicId);

        // Calculate completion percentage
        int completed = (int) levels.stream().filter(l -> l.completed).count();
        int completionPercent = (completed * 100) / 10;
        pbProgress.setProgress(completionPercent);

        // Set up adapter
        rvLevels.setAdapter(new LevelAdapter(levels, level -> {
            // Check if previous level is completed
            boolean canAccess = level.levelNumber == 1 || 
                              levels.get(level.levelNumber - 2).completed;

            if (canAccess) {
                vm.selectedLevel.setValue(level);
                ((MainActivity) requireActivity()).replace(new QuizFragment(), true);
            } else {
                Toast.makeText(requireContext(), 
                    "Complete level " + (level.levelNumber - 1) + " first!", 
                    Toast.LENGTH_SHORT).show();
            }
        }));

        // Back button
        btnBack.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).replace(new HomeFragment(), false);
        });
    }

    private List<Level> generateLevelsForTopic(String topicId) {
        List<Level> levels = new ArrayList<>();
        String[] titles = {
            "Foundations",
            "Fundamentals", 
            "Core Concepts",
            "Intermediate",
            "Intermediate Advanced",
            "Complex Problems",
            "Optimization",
            "Real World Applications",
            "Expert Challenge",
            "Master Level"
        };
        
        String[] difficulties = {
            "Beginner", "Beginner", "Beginner", "Intermediate", "Intermediate",
            "Intermediate", "Advanced", "Advanced", "Advanced", "Expert"
        };

        for (int i = 1; i <= 10; i++) {
            levels.add(new Level(
                topicId + "_level_" + i,
                topicId,
                i,
                titles[i - 1],
                8 + (i % 3), // 8-10 questions
                false, // Not completed yet
                difficulties[i - 1]
            ));
        }

        return levels;
    }
}
