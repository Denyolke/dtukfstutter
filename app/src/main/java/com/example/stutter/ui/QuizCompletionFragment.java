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
import com.example.stutter.data.QuestManager;
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

        Level   level          = vm.selectedLevel.getValue();
        Integer score          = vm.quizScore.getValue();
        Integer totalQuestions = vm.totalQuestions.getValue();

        if (score          == null) score          = 0;
        if (totalQuestions == null) totalQuestions = 0;

        final int finalScore     = score;
        final int finalTotal     = totalQuestions;
        final boolean isPerfect  = finalScore == finalTotal && finalTotal > 0;
        final int xpEarned       = level != null ? level.xpReward : 15;

        //Bind views
        TextView tvScore   = v.findViewById(R.id.tvScore);
        TextView tvMessage = v.findViewById(R.id.tvMessage);
        TextView tvXP      = v.findViewById(R.id.tvXP);
        Button btnContinue = v.findViewById(R.id.btnContinue);
        Button btnTryAgain = v.findViewById(R.id.btnTryAgain);

        tvScore.setText(finalScore + "/" + finalTotal);
        tvXP.setText("+" + xpEarned + " XP Earned!");

        if (isPerfect) {
            tvMessage.setText("Perfect Score!");
            tvMessage.setTextColor(0xFF4CAF50);
        } else if (finalScore >= (finalTotal * 0.67)) {
            tvMessage.setText("Great job!");
            tvMessage.setTextColor(0xFF2196F3);
        } else {
            tvMessage.setText("Good effort!");
            tvMessage.setTextColor(0xFF1F2937);
        }

        //Award XP + coins + streak
        if (user != null) {
            authManager.updateUserStatsOnQuizCompletion(
                    user.getUid(),
                    xpEarned,
                    new FirebaseAuthManager.OnCoinsUpdateListener() {
                        @Override
                        public void onSuccess(int newTotal, int coinsEarned) {
                            if (!isAdded()) return;
                            Toast.makeText(requireContext(),
                                    "+" + coinsEarned + " 🪙 coins earned!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onError(String error) { /* silent */ }
                    });

            //Quest progress
            QuestManager.incrementQuestProgress(
                    user.getUid(), "COMPLETE_QUIZZES", 1, makeQuestListener());
            QuestManager.incrementQuestProgress(
                    user.getUid(), "CORRECT_ANSWERS", finalScore, makeQuestListener());
            QuestManager.incrementQuestProgress(
                    user.getUid(), "EARN_XP", xpEarned, makeQuestListener());
            if (isPerfect) {
                QuestManager.incrementQuestProgress(
                        user.getUid(), "PERFECT_SCORE", 1, makeQuestListener());
            }
        }

        //Continue button
        btnContinue.setOnClickListener(x -> {
            if (level == null) {
                Toast.makeText(requireContext(), "Level data missing.", Toast.LENGTH_SHORT).show();
                ((MainActivity) requireActivity()).replace(new LevelSelectionFragment(), false);
                return;
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

            if (newCompleted >= 10 && user != null) {
                QuestManager.incrementQuestProgress(
                        user.getUid(), "COMPLETE_TOPIC", 1, makeQuestListener());
            }

            btnContinue.setEnabled(false);
            btnContinue.setText("Saving...");

            final int finalCompleted = newCompleted;
            vm.updateTopicProgress(level.topicId, finalCompleted, new AppViewModel.SaveProgressCallback() {
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

        //Try Again
        btnTryAgain.setOnClickListener(x -> {
            vm.resetQuizSessionOnly();
            vm.selectedLevel.setValue(level);
            ((MainActivity) requireActivity()).replace(new QuizFragment(), true);
        });
    }

    //Quest listener helper
    private QuestManager.OnQuestProgressListener makeQuestListener() {
        return new QuestManager.OnQuestProgressListener() {
            @Override
            public void onQuestCompleted(String questId, int coinsEarned) {
                if (!isAdded()) return;
                FirebaseUser user = FirebaseAuthManager.getInstance().getCurrentUser();
                if (user == null) return;
                FirebaseAuthManager.getInstance().awardCoins(
                        user.getUid(), coinsEarned,
                        new FirebaseAuthManager.OnAuthListener() {
                            @Override
                            public void onSuccess(String message) {
                                if (!isAdded()) return;
                                Toast.makeText(requireContext(),
                                        "Quest complete! +" + coinsEarned + " 🪙",
                                        Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void onError(String errorMessage) {}
                        });
            }

            @Override
            public void onError(String error) {}
        };
    }
}