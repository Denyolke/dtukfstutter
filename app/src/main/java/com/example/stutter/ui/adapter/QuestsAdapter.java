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
        DailyQuest q = quests.get(position);

        h.tvTitle.setText(q.title);
        h.tvDesc.setText(q.description);
        h.tvReward.setText("🪙 +" + q.rewardCoins);

        int progress = q.target > 0
                ? (int) ((q.progress * 100f) / q.target)
                : 0;

        h.progressBar.setProgress(Math.min(progress, 100));
        h.tvProgress.setText(q.progress + " / " + q.target);

        if (q.completed) {
            h.tvStatus.setText("✅ Completed");
            h.tvStatus.setTextColor(0xFF10B981);
            h.itemView.setAlpha(0.7f);
        } else {
            h.tvStatus.setText("In Progress");
            h.tvStatus.setTextColor(0xFF6B7280);
            h.itemView.setAlpha(1f);
        }
    }

    @Override
    public int getItemCount() {
        return quests.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvReward, tvProgress, tvStatus;
        LinearProgressIndicator progressBar;

        VH(@NonNull View itemView) {
            super(itemView);
            tvTitle     = itemView.findViewById(R.id.tvQuestTitle);
            tvDesc      = itemView.findViewById(R.id.tvQuestDesc);
            tvReward    = itemView.findViewById(R.id.tvQuestReward);
            tvProgress  = itemView.findViewById(R.id.tvQuestProgress);
            tvStatus    = itemView.findViewById(R.id.tvQuestStatus);
            progressBar = itemView.findViewById(R.id.questProgress);
        }
    }
}
