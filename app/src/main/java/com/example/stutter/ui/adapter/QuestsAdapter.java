package com.example.stutter.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stutter.R;
import com.example.stutter.model.DailyQuest;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;

public class QuestsAdapter extends RecyclerView.Adapter<QuestsAdapter.VH> {

    private final List<DailyQuest> quests;

    private static String iconFor(String type) {
        if (type == null) return "🎯";
        switch (type) {
            case "COMPLETE_QUIZZES": return "📝";
            case "PERFECT_SCORE":   return "⭐";
            case "EARN_XP":         return "🚀";
            case "CORRECT_ANSWERS": return "✅";
            case "COMPLETE_TOPIC":  return "🎓";
            case "LOGIN_STREAK":    return "🔥";
            default:                return "🎯";
        }
    }

    public QuestsAdapter(List<DailyQuest> quests) {
        this.quests = quests;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quest, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        DailyQuest quest = quests.get(position);

        h.tvIcon.setText(iconFor(quest.type));
        h.tvTitle.setText(quest.title);
        h.tvDesc.setText(quest.description);
        h.tvReward.setText("+" + quest.rewardCoins);

        int percent = quest.target > 0
                ? (int) ((quest.progress * 100f) / quest.target) : 0;
        h.progressBar.setProgress(percent);
        h.tvProgress.setText(quest.progress + " / " + quest.target);

        if (quest.completed) {
            h.tvCompleted.setVisibility(View.VISIBLE);
            h.progressBar.setProgress(100);
            h.tvProgress.setText(quest.target + " / " + quest.target);
        } else {
            h.tvCompleted.setVisibility(View.GONE);
        }

        h.itemView.setAlpha(quest.completed ? 0.7f : 1f);
    }

    @Override
    public int getItemCount() {
        return quests.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        // IDs must match item_quest.xml exactly
        TextView tvIcon, tvTitle, tvDesc, tvReward, tvProgress, tvCompleted;
        LinearProgressIndicator progressBar;

        VH(@NonNull View itemView) {
            super(itemView);
            tvIcon      = itemView.findViewById(R.id.tvQuestIcon);
            tvTitle     = itemView.findViewById(R.id.tvQuestTitle);
            tvDesc      = itemView.findViewById(R.id.tvQuestDesc);
            tvReward    = itemView.findViewById(R.id.tvQuestReward);
            tvProgress  = itemView.findViewById(R.id.tvQuestProgress);
            tvCompleted = itemView.findViewById(R.id.tvQuestCompleted);  // ← was tvQuestStatus
            progressBar = itemView.findViewById(R.id.questProgress);
        }
    }
}