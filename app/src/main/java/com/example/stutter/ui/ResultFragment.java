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

public class ResultFragment extends Fragment {

    public ResultFragment() {
        super(R.layout.fragment_result);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        super.onViewCreated(v, b);
        
        AppViewModel vm = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        Integer score = vm.quizScore.getValue();
        Integer xp = vm.quizXP.getValue();

        if (score != null) {
            ((TextView) v.findViewById(R.id.tvScore))
                    .setText("Score: " + score);
        }

        if (xp != null) {
            ((TextView) v.findViewById(R.id.tvXP))
                    .setText("XP earned: +" + xp);
        }

        Button btnHome = v.findViewById(R.id.btnHome);
        if (btnHome != null) {
            btnHome.setOnClickListener(x -> {
                vm.resetQuiz();
                ((MainActivity) requireActivity())
                        .replace(new HomeFragment(), false);
            });
        }
    }
}
