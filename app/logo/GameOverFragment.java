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

public class GameOverFragment extends Fragment {

    public GameOverFragment() {
        super(R.layout.fragment_game_over);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        super.onViewCreated(v, b);

        AppViewModel vm = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        Integer score = vm.quizScore.getValue();
        Integer totalQuestions = vm.totalQuestions.getValue();

        // Display info
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        TextView tvScore = v.findViewById(R.id.tvScore);
        TextView tvScoreLabel = v.findViewById(R.id.tvScoreLabel);
        TextView tvMessage = v.findViewById(R.id.tvMessage);

        tvTitle.setText("Out of Hearts! ❌");

        if (score != null && totalQuestions != null) {
            tvScore.setText(score + "/" + totalQuestions);
            tvScoreLabel.setText("Correct Answers");
        }

        tvMessage.setText("You ran out of hearts! Practice makes perfect. Try again!");
        tvMessage.setTextColor(0xFFDC2626); // Red color

        // Try Again button
        Button btnTryAgain = v.findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(x -> {
            vm.resetQuiz();
            ((MainActivity) requireActivity()).replace(new QuizFragment(), true);
        });

        // Back to Levels button
        Button btnBack = v.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(x -> {
            vm.resetQuiz();
            ((MainActivity) requireActivity()).replace(new LevelSelectionFragment(), false);
        });
    }
}
