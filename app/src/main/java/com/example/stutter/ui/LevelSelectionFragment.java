package com.example.stutter.ui;

import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.List;

public class LevelSelectionFragment extends Fragment {

    private AppViewModel vm;
    private String selectedTopicId;

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

        vm.topics.observe(getViewLifecycleOwner(), topics -> {
            if (topics == null) return;

            Topic selectedTopic = topics.stream().filter(topic -> topic.id.equals(selectedTopicId)).findFirst().orElse(null);

            if (selectedTopic == null) {
                ((MainActivity) requireActivity()).replace(new HomeFragment(), false);
                return;
            }

            tvTopicName.setText(selectedTopic.title);

            List<Level> levels = vm.getLevelsForSelectedTopic();
            int completionPercent = (selectedTopic.completedLessons * 100) / 10;
            pbProgress.setProgress(completionPercent);

            rvLevels.setAdapter(new LevelAdapter(levels, level -> {
                boolean unlocked = level.levelNumber <= selectedTopic.completedLessons + 1;

                if (unlocked) {
                    vm.selectedLevel.setValue(level);
                    ((MainActivity) requireActivity()).replace(new QuizFragment(), true);
                } else {
                    Toast.makeText(requireContext(),
                            "Complete level " + (level.levelNumber - 1) + " first!",
                            Toast.LENGTH_SHORT).show();
                }
            }));
        });
        btnBack.setOnClickListener(v ->
                ((MainActivity) requireActivity()).replace(new HomeFragment(), false));
    }
}