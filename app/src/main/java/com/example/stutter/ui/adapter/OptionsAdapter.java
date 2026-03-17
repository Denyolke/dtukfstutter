package com.example.stutter.ui.adapter;

import android.content.Context;
import android.util.TypedValue;
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
    private int selected        = -1;
    private int correctAnswer   = -1;
    private int incorrectAnswer = -1;
    private boolean locked      = false;
    private final OnSelect onSelect;

    public OptionsAdapter(List<String> options, OnSelect onSelect) {
        this.options  = options;
        this.onSelect = onSelect;
    }

    public void lock()            { locked = true; }
    public int  getSelected()     { return selected; }

    public void markCorrect(int correctIdx) {
        this.correctAnswer = correctIdx;
        notifyDataSetChanged();
    }

    public void markIncorrect(int incorrectIdx, int correctIdx) {
        this.incorrectAnswer = incorrectIdx;
        this.correctAnswer   = correctIdx;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_option, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        h.tv.setText(options.get(position));

        Context ctx = h.itemView.getContext();

        // Resolve theme-aware surface color for the default state
        int bgColor   = resolveAttr(ctx, R.attr.appCard);
        int textColor = resolveAttr(ctx, R.attr.appTextPrimary);

        if (correctAnswer == position) {
            bgColor   = 0xFFD1FAE5; // light green — same in both themes, readable
            textColor = 0xFF065F46;
        } else if (incorrectAnswer == position) {
            bgColor   = 0xFFFEE2E2; // light red
            textColor = 0xFF7F1D1D;
        } else if (selected == position) {
            bgColor   = 0xFF1D4ED8; // blue selection
            textColor = 0xFFFFFFFF;
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
    public int getItemCount() { return options.size(); }

    /** Resolve a theme color attribute to its actual color int. */
    private static int resolveAttr(Context ctx, int attr) {
        TypedValue tv = new TypedValue();
        ctx.getTheme().resolveAttribute(attr, tv, true);
        return tv.data;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tv;
        VH(View v) {
            super(v);
            tv = v.findViewById(R.id.tvOptionText);
        }
    }
}
