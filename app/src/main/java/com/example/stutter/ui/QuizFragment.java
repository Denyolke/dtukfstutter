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
        questions = vm.getQuestionsForSelectedTopic();

        if (questions == null || questions.isEmpty()) {
            ((MainActivity) requireActivity()).replace(new HomeFragment(), false);
            return;
        }

        TextView tvHearts = v.findViewById(R.id.tvHearts);
        TextView tvQuestion = v.findViewById(R.id.tvQuestion);
        TextView tvExplanation = v.findViewById(R.id.tvExplanation);
        ProgressBar progress = v.findViewById(R.id.progress);
        Button btn = v.findViewById(R.id.btnAction);
        Button btnBack = v.findViewById(R.id.btnBack);

        RecyclerView rv = v.findViewById(R.id.rvOptions);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        loadQuestion(tvHearts, tvQuestion, tvExplanation, progress, rv, btn);

        // Back button
        if (btnBack != null) {
            btnBack.setOnClickListener(x -> {
                ((MainActivity) requireActivity()).replace(new HomeFragment(), false);
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

                if (hearts <= 0 || index >= questions.size()) {
                    vm.quizScore.setValue(score);
                    vm.quizXP.setValue(score * 10);
                    ((MainActivity) requireActivity())
                            .replace(new ResultFragment(), false);
                } else {
                    loadQuestion(tvHearts, tvQuestion, tvExplanation, progress, rv, btn);
                }
            }
        });
    }

    private void loadQuestion(TextView tvHearts, TextView tvQuestion,
                              TextView tvExplanation, ProgressBar progress,
                              RecyclerView rv, Button btn) {

        if (index >= questions.size()) return;

        Question q = questions.get(index);
        
        // Update hearts display
        StringBuilder heartsDisplay = new StringBuilder();
        for (int i = 0; i < hearts; i++) {
            heartsDisplay.append("❤️");
        }
        tvHearts.setText(heartsDisplay.toString());
        
        tvQuestion.setText(q.question);
        tvExplanation.setVisibility(View.GONE);
        
        // Update progress
        int progressPercent = (int) ((index * 100f) / questions.size());
        progress.setProgress(progressPercent);

        rv.setAdapter(new OptionsAdapter(q.options, i -> {}));
        btn.setText("Check Answer");
    }
}
