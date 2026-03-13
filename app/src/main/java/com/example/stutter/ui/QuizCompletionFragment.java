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

        Integer score = vm.quizScore.getValue();
        Integer totalQuestions = vm.totalQuestions.getValue();

        // Display score
        TextView tvScore = v.findViewById(R.id.tvScore);
        TextView tvScoreLabel = v.findViewById(R.id.tvScoreLabel);
        if (score != null && totalQuestions != null) {
            tvScore.setText(score + "/" + totalQuestions);
            tvScoreLabel.setText("Correct Answers");
        }

        // Display XP earned
        TextView tvXP = v.findViewById(R.id.tvXP);
        tvXP.setText("+15 XP Earned!");

        // Perfect score message
        TextView tvMessage = v.findViewById(R.id.tvMessage);
        if (score != null && totalQuestions != null && score == totalQuestions) {
            tvMessage.setText("🎉 Perfect Score! Amazing!");
            tvMessage.setTextColor(0xFF4CAF50); // Green
        } else if (score != null && totalQuestions != null && score >= (totalQuestions * 0.8)) {
            tvMessage.setText("Great job! 🌟");
            tvMessage.setTextColor(0xFF2196F3); // Blue
        } else {
            tvMessage.setText("Good effort! Keep improving.");
            tvMessage.setTextColor(0xFF1F2937); // Dark gray
        }

        // Continue to next level
        Button btnContinue = v.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(x -> {
            // Update user XP in Firebase
            if (user != null) {
                authManager.updateUserStats(user.getUid(), 15);
            }
            
            vm.resetQuiz();
            // Go back to level selection
            ((MainActivity) requireActivity()).replace(new LevelSelectionFragment(), false);
        });

        // Try again button
        Button btnTryAgain = v.findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(x -> {
            vm.resetQuiz();
            ((MainActivity) requireActivity()).replace(new QuizFragment(), true);
        });
    }
}
