package com.example.stutter.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.stutter.MainActivity;
import com.example.stutter.R;
import com.example.stutter.firebase.FirebaseAuthManager;
import com.example.stutter.model.Level;
import com.example.stutter.model.Topic;
import com.google.firebase.auth.FirebaseUser;

public class QuizCompletionFragment extends Fragment {

    public QuizCompletionFragment() {
        super(R.layout.fragment_quiz_completion);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        super.onViewCreated(v, b);

        AppViewModel vm = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();
        FirebaseUser user = authManager.getCurrentUser();

        Level level = vm.selectedLevel.getValue();
        Integer score = vm.quizScore.getValue();
        Integer totalQuestions = vm.totalQuestions.getValue();

        if (score == null) score = 0;
        if (totalQuestions == null) totalQuestions = 0;

        TextView tvScore = v.findViewById(R.id.tvScore);
        TextView tvMessage = v.findViewById(R.id.tvMessage);
        TextView tvXP = v.findViewById(R.id.tvXP);

        tvScore.setText(score + "/" + totalQuestions);

        if (score == totalQuestions) {
            tvMessage.setText("🎉 Perfect Score!");
            tvMessage.setTextColor(0xFF4CAF50);
        } else if (score >= (totalQuestions * 0.67)) {
            tvMessage.setText("Great job! 🌟");
            tvMessage.setTextColor(0xFF2196F3);
        } else {
            tvMessage.setText("Good effort!");
            tvMessage.setTextColor(0xFF1F2937);
        }

        int xpEarned = level != null ? level.xpReward : 15;
        tvXP.setText("+" + xpEarned + " XP Earned!");

        Button btnContinue = v.findViewById(R.id.btnContinue);
        Button btnTryAgain = v.findViewById(R.id.btnTryAgain);

        btnContinue.setOnClickListener(x -> {
            if (level == null) {
                Toast.makeText(requireContext(), "Level data missing.", Toast.LENGTH_SHORT).show();
                ((MainActivity) requireActivity()).replace(new LevelSelectionFragment(), false);
                return;
            }

            if (user != null) {
                authManager.updateUserStatsOnQuizCompletion(user.getUid(), level.xpReward);
            }

            Topic matchedTopic = null;
            if (vm.topics.getValue() != null) {
                for (Topic topic : vm.topics.getValue()) {
                    if (topic.id.equals(level.topicId)) {
                        matchedTopic = topic;
                        break;
                    }
                }
            }

            int newCompleted = level.levelNumber;
            if (matchedTopic != null) {
                newCompleted = Math.max(matchedTopic.completedLessons, level.levelNumber);
            }

            btnContinue.setEnabled(false);
            btnContinue.setText("Saving...");

            vm.updateTopicProgress(level.topicId, newCompleted, new AppViewModel.SaveProgressCallback() {
                @Override
                public void onSuccess() {
                    if (!isAdded()) return;

                    vm.resetQuizSessionOnly();
                    ((MainActivity) requireActivity()).replace(new LevelSelectionFragment(), false);
                }

                @Override
                public void onFailure(String errorMessage) {
                    if (!isAdded()) return;

                    btnContinue.setEnabled(true);
                    btnContinue.setText("Continue to Next Level");

                    Toast.makeText(requireContext(),
                            "Failed to save progress: " + errorMessage,
                            Toast.LENGTH_LONG).show();
                }
            });
        });

        btnTryAgain.setOnClickListener(x -> {
            vm.resetQuizSessionOnly();
            vm.selectedLevel.setValue(level);
            ((MainActivity) requireActivity()).replace(new QuizFragment(), true);
        });
    }
}