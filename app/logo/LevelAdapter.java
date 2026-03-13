package com.example.stutter.ui.adapter;

import android.view.*;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stutter.R;
import com.example.stutter.model.Level;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.VH> {

    public interface OnLevelClick {
        void onClick(Level level);
    }

    private final List<Level> levels;
    private final OnLevelClick onLevelClick;

    public LevelAdapter(List<Level> levels, OnLevelClick onLevelClick) {
        this.levels = levels;
        this.onLevelClick = onLevelClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_level, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Level level = levels.get(position);
        boolean locked = position > 0 && !levels.get(position - 1).completed;

        h.tvLevel.setText("Level " + level.levelNumber);
        h.tvTitle.setText(level.title);
        h.tvQuestions.setText(level.questionsCount + " Questions");
        h.tvDifficulty.setText(level.difficulty);
        h.tvXP.setText("+" + level.xpReward + " XP");
        h.tvStatus.setText(level.completed ? "✅ Completed" : (locked ? "🔒 Locked" : "🎯 Available"));

        h.itemView.setAlpha(locked ? 0.5f : 1f);
        h.itemView.setEnabled(!locked);
        
        h.itemView.setOnClickListener(v -> {
            if (!locked) {
                onLevelClick.onClick(level);
            }
        });
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvLevel, tvTitle, tvQuestions, tvDifficulty, tvXP, tvStatus;

        VH(@NonNull View itemView) {
            super(itemView);
            tvLevel = itemView.findViewById(R.id.tvLevel);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvQuestions = itemView.findViewById(R.id.tvQuestions);
            tvDifficulty = itemView.findViewById(R.id.tvDifficulty);
            tvXP = itemView.findViewById(R.id.tvXP);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
