package com.example.stutter.ui.adapter;

import android.view.*;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.example.stutter.R;
import com.example.stutter.model.Topic;

import java.util.List;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.VH> {

    public interface OnTopicClick {
        void onClick(String topicId);
    }

    private final List<Topic> topics;
    private final OnTopicClick onTopicClick;

    public TopicsAdapter(List<Topic> topics, OnTopicClick onTopicClick) {
        this.topics = topics;
        this.onTopicClick = onTopicClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Topic t = topics.get(position);

        // A topic is locked if the previous topic is not completed
        boolean locked = position > 0 && !topics.get(position - 1).completed;

        // Show completion badge or icon
        h.tvIcon.setText(t.completed ? "✅" : t.icon);
        h.tvTitle.setText(t.title);
        h.tvDesc.setText(t.description);

        // Calculate progress percentage based on LEVELS (10 total)
        // Each topic has 10 levels
        int totalLevels = 10;
        int completedLevels = t.completedLessons; // This represents completed levels
        
        int progressPercent = (completedLevels * 100) / totalLevels;
        h.progress.setProgress(progressPercent);

        // Display "X/10 levels completed" (changed from "lessons" to "levels")
        h.tvLessons.setText(completedLevels + "/" + totalLevels + " levels completed");

        // Show lock icon if locked
        h.tvLock.setVisibility(locked ? View.VISIBLE : View.GONE);
        h.itemView.setAlpha(locked ? 0.6f : 1f);

        // Set click listener only if not locked
        h.itemView.setOnClickListener(v -> {
            if (!locked) {
                onTopicClick.onClick(t.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvIcon, tvTitle, tvDesc, tvLessons, tvLock;
        LinearProgressIndicator progress;

        VH(@NonNull View itemView) {
            super(itemView);
            tvIcon = itemView.findViewById(R.id.tvIcon);
            tvTitle = itemView.findViewById(R.id.tvTopicTitle);
            tvDesc = itemView.findViewById(R.id.tvTopicDesc);
            tvLessons = itemView.findViewById(R.id.tvLessons);
            tvLock = itemView.findViewById(R.id.tvLock);
            progress = itemView.findViewById(R.id.progress);
        }
    }
}
