package com.example.stutter.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
            if (user != null && level != null) {
                authManager.updateUserStatsOnQuizCompletion(user.getUid(), level.xpReward);

                if (vm.topics.getValue() != null) {
                    for (Topic topic : vm.topics.getValue()) {
                        if (topic.id.equals(level.topicId)) {
                            int newCompleted = Math.max(topic.completedLessons, level.levelNumber);
                            vm.updateTopicProgress(level.topicId, newCompleted);
                            break;
                        }
                    }
                }
            }

            vm.resetQuizSessionOnly();
            ((MainActivity) requireActivity()).replace(new LevelSelectionFragment(), false);
        });

        btnTryAgain.setOnClickListener(x -> {
            vm.resetQuizSessionOnly();
            vm.selectedLevel.setValue(level);
            ((MainActivity) requireActivity()).replace(new QuizFragment(), true);
        });
    }
}