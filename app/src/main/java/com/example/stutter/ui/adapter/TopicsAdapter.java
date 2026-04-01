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

    //Progress calculation
    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Topic t = topics.get(position);

        boolean locked = position > 0 && !topics.get(position - 1).completed;

        h.tvIcon.setText(t.completed ? "✅" : t.icon);
        h.tvTitle.setText(t.title);
        h.tvDesc.setText(t.description);


        int totalLevels = 10;
        int completedLevels = t.completedLessons;
        
        int progressPercent = (completedLevels * 100) / totalLevels;
        h.progress.setProgress(progressPercent);

        h.tvLessons.setText(completedLevels + "/" + totalLevels + " levels completed");


        h.tvLock.setVisibility(locked ? View.VISIBLE : View.GONE);
        h.itemView.setAlpha(locked ? 0.6f : 1f);

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
