package com.example.stutter.ui;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.stutter.MainActivity;
import com.example.stutter.R;
import com.example.stutter.firebase.FirebaseAuthManager;
import com.example.stutter.model.Level;
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
        if (totalQuestions == null) totalQuestions = 3;

        // Display score
        TextView tvScore = v.findViewById(R.id.tvScore);
        if (tvScore != null) {
            tvScore.setText(score + "/" + totalQuestions);
        }

        // Display message
        TextView tvMessage = v.findViewById(R.id.tvMessage);
        if (tvMessage != null) {
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
        }

        // Display XP
        int xpEarned = 0;
        if (level != null) {
            xpEarned = level.xpReward;
        }

        TextView tvXP = v.findViewById(R.id.tvXP);
        if (tvXP != null) {
            tvXP.setText("+" + xpEarned + " XP Earned!");
        }

        // Continue button
        Button btnContinue = v.findViewById(R.id.btnContinue);
        if (btnContinue != null) {
            btnContinue.setOnClickListener(x -> {
                // Update user stats in Firebase
                if (user != null && level != null) {
                    authManager.updateUserStatsOnQuizCompletion(user.getUid(), level.xpReward);

                    // Update topic progress
                    if (vm.topics.getValue() != null) {
                        for (com.example.stutter.model.Topic topic : vm.topics.getValue()) {
                            if (topic.id.equals(level.topicId)) {
                                int newCompleted = topic.completedLessons + 1;
                                vm.updateTopicProgress(level.topicId, newCompleted);
                                break;
                            }
                        }
                    }
                }

                vm.resetQuiz();
                ((MainActivity) requireActivity()).replace(new LevelSelectionFragment(), false);
            });
        }

        // Try again button
        Button btnTryAgain = v.findViewById(R.id.btnTryAgain);
        if (btnTryAgain != null) {
            btnTryAgain.setOnClickListener(x -> {
                vm.resetQuiz();
                ((MainActivity) requireActivity()).replace(new QuizFragment(), true);
            });
        }
    }
}
