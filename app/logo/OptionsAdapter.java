package com.example.stutter.ui.adapter;

import android.graphics.Color;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stutter.R;
import java.util.List;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.VH> {

    public interface OnSelect {
        void onSelect(int index);
    }

    private final List<String> options;
    private int selected = -1;
    private int correctAnswer = -1;
    private int incorrectAnswer = -1;
    private boolean locked = false;
    private final OnSelect onSelect;

    public OptionsAdapter(List<String> options, OnSelect onSelect) {
        this.options = options;
        this.onSelect = onSelect;
    }

    public void lock() { 
        locked = true; 
    }

    public int getSelected() { 
        return selected; 
    }

    public void markCorrect(int correctIdx) {
        this.correctAnswer = correctIdx;
        notifyDataSetChanged();
    }

    public void markIncorrect(int incorrectIdx, int correctIdx) {
        this.incorrectAnswer = incorrectIdx;
        this.correctAnswer = correctIdx;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        h.tv.setText(options.get(position));

        // Determine background color based on state
        int bgColor = 0xFFFAFAFA; // Default light gray
        int textColor = Color.BLACK;

        if (correctAnswer == position) {
            // Correct answer - green
            bgColor = 0xFFD1FAE5; // Light green
            textColor = 0xFF065F46; // Dark green text
        } else if (incorrectAnswer == position) {
            // Wrong answer - red
            bgColor = 0xFFFEE2E2; // Light red
            textColor = 0xFF7F1D1D; // Dark red text
        } else if (selected == position && locked) {
            // Selected but waiting to show result
            bgColor = 0xFFE0F2FE; // Light blue
        } else if (selected == position && !locked) {
            // Selected and not locked
            bgColor = 0xFFBFDBFE; // Slightly darker blue
        }

        h.itemView.setBackgroundColor(bgColor);
        h.tv.setTextColor(textColor);

        h.itemView.setOnClickListener(v -> {
            if (locked) return;
            selected = position;
            notifyDataSetChanged();
            onSelect.onSelect(position);
        });
    }

    @Override
    public int getItemCount() { 
        return options.size(); 
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tv;

        VH(View v) {
            super(v);
            tv = v.findViewById(android.R.id.text1);
        }
    }
}
