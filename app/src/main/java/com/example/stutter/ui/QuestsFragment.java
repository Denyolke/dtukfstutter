package com.example.stutter.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stutter.R;
import com.example.stutter.data.QuestManager;
import com.example.stutter.firebase.FirebaseAuthManager;
import com.example.stutter.model.DailyQuest;
import com.example.stutter.model.UserProfile;
import com.example.stutter.ui.adapter.QuestsAdapter;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuestsFragment extends Fragment {

    public QuestsFragment() {
        super(R.layout.fragment_quests);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        RecyclerView rv      = v.findViewById(R.id.rvQuests);
        ProgressBar  pb      = v.findViewById(R.id.pbQuestsLoading);
        TextView     tvError = v.findViewById(R.id.tvQuestsError);
        TextView     tvDate  = v.findViewById(R.id.tvQuestDate);
        TextView     tvCoins = v.findViewById(R.id.tvCoinBalance);

        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        String todayDisplay = new SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
                .format(new Date());
        tvDate.setText(todayDisplay);

        FirebaseAuthManager authManager = FirebaseAuthManager.getInstance();
        FirebaseUser user = authManager.getCurrentUser();

        if (user == null) {
            pb.setVisibility(View.GONE);
            tvError.setText("Please log in to view quests.");
            tvError.setVisibility(View.VISIBLE);
            return;
        }

        // Load coin balance
        authManager.getUserProfile(user.getUid(), new FirebaseAuthManager.OnUserProfileListener() {
            @Override
            public void onSuccess(UserProfile profile) {
                if (isAdded()) tvCoins.setText(String.valueOf(profile.coins));
            }
            @Override
            public void onError(String errorMessage) { /* silent */ }
        });

        // Load / generate today's quests
        QuestManager.loadOrGenerateDailyQuests(user.getUid(), new QuestManager.OnQuestsLoadedListener() {
            @Override
            public void onLoaded(List<DailyQuest> quests) {
                if (!isAdded()) return;
                pb.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
                rv.setAdapter(new QuestsAdapter(quests));
            }

            @Override
            public void onError(String error) {
                if (!isAdded()) return;
                pb.setVisibility(View.GONE);
                tvError.setText("Could not load quests: " + error);
                tvError.setVisibility(View.VISIBLE);
            }
        });
    }
}