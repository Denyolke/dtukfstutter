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
import com.example.stutter.model.Question;
import com.example.stutter.ui.adapter.OptionsAdapter;

import java.util.List;

public class QuizFragment extends Fragment {

    private AppViewModel vm;
    private List<Question> questions;
    private int index = 0;
    private int hearts = 3;
    private int score = 0;
    private boolean checked = false;

    public QuizFragment() {
        super(R.layout.fragment_quiz);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        super.onViewCreated(v, b);
        
        vm = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        
        // Get questions from selected LEVEL (not topic)
        questions = vm.getQuestionsForSelectedTopic(); // Uses selectedTopicId
        
        if (questions == null || questions.isEmpty()) {
            Toast.makeText(requireContext(), "No questions found", Toast.LENGTH_SHORT).show();
            ((MainActivity) requireActivity()).replace(new LevelSelectionFragment(), false);
            return;
        }

        TextView tvHearts = v.findViewById(R.id.tvHearts);
        TextView tvQuestion = v.findViewById(R.id.tvQuestion);
        TextView tvExplanation = v.findViewById(R.id.tvExplanation);
        ProgressBar progress = v.findViewById(R.id.progress);
        Button btn = v.findViewById(R.id.btnAction);
        Button btnBack = v.findViewById(R.id.btnBack);
        Button btnAddHeart = v.findViewById(R.id.btnAddHeart); // Test button

        RecyclerView rv = v.findViewById(R.id.rvOptions);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        loadQuestion(tvHearts, tvQuestion, tvExplanation, progress, rv, btn);

        // Back button - go back to level selection instead of home
        if (btnBack != null) {
            btnBack.setOnClickListener(x -> {
                ((MainActivity) requireActivity()).replace(new LevelSelectionFragment(), false);
            });
        }

        // Test button to add heart (for testing only - press H key)
        if (btnAddHeart != null) {
            btnAddHeart.setOnClickListener(x -> {
                hearts++;
                tvHearts.setText(getHeartsDisplay());
                Toast.makeText(requireContext(), "Heart added! Total: " + hearts, Toast.LENGTH_SHORT).show();
            });

            // Also respond to hardware H key (for testing)
            v.setOnKeyListener((v1, keyCode, event) -> {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_H) {
                        hearts++;
                        tvHearts.setText(getHeartsDisplay());
                        Toast.makeText(requireContext(), "Heart added! Total: " + hearts, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            });
        }

        btn.setOnClickListener(x -> {
            OptionsAdapter ad = (OptionsAdapter) rv.getAdapter();
            if (ad == null) return;

            if (!checked) {
                int sel = ad.getSelected();
                if (sel == -1) {
                    Toast.makeText(requireContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                ad.lock();
                checked = true;

                Question q = questions.get(index);
                tvExplanation.setText(q.explanation);
                tvExplanation.setVisibility(View.VISIBLE);

                // Check answer
                boolean isCorrect = sel == q.correctAnswer;
                if (isCorrect) {
                    score++;
                    ad.markCorrect(sel);
                } else {
                    hearts--;
                    ad.markIncorrect(sel, q.correctAnswer);
                }

                btn.setText("Continue");
            } else {
                // Move to next question or end quiz
                index++;
                checked = false;

                // Check if out of hearts
                if (hearts <= 0) {
                    // Game over - out of hearts!
                    vm.quizScore.setValue(score);
                    vm.quizXP.setValue(0); // NO XP if out of hearts
                    vm.totalQuestions.setValue(questions.size());
                    vm.gameOver.setValue(true); // Signal game over
                    
                    ((MainActivity) requireActivity())
                            .replace(new GameOverFragment(), false);
                } else if (index >= questions.size()) {
                    // Quiz complete with hearts remaining!
                    vm.quizScore.setValue(score);
                    vm.quizXP.setValue(15); // 15 XP only if hearts remain
                    vm.totalQuestions.setValue(questions.size());
                    vm.gameOver.setValue(false);
                    
                    ((MainActivity) requireActivity())
                            .replace(new QuizCompletionFragment(), false);
                } else {
                    loadQuestion(tvHearts, tvQuestion, tvExplanation, progress, rv, btn);
                }
            }
        });
    }

    private String getHeartsDisplay() {
        StringBuilder heartsDisplay = new StringBuilder();
        for (int i = 0; i < hearts; i++) {
            heartsDisplay.append("❤️");
        }
        return heartsDisplay.toString();
    }

    private void loadQuestion(TextView tvHearts, TextView tvQuestion,
                              TextView tvExplanation, ProgressBar progress,
                              RecyclerView rv, Button btn) {

        if (index >= questions.size()) return;

        Question q = questions.get(index);
        
        // Update hearts display
        tvHearts.setText(getHeartsDisplay());
        
        tvQuestion.setText(q.question);
        tvExplanation.setVisibility(View.GONE);
        
        // Update progress
        int progressPercent = (int) ((index * 100f) / questions.size());
        progress.setProgress(progressPercent);

        rv.setAdapter(new OptionsAdapter(q.options, i -> {}));
        btn.setText("Check Answer");
    }
}
